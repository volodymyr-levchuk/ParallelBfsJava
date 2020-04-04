import java.util.Calendar;

public class TestingTimeWorking {

    public void changingCountVertex(int minCount, int step, int maxCount, int countThreads){
        for(int i = minCount; i < maxCount; i+= step){
            testing(i, countThreads);
        }
    }

    public void changingCountThreads(int minCount, int step, int maxCount, int countVertex){
        for(int i = minCount; i < maxCount; i+= step){
            testing(countVertex, i);
        }
    }

    public void testing(int countVertex, int countThreads) {
        if( countThreads > countVertex ){
            System.out.println("Warning: count thread more then count vertex");
            return;
        }
        System.out.println("Count vertex = " + countVertex + ", count Threads = " + countThreads);
        long start, finish;

        final int[][] matrix = Generator.generateMatrix(countVertex);

        start = Calendar.getInstance().getTimeInMillis();
        SequenceBFS sequenceBFS = new SequenceBFS(matrix);
        sequenceBFS.make();
        finish = Calendar.getInstance().getTimeInMillis();
        System.out.println("Sequence Time " + (finish - start));


        start = Calendar.getInstance().getTimeInMillis();
        ParallelBFS parallelBFS = new ParallelBFS(matrix, countThreads);
        Thread[] subBFS = new Thread[countThreads];
        for (int i = 0; i < countThreads; i++) {
            subBFS[i] = new SubBFS(parallelBFS, i);
            subBFS[i].start();
        }
        for (int i = 0; i < countThreads; i++) {
            try {
                subBFS[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        finish = Calendar.getInstance().getTimeInMillis();
        System.out.println("Parallel Time " + (finish - start));
    }
}
