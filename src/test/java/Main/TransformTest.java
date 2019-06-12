package Main;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransformTest {

    //private int[][] testMatrix = new int[3][3];//{{0,0,0},{0,1,0},{0,0,0}};
    int[][] indexSpecial =new int[][]{{1,2,4,5},{1,2,1,8}};
    Matrix testSpecialMatrix=new Matrix();



    @Test
    public void testCellStatus() {

        int[][] init = testSpecialMatrix.initMatrix(30,30);
        Util.display(init);

        Transform transform = new Transform();
        System.out.println();
        int[][] next;
        int num=100;
        while(num>0){
            num--;
            init = transform.tran(init);
            Util.display(init);
            System.out.println();
        }

    }

}