
import java.util.PriorityQueue;
import java.util.Queue;

public class SubBFS extends Thread {

    private int threadNumber;
    private ParallelBFS bfs;

    public SubBFS(ParallelBFS bfs, int threadNumber) {
        this.threadNumber = threadNumber;
        this.bfs = bfs;
    }


    @Override
    public long getId() {
        return threadNumber;
    }

    @Override
    public void run() {
        while (!bfs.isDone()) {
            bfs.bfs();
            yield();
            subBfs(bfs.getLocalQueues().get(threadNumber));
        }
    }

    public void subBfs(Queue<Integer> localQueue) {
        Queue<Integer> tmpQueue = new PriorityQueue();

        while (!localQueue.isEmpty()) {
            int vertex = localQueue.poll();
            //System.out.println(node);
            if (!bfs.isVisited(vertex)) {
                bfs.setVisited(vertex, true);
                bfs.incrementCounter();
                boolean toLocal = true;
                for (int i = 0; i < bfs.getCountVertex(); i++) {
                    if (vertex == i) {
                        continue;
                    }
                    if (bfs.isNeighbour(vertex, i) && !toLocal && !bfs.isVisited(i)) {
                        tmpQueue.add(i);

                    }
                    if (bfs.isNeighbour(vertex, i) && toLocal && !bfs.isVisited(i)) {
                        localQueue.add(i);
                        toLocal = false;
                    }
                }
            }
        }
        bfs.addQueue(tmpQueue);
    }
}
