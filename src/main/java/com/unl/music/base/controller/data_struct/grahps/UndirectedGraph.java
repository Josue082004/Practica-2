package com.unl.music.base.controller.data_struct.grahps;

public class UndirectedGraph  extends DirectedGraph{

    public UndirectedGraph(Integer nro_vertex) {
        super(nro_vertex);
     
    }
    
     @Override
    public void insert(Integer o, Integer d, Float weight) {
        // TODO Auto-generated method stub
        if (o.intValue()<= nro_vertex().intValue() && d.intValue() <= nro_vertex().intValue()) {
            if (exists_edge(o, d) == null) {
                //nro_edge++;
                setNro_edge(nro_edge() + 1);
                //origin
                Adjacency aux = new Adjacency(d, weight);
                getList_adjacencies()[o].add(aux);
                //destiny
                Adjacency auxD = new Adjacency(o, weight);
                getList_adjacencies()[d].add(auxD);
            }
        }else{
            throw new ArrayIndexOutOfBoundsException("Vertex origin o destiny index out");
        }

    }
}

