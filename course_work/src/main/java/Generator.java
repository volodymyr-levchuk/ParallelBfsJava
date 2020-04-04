public class Generator {
    public static int[][] generateMatrix(Integer size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < matrix[0].length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i != j && Math.random() > 0.5) {
                    matrix[i][j] = 1;
                    matrix[j][i] = 1;
                }
            }
        }
//        for (int i = 0; i < matrix[0].length; i++) {
//            for (int j = 0; j < matrix.length; j++) {
//                System.out.print(matrix[i][j] + " ");
//            }
//            System.out.println();
//        }
        return matrix;
    }
}
