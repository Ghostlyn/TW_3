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

public class LifeGame extends JFrame {

    private JButton startGameBtn = new JButton("开始新游戏");
    private JButton pauseGameBtn = new JButton("暂停游戏");
    private JButton startSpecialGameBtn = new JButton("定制初始化");
    private JLabel durationPromtLabel = new JLabel("动画间隔设置(ms为单位)", JLabel.CENTER);
    private JTextField durationTextField = new JTextField();
    private JLabel RowLabel = new JLabel("矩阵行数",JLabel.CENTER);
    private JTextField RowTextField = new JTextField();
    private JLabel ColumnLabel = new JLabel("矩阵列数",JLabel.CENTER);
    private JTextField ColumnTextField = new JTextField();


    private JButton xButton = new JButton("X");

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

    public LifeGame() throws Exception {
        setTitle("生命游戏");
        startGameBtn.addActionListener(new StartGameActioner());
        startSpecialGameBtn.addActionListener(new StartSpecialGameActioner());
        pauseGameBtn.addActionListener(new PauseGameActioner());

        xButton.addActionListener(new ShapeActioner());

        buttonPanel.add(startGameBtn);
        buttonPanel.add(startSpecialGameBtn);
        buttonPanel.add(durationPromtLabel);
        buttonPanel.add(durationTextField);
        buttonPanel.add(RowLabel);
        buttonPanel.add(RowTextField);
        buttonPanel.add(ColumnLabel);
        buttonPanel.add(ColumnTextField);
        buttonPanel.add(pauseGameBtn);

        buttonPanel.add(xButton);

        buttonPanel.setBackground(Color.WHITE);

        getContentPane().add("North", buttonPanel);

        this.setSize(1500, 1200);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        durationTextField.setHorizontalAlignment(JTextField.CENTER);
        RowTextField.setHorizontalAlignment(JTextField.CENTER);
        ColumnTextField.setHorizontalAlignment(JTextField.CENTER);
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
            pauseGameBtn.setText("暂停游戏");
        }
    }

    private class StartSpecialGameActioner implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println(speicalOn);
            if (!speicalOn) {
                try {
                    duration = Integer.parseInt(durationTextField.getText().trim());
                    row = Integer.parseInt(RowTextField.getText().trim());
                    col = Integer.parseInt(ColumnTextField.getText().trim());
                } catch (NumberFormatException e1) {
                    duration = DEFAULT_DURATION;
                    row = DEFAULT_ROW;
                    col = DEFAULT_COL;
                }
                System.out.println("row: " + row + " col: " + col);
                cellMatrix = new int[row][col];
                initGridLayout(cellMatrix);
                giveColor(cellMatrix);
                gridPanel.revalidate();
                speicalOn = true;
            } else {
                try {
                    cellMatrix = new int[row][col];
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < col; j++) {
                            Document doc = textMatrix[i][j].getDocument();
                            if (doc.getLength() > 0) {
                                System.out.println(doc.getText(0, doc.getLength()));
                                cellMatrix[i][j] = 1;
                            }
                            textMatrix[i][j].setText("");
                        }
                    }
                    Util.display(cellMatrix);
                    giveColor(cellMatrix);
                    speicalOn = false;
                    new Thread(new GameControlTask()).start();

                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }

        }
    }


    private class ShapeActioner implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                duration = Integer.parseInt(durationTextField.getText().trim());
                row = Integer.parseInt(RowTextField.getText().trim());
                col = Integer.parseInt(ColumnTextField.getText().trim());
            } catch (NumberFormatException e1) {
                duration = DEFAULT_DURATION;
                row = DEFAULT_ROW;
                col = DEFAULT_COL;
            }
            int[][] shapeX = SpecialShape.shapeX(row,col);
            cellMatrix = text.initSpecialMatrix(row,col, shapeX);
            initGridLayout(cellMatrix);
            gridPanel.revalidate();
            giveColor(cellMatrix);
            new Thread(new GameControlTask()).start();
            pauseGameBtn.setText("暂停游戏");
        }
    }

    private class PauseGameActioner implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (!isPause) {
                isPause = true;
                stop = true;
                pauseGameBtn.setText("继续游戏");
            }
            else {
                isPause = false;
                stop = false;
                new Thread(new GameControlTask()).start();
                pauseGameBtn.setText("暂停游戏");
            }
        }
    }

    private class GameControlTask implements Runnable {
        public void run() {
            while (!stop) {
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
