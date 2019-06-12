package Main;

public class Matrix {

    public int[][] initMatrix(int row, int clo) {
        int[][] matrix = new int[row][clo];

        for (int i = 0; i< matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = Math.random() > 0.5 ? 0 : 1;
            }
        }

        return matrix;
    }



    public int[][] initSpecialMatrix(int row, int clo,int[][] indexSpecial) {
        int[][] matrix = new int[row][clo];

        for (int i = 0; i< matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = 0;
            }
        }

        for(int i=0;i<indexSpecial[0].length;i++){
            matrix[indexSpecial[0][i]] [indexSpecial[1][i]] =1;
        }

        return matrix;
    }

}
