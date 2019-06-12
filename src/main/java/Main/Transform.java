package Main;

/**
 * 根据规则可知:
 * 1. 不论生死状态, <2 & >3 死
 * 2. 不论生死状态, ==2 不变
 * 3. 不论生死状态, ==3 活
 */
public class Transform {

    public int[][] tran(int[][] matrix) {
        int[][] nextMatrix = new int[matrix.length][matrix[0].length];
        for (int i = 0; i< matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (countNums(matrix, i, j) == 2) nextMatrix[i][j] = matrix[i][j];
                if (countNums(matrix, i, j) == 3) nextMatrix[i][j] = 1;
            }
        }
        return nextMatrix;
    }

    public int countNums(int[][] matrix, int i, int j) {
        int count = 0;
        //左上角
        if (i != 0 && j != 0) count += matrix[i-1][j-1];
        //右上角
        if (i != 0 && j != matrix[i].length-1) count += matrix[i-1][j+1];
        //左下角
        if (i != matrix.length-1 && j != 0) count += matrix[i+1][j-1];
        //右下角
        if (i != matrix.length-1 && j != matrix[i].length-1) count += matrix[i+1][j+1];
        //上边
        if (i != 0) count += matrix[i-1][j];
        //下边
        if (i != matrix.length-1) count += matrix[i+1][j];
        //左边
        if (j != 0) count += matrix[i][j-1];
        //右边
        if (j != matrix[i].length-1) count += matrix[i][j+1];
        return count;
    }


}
