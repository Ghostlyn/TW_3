package Main;

import sun.awt.windows.ThemeReader;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class ui_test extends JFrame {

    private JButton startGameBtn = new JButton("开始新游戏");
    private JButton pauseGameBtn = new JButton("暂停游戏");
//    private JButton startSpecialGameBtn = new JButton("定制初始化");
    private JLabel durationPromtLabel = new JLabel("动画间隔设置(ms为单位)", JLabel.CENTER);
    private JTextField durationTextField = new JTextField();
    private JLabel RowLabel = new JLabel("矩阵行数",JLabel.CENTER);
    private JTextField RowTextField = new JTextField();
    private JLabel ColumnLabel = new JLabel("矩阵列数",JLabel.CENTER);
    private JTextField ColumnTextField = new JTextField();


    private Transform transform = new Transform();
    private int[][] cellMatrix;
    /**
     * 游戏是否开始的标志
     */
    private boolean isStart = false;

    private boolean isPause = false;

    /**
     * 游戏结束的标志
     */
    private static volatile boolean stop = false;

    private static final int DEFAULT_DURATION = 1000;
    private static final int DEFAULT_ROW = 10;
    private static final int DEFAULT_COL = 10;
    //动画间隔
    private int duration = DEFAULT_DURATION;
    private int row = DEFAULT_ROW;
    private int col = DEFAULT_COL;

    //    private CellMatrix cellMatrix;
    private JPanel buttonPanel = new JPanel(new GridLayout(5, 2));
    private JPanel gridPanel = new JPanel();

    Matrix text = new Matrix();


    private JTextField[][] textMatrix;


    private boolean speicalOn = false;

    public ui_test() throws Exception {
        setTitle("生命游戏");
//        cellMatrix = text.initMatrix(20,20);
        startGameBtn.addActionListener(new StartGameActioner());
//        startSpecialGameBtn.addActionListener(new StartSpecialGameActioner());
        pauseGameBtn.addActionListener(new PauseGameActioner());
//        buttonPanel.add(openFileBtn);
        buttonPanel.add(startGameBtn);
        buttonPanel.add(pauseGameBtn);
//        buttonPanel.add(startSpecialGameBtn);
        buttonPanel.add(durationPromtLabel);
        buttonPanel.add(durationTextField);
        buttonPanel.add(RowLabel);
        buttonPanel.add(RowTextField);
        buttonPanel.add(ColumnLabel);
        buttonPanel.add(ColumnTextField);

        buttonPanel.setBackground(Color.WHITE);

        getContentPane().add("North", buttonPanel);
//        initGridLayout(cellMatrix);

        this.setSize(1500, 1200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        durationTextField.setHorizontalAlignment(JTextField.CENTER);
        RowTextField.setHorizontalAlignment(JTextField.CENTER);
        ColumnTextField.setHorizontalAlignment(JTextField.CENTER);
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
            stop = true;
//            if (!isStart) {
                System.out.println("11111");
                //获取时间
                try {
                    duration = Integer.parseInt(durationTextField.getText().trim());
                    row = Integer.parseInt(RowTextField.getText().trim());
                    col = Integer.parseInt(RowTextField.getText().trim());
                } catch (NumberFormatException e1) {
                    duration = DEFAULT_DURATION;
                    row = DEFAULT_ROW;
                    col = DEFAULT_COL;
                }
                stop = false;
                isPause = false;
                System.out.println(duration);
                System.out.println("isStart:" + isStart);
                System.out.println("isPause:" + isPause);
                System.out.println("isStop:" + stop);
                cellMatrix = text.initMatrix(row,col);
                initGridLayout(cellMatrix);
                gridPanel.revalidate();
                giveColor(cellMatrix);
                new Thread(new GameControlTask()).start();
//                isStart = true;
//                stop = false;
                pauseGameBtn.setText("暂停游戏");
//            }
//            else {
//                System.out.println("2222");
//                stop = true;
//                isStart = false;
//                startGameBtn.setText("开始游戏");
//            }
        }
    }

//    private class StartSpecialGameActioner implements ActionListener {
//        public void actionPerformed(ActionEvent e) {
//            System.out.println(speicalOn);
//            if (!speicalOn) {
//                try {
//                    duration = Integer.parseInt(durationTextField.getText().trim());
//                    row = Integer.parseInt(RowTextField.getText().trim());
//                    col = Integer.parseInt(RowTextField.getText().trim());
//                } catch (NumberFormatException e1) {
//                    duration = DEFAULT_DURATION;
//                    row = DEFAULT_ROW;
//                    col = DEFAULT_COL;
//                }
//                cellMatrix = new int[row][col];
//                initGridLayout(cellMatrix);
//                gridPanel.revalidate();
//                speicalOn = true;
//            } else {
//                try {
//                    for (int i = 0; i < row; i++) {
//                        for (int j = 0; j < col; j++) {
//                            Document doc = textMatrix[i][j].getDocument();
//                            if (doc.getLength() > 0) {
//                                System.out.println(doc.getText(0, doc.getLength()));
//                                cellMatrix[i][j] = 1;
//                            }
//                        }
//                    }
//                    Util.display(cellMatrix);
//                    System.out.println("qhgwfevasjbdfjhsbf" +
//                            "");
//                    giveColor(cellMatrix);
//                    speicalOn = false;
//                    new Thread(new GameControlTask()).start();
//
//                } catch (Exception e1) {
//                    e1.printStackTrace();
//                }
//
//            }
//
//        }
//    }

    private class PauseGameActioner implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!isPause) {
                isPause = true;
                stop = true;
                System.out.println("isStart:" + isStart);
                System.out.println("isPause:" + isPause);
                System.out.println("isStop:" + stop);
                pauseGameBtn.setText("继续游戏");
            }
            else {
                isPause = false;
                stop = false;
                new Thread(new GameControlTask()).start();
                System.out.println("isStart:" + isStart);
                System.out.println("isPause:" + isPause);
                System.out.println("isStop:" + stop);
                pauseGameBtn.setText("暂停游戏");
            }
        }
    }

    private class GameControlTask implements Runnable {
        public void run() {
            while (!stop) {
                Util.display(cellMatrix);
                cellMatrix = transform.tran(cellMatrix);
                giveColor(cellMatrix);
                try {
                    TimeUnit.MILLISECONDS.sleep(duration);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
