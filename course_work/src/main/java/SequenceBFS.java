import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;

public class SequenceBFS {
    private int[][] matrix;
    private boolean[] visited;
    private Queue<Integer> queue;
    private int startVertex;
    public SequenceBFS(int[][] matrix){
        this.matrix = matrix;
        this.visited = new boolean[matrix.length];
        this.queue = new PriorityQueue();
        this.startVertex = 0;
        Arrays.fill(this.visited, false);
    }
    public void make() {
        queue.add(startVertex);
        while(!queue.isEmpty()){
            int node = queue.poll();
            if(visited[node]==false){
              //    System.out.println(node);
                visited[node] = true;
                for(int i = 0; i < matrix.length; i++){
                    if(node==i)continue;
                    if(matrix[node][i] == 1 && visited[i]==false){
                        queue.add(i);
                    }
                }
            }
        }
    }

}
