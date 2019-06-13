package Main;

public class SpecialShape {

    public static int[][] shapeX(int row, int cloum) {
        int[][] indexSpecial3=new int[2][row+cloum];
        for(int i=0;i<cloum;i++){
            indexSpecial3[0][i]=i;
            indexSpecial3[1][i]=i;
        }
        for(int i=cloum;i<(row+cloum);i++){
            indexSpecial3[0][i]=2*cloum-i-1;
            indexSpecial3[1][i]=i-cloum;
        }
        return indexSpecial3;
    }


}
