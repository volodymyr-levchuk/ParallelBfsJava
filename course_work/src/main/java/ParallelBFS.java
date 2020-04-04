
import java.util.*;

public class ParallelBFS {
    private int[][] matrix;
    private Queue<Integer> globalQueue;
    private List<Queue<Integer>> localQueues;
    private boolean[] visited;
    private boolean isDone;
    private int counter;
    private int countVertex;

    public ParallelBFS(int[][] matrix, int countThreads) {
        this.matrix = matrix;
        this.countVertex = matrix.length;
        this.localQueues = new ArrayList<>(countThreads);
        for (int i = 0; i < countThreads; i++) {
            localQueues.add(new PriorityQueue());
        }
        this.visited = new boolean[this.countVertex];
        Arrays.fill(this.visited, false);
        isDone = false;
        globalQueue = new PriorityQueue();
        globalQueue.add(0);
        counter = 0;
    }

    public List<Queue<Integer>> getLocalQueues() {
        return localQueues;
    }

    public int getCountVertex() {
        return countVertex;
    }

    public synchronized boolean isVisited(int index) {
        return visited[index];
    }

    public synchronized void setVisited(int index, boolean value) {
       // System.out.println(index);
        visited[index] = value;
    }

    public synchronized void addQueue(Queue<Integer> tmp) {
        while (!tmp.isEmpty()) {
            globalQueue.add(tmp.poll());
        }
    }

    public boolean isNeighbour(int node, int neighbour) {
        return matrix[node][neighbour] == 1 ? true : false;
    }

    public synchronized void incrementCounter() {
        counter++;
    }

    public boolean isDone() {
        return isDone;
    }

    public synchronized void bfs() {
        while (!isDone && globalQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        int index = (int) (Thread.currentThread().getId());
        if (!globalQueue.isEmpty()) {
            boolean popped = false;
            int vertex = globalQueue.poll();
            popped = true;

            while (visited[vertex]) {
                if (globalQueue.isEmpty()) {
                    isDone = true;
                    popped = false;
                    break;
                } else {
                    vertex = globalQueue.poll();
                    popped = true;
                }
            }

            if (popped) {
                setVisited(vertex, true);
                counter++;
                boolean flag = false;

                for (int i = 0; i < countVertex; i++) {
                    if (vertex == i) {
                        continue;
                    }
                    if (isNeighbour(vertex, i) && !visited[i] && !flag) {
                        localQueues.get(index).add(i);
                        flag = true;
                    }

                    if (isNeighbour(vertex, i) && !visited[i] && flag) {
                        globalQueue.add(i);
                    }
                }
            }
        }

        if (globalQueue.isEmpty()) {
            isDone = true;
        }

        if (isDone && counter < countVertex) {
            isDone = false;
            for (int i = 0; i < countVertex; i++) {
                if (!visited[i])
                    globalQueue.add(i);
            }
        }
        notifyAll();
    }
}
