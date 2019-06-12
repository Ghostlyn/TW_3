package Main;

import sun.awt.windows.ThemeReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class ui_test extends JFrame {

//    private JButton openFileBtn = new JButton("选择文件");
    private JButton startGameBtn = new JButton("开始游戏");
    private JLabel durationPromtLabel = new JLabel("动画间隔设置(ms为单位)");
    private JTextField durationTextField = new JTextField();
    private JLabel RowLabel = new JLabel("矩阵行数");
    private JTextField RowTextField = new JTextField();
    private JLabel ColumnLabel = new JLabel("矩阵列数");
    private JTextField ColumnTextField = new JTextField();

    private Transform transform = new Transform();
    private int[][] cellMatrix;
    /*  *
     * 游戏是否开始的标志
     */
    private boolean isStart = false;

    /**
     * 游戏结束的标志
     */
    private boolean stop = false;

    private static final int DEFAULT_DURATION = 200;

    //动画间隔
    private int duration = DEFAULT_DURATION;


    //    private CellMatrix cellMatrix;
    private JPanel buttonPanel = new JPanel(new GridLayout(4, 2));
    private JPanel gridPanel = new JPanel();

    Matrix text=new Matrix();


    private JTextField[][] textMatrix;


    public ui_test() throws Exception {
        setTitle("生命游戏");
//        cellMatrix = text.initMatrix(20,20);
        startGameBtn.addActionListener(new StartGameActioner());

//        buttonPanel.add(openFileBtn);
        buttonPanel.add(startGameBtn);
        buttonPanel.add(durationPromtLabel);
        buttonPanel.add(durationTextField);
        buttonPanel.add(RowLabel);
        buttonPanel.add(RowTextField);
        buttonPanel.add(ColumnLabel);
        buttonPanel.add(ColumnTextField);
        buttonPanel.setBackground(Color.WHITE);

        getContentPane().add("North", buttonPanel);
//        initGridLayout(cellMatrix);

        this.setSize(1500, 1800);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int count=100;
//        giveColor(cellMatrix);
//
//        while(count>0){
////            gridPanel.removeAll();
////            gridPanel.repaint();
////            initGridLayout(cellMatrix);
//            cellMatrix=transform.tran(cellMatrix);
////            Util.display(cellMatrix);
//            giveColor(cellMatrix);
////            gridPanel.revalidate();
//            Thread.sleep(500);
//            count--;
//        }
    }


    private void initGridLayout(int[][] cellMatrix) {
        int rows = cellMatrix.length;
        int cols = cellMatrix[0].length;
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(rows, cols));
        textMatrix = new JTextField[rows][cols];
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                JTextField text = new JTextField();
                textMatrix[y][x] = text;
                gridPanel.add(text);
            }
        }
        add("Center", gridPanel);
    }

    public void giveColor(int[][] cellMatrix) {
        int rows = cellMatrix.length;
        int cols = cellMatrix[0].length;
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (cellMatrix[y][x] == 1) textMatrix[y][x].setBackground(Color.BLACK);
                else textMatrix[y][x].setBackground(Color.WHITE);
            }
        }
    }

    private class StartGameActioner implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (!isStart) {
                //获取时间
                try {
                    duration = Integer.parseInt(durationTextField.getText().trim());
                    Integer row = Integer.parseInt(RowTextField.getText().trim());
                    Integer cloum = Integer.parseInt(RowTextField.getText().trim());
                    cellMatrix = text.initMatrix(row,cloum);
                    initGridLayout(cellMatrix);
                    giveColor(cellMatrix);
                } catch (NumberFormatException e1) {
                    duration = DEFAULT_DURATION;
                }
                System.out.println(duration);
                new Thread(new GameControlTask()).start();
                isStart = true;
                stop = false;
                startGameBtn.setText("暂停游戏");
            } else {
                stop = true;
                isStart = false;
                startGameBtn.setText("开始游戏");
            }
        }
    }

    private class GameControlTask implements Runnable {

        public void run() {
            while (!stop) {
                Util.display(cellMatrix);
                cellMatrix = transform.tran(cellMatrix);
                giveColor(cellMatrix);
//                System.out.println("--------");
                try {
                    TimeUnit.MILLISECONDS.sleep(duration);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
