package com.unl.music.base.models.Practica4;

import com.unl.music.base.controller.data_struct.grahps.UndirectLabelGraph;
import com.unl.music.base.controller.data_struct.grahps.Adjacency;
import com.unl.music.base.controller.data_struct.list.LinkedList;

import javax.swing.*;
import java.awt.*;

public class Laberinto {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                int[] dims = pedirDimensiones();
                int filas = dims[0], cols = dims[1];
                char[][] maz = parseMaze(new Prim2().generar(filas, cols), filas, cols);
                Panel panel = new Panel(maz, new LinkedList<>(), cols);
                JScrollPane scroll = new JScrollPane(panel);
                JLabel distanceLabel = new JLabel("Distancia: –");
                JButton solveBtn = new JButton("Resolver");
                solveBtn.addActionListener(e -> {
                    try {
                        int idxS = -1, idxE = -1;
                        for (int i = 0; i < filas; i++) {
                            for (int j = 0; j < cols; j++) {
                                if (maz[i][j] == 'S') idxS = i * cols + j;
                                if (maz[i][j] == 'E') idxE = i * cols + j;
                            }
                        }
                        LinkedList<int[]> edges = new LinkedList<>();
                        for (int i = 0; i < filas; i++) {
                            for (int j = 0; j < cols; j++) {
                                if (maz[i][j] != '0') {
                                    int u = i * cols + j;
                                    int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
                                    for (int[] d : dirs) {
                                        int ni = i + d[0], nj = j + d[1];
                                        if (ni>=0 && ni<filas && nj>=0 && nj<cols && maz[ni][nj] != '0') {
                                            edges.add(new int[]{u, ni*cols + nj, 1});
                                        }
                                    }
                                }
                            }
                        }
                        int E = edges.getLength();
                        int[][] edgeArr = new int[E][3];
                        for (int k = 0; k < E; k++) edgeArr[k] = edges.get(k);
                        int V = filas * cols;
                        LinkedList<LinkedList<Adjacency>> adj =
                            UndirectLabelGraph.constructAdj(edgeArr, V);
                        UndirectLabelGraph.DijkstraRes res =
                            UndirectLabelGraph.dijkstraPre(adj, idxS, V);
                        LinkedList<Integer> path =
                            UndirectLabelGraph.obtenerCamino(res.anterior, idxS, idxE);
                        panel.setPath(path, cols);
                        int dist = Math.max(0, path.getLength() - 1);
                        distanceLabel.setText("Distancia: " + dist);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });

                JFrame frame = new JFrame("Laberinto de Josue");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().setLayout(new BorderLayout());
                frame.getContentPane().add(scroll, BorderLayout.CENTER);
                JPanel bottom = new JPanel(new BorderLayout(5,0));
                bottom.add(solveBtn, BorderLayout.WEST);
                bottom.add(distanceLabel, BorderLayout.EAST);
                frame.getContentPane().add(bottom, BorderLayout.SOUTH);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private static int[] pedirDimensiones() {
        JPanel p = new JPanel(new GridLayout(2,2,5,5));
        p.add(new JLabel("Filas (30–100):"));
        JTextField fField = new JTextField();
        p.add(fField);
        p.add(new JLabel("Columnas (30–100):"));
        JTextField cField = new JTextField();
        p.add(cField);

        while (true) {
            int res = JOptionPane.showConfirmDialog(null, p, 
                "Dimensiones del Laberinto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (res != JOptionPane.OK_OPTION) {
                JOptionPane.showMessageDialog(null, "Operacion cancelada");
                System.exit(0);
            }
            try {
                int f = Integer.parseInt(fField.getText().trim());
                int c = Integer.parseInt(cField.getText().trim());
                if (f >= 30 && f <= 100 && c >= 30 && c <= 100) {
                    return new int[]{f, c};
                }
            } catch (Exception ignored) {}
            JOptionPane.showMessageDialog(null, "Valores inválidos. Inténtalo de nuevo.");
        }
    }

    private static char[][] parseMaze(String raw, int filas, int cols) {
        char[][] maz = new char[filas][cols];
        String[] lines = raw.split("\n");
        for (int i = 0; i < filas; i++) {
            String[] tokens = lines[i].split(",");
            for (int j = 0; j < cols; j++) {
                maz[i][j] = tokens[j].charAt(0);
            }
        }
        return maz;
    }

    static class Panel extends JPanel {
        private final char[][] maze;
        private boolean[][] isPath;
        private final int cellSize = 10;
        private final int cols;
        public Panel(char[][] m, LinkedList<Integer> path, int cols) {
            this.maze = m;
            this.cols = cols;
            setPath(path, cols);
            setPreferredSize(new Dimension(m[0].length*cellSize, m.length*cellSize));
        }

        public void setPath(LinkedList<Integer> path, int cols) {
            int filas = maze.length, cols0 = maze[0].length;
            isPath = new boolean[filas][cols0];
            for (int k = 0; k < path.getLength(); k++) {
                int idx = path.get(k);
                int r = idx / cols;
                int c = idx % cols;
                isPath[r][c] = true;
            }
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[0].length; j++) {
                    if (isPath[i][j] && maze[i][j] != 'S' && maze[i][j] != 'E') {
                        g.setColor(Color.ORANGE);
                    } else {
                        switch (maze[i][j]) {
                            case '0': g.setColor(Color.DARK_GRAY); break;
                            case '1': g.setColor(Color.WHITE);     break;
                            case 'S': g.setColor(Color.GREEN);     break;
                            case 'E': g.setColor(Color.RED);       break;
                            default:  g.setColor(Color.PINK);      break;
                        }
                    }
                    g.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
                }
            }
        }
    }
}
