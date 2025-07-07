package com.unl.music.base.controller.data_struct.grahps;

import com.unl.music.base.controller.data_struct.list.LinkedList;

public class UndirectLabelGraph<E> extends DirectLabelGraph<E> {

    public UndirectLabelGraph(Integer nro_vertex, Class<E> clazz) {
        super(nro_vertex, clazz);
    }

    @Override
    public void insert_label(E o, E d, Float weight) {
        if (isLabelGraph()) {
            insert(getVertex(o), getVertex(d), weight);
            insert(getVertex(d), getVertex(o), weight);
        }
    }

    public static LinkedList<LinkedList<Adjacency>> constructAdj(int[][] edges, int V) {
        LinkedList<LinkedList<Adjacency>> adj = new LinkedList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new LinkedList<>());
        }
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            float wt = edge[2];

            adj.get(u).add(new Adjacency(v, wt));
            adj.get(v).add(new Adjacency(u, wt));
        }
        return adj;
    }

    public static float[] dijkstra(LinkedList<LinkedList<Adjacency>> adj, int origen, int V) throws Exception {
        float[] distancia = new float[V];
        boolean[] revisado = new boolean[V];
        int[] anterior = new int[V];
        inicioDijkstra(distancia, anterior, origen, V);
        for (int cont = 0; cont < V - 1; cont++) {
            int verticeM = encontrarVerticeM(distancia, revisado, V);

            if (verticeM == -1 || distancia[verticeM] == Float.MAX_VALUE) {
                break;
            }

            revisado[verticeM] = true;
            nuevaDistancia(adj, distancia, revisado, anterior, verticeM);
        }
        return distancia;
    }

    public static class DijkstraRes {
        public final float[] distancia;
        public final int[] anterior;

        public DijkstraRes(float[] dist, int[] pred) {
            this.distancia = dist;
            this.anterior = pred;
        }
    }

    public static DijkstraRes dijkstraPre(
            LinkedList<LinkedList<Adjacency>> adj, int origen, int V) throws Exception {
        float[] distancia = new float[V];
        boolean[] revisado = new boolean[V];
        int[] anterior = new int[V];
        inicioDijkstra(distancia, anterior, origen, V);
        for (int cont = 0; cont < V - 1; cont++) {
            int u = encontrarVerticeM(distancia, revisado, V);
            if (u == -1 || distancia[u] == Float.MAX_VALUE)
                break;
            revisado[u] = true;
            nuevaDistancia(adj, distancia, revisado, anterior, u);
        }
        return new DijkstraRes(distancia, anterior);
    }

    private static void inicioDijkstra(float[] distancia, int[] anterior, int origen, int V) {
        for (int i = 0; i < V; i++) {
            distancia[i] = Float.MAX_VALUE;
            anterior[i] = -1;
        }
        distancia[origen] = 0;
    }

    private static int encontrarVerticeM(float[] distancia, boolean[] revisado, int V) {
        float minimaDistancia = Float.MAX_VALUE;
        int indiceMinimo = -1;

        for (int vertice = 0; vertice < V; vertice++) {
            if (!revisado[vertice] && distancia[vertice] <= minimaDistancia) {
                minimaDistancia = distancia[vertice];
                indiceMinimo = vertice;
            }
        }
        return indiceMinimo;
    }

    private static void nuevaDistancia(LinkedList<LinkedList<Adjacency>> adj, float[] distancia,
            boolean[] revisado, int[] anterior, int verticeA) throws Exception {
        LinkedList<Adjacency> adyacentes = adj.get(verticeA);
        for (int i = 0; i < adyacentes.getLength(); i++) {
            Adjacency arista = adyacentes.get(i);
            int destino = arista.getDestiny();
            float peso = arista.getWieght();
            if (!revisado[destino] && peso >= 0) {
                float nuevaDistancia = distancia[verticeA] + peso;

                if (nuevaDistancia < distancia[destino]) {
                    distancia[destino] = nuevaDistancia;
                    anterior[destino] = verticeA;
                }
            }
        }
    }

    public static LinkedList<Integer> obtenerCamino(int[] anterior, int origen, int destino) throws Exception {
        LinkedList<Integer> temp = new LinkedList<>();
        LinkedList<Integer> camino = new LinkedList<>();
        if (anterior[destino] == -1 && destino != origen) {
            return camino;
        }
        int actual = destino;
        while (actual != -1) {
            temp.add(actual);
            actual = anterior[actual];
        }
        for (int i = temp.getLength() - 1; i >= 0; i--) {
            camino.add(temp.get(i));
        }
        return camino;
    }
}
