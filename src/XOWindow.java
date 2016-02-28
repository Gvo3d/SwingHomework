import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Gvozd on 28.02.2016.
 */

class XOWindow extends JFrame {
    private Random rnd = new Random();
    private int gamestart = 0;
    private int tempper = 0;
    ArrayList<JButton> jbt = new ArrayList<>();
    JLabel label = new JLabel();
    private boolean gameEnded = false;
    XOWindow thisClass;

    public XOWindow() {
        super("XO");
        setCenter();
        ArrayList<Color> clr = randomizeButtonColor();
        int ti = 0;
        int tj = 0;
        for (int i = 0; i < 9; i++) {
            JButton jbutton = new JButton("");
            jbutton.setBackground(clr.get(i));
            jbutton.setFocusable(false);
            jbt.add(jbutton);
        }
        Dimension btndim = new Dimension(30, 30);
        label.setForeground(clr.get(9));
        label.setBackground(clr.get(10));
        label.setText("Начать игру!");
        FlowLayout flwl = new FlowLayout();
        GridLayout grdl = new GridLayout();
        grdl.setColumns(3);
        grdl.setRows(3);
        JPanel labelPanel = new JPanel(flwl, true);
        JPanel buttonsPanel = new JPanel(grdl, true);
        labelPanel.add(label);
        for (JButton jbtn : jbt) {
            buttonsPanel.add(jbtn);
            jbtn.setVisible(true);
        }

        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(buttonsPanel, BorderLayout.NORTH);
        add(labelPanel, BorderLayout.SOUTH);
        buttonsPanel.setVisible(true);
        labelPanel.setVisible(true);

        setSize(150, 155);
        setResizable(false);
        setVisible(true);

        for (JButton jbtn : jbt) {
            jbtn.setSize(btndim);
            jbtn.setMaximumSize(btndim);
            jbtn.setMinimumSize(btndim);
            jbtn.setPreferredSize(btndim);
        }
    
        label.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (gamestart == 0) {
                    gamestart = rnd.nextInt(9);
                    gamestart++;
                    buttonsInitialize();
                } else {
                    String caption;
                    switch (tempper) {
                        case 0:
                            caption = "Чё?";
                            break;
                        case 1:
                            caption = "Харе клацать!";
                            break;
                        case 2:
                            caption = "Нарываешься?";
                            break;
                        case 3:
                            caption = "Я тебе щас клацну!";
                            break;
                        case 4:
                            caption = "Ну давай, давай...";
                            break;
                        case 5:
                            caption = "Не устал ещё";
                            break;
                        case 6:
                            caption = "Э, мешаешь думать!";
                            break;
                        case 7:
                            caption = "Дост$%#...";
                            break;
                        default:
                            caption = "RuntimeException";
                            break;
                    }
                    tempper++;
                    labelSetText(caption);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

    }

    private void setCenter() {
        this.setLocationRelativeTo(null);
    }

    private void labelSetText(String text) {
        int tempcolor = rnd.nextInt(15);
        label.setForeground(getColor(tempcolor));
        label.setText(text);
    }

    private ArrayList<Color> randomizeButtonColor() {
        ArrayList<Color> colorResult = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            int temporaryColorNumber = rnd.nextInt(15);
            Color tempColor = getColor(temporaryColorNumber);
            colorResult.add(tempColor);
        }
        return colorResult;
    }

    private Color getColor(int number) {
        switch (number) {
            case 1:
                return new Color(43, 56, 211);
            case 2:
                return new Color(233, 18, 14);
            case 3:
                return new Color(33, 179, 26);
            case 4:
                return new Color(207, 239, 31);
            case 5:
                return new Color(237, 107, 158);
            case 6:
                return new Color(15, 21, 128);
            case 7:
                return new Color(142, 39, 49);
            case 8:
                return new Color(6, 112, 19);
            case 9:
                return new Color(191, 105, 40);
            case 10:
                return new Color(171, 63, 160);
            case 11:
                return new Color(92, 211, 210);
            case 12:
                return new Color(238, 146, 144);
            case 13:
                return new Color(147, 238, 107);
            case 14:
                return new Color(238, 168, 64);
            case 15:
                return new Color(235, 143, 238);
            default:
                return new Color(9, 5, 9);
        }
    }

    private void buttonsInitialize() {
        label.setBackground(new Color(207, 211, 206));
        for (JButton jbtn : jbt) {
            jbtn.setBackground(new Color(247, 245, 240));
            jbtn.setText("");
            jbtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (jbtn.getText() == "") {
                        jbtn.setForeground(new Color(239, 237, 44));
                        jbtn.setBackground(new Color(33, 179, 26));
                        jbtn.setText("X");
                        gameEndCheck();
                        enemyMove();
                    } else labelSetText("Занято!");
                }
            });
        }
        gameStarts();
    }

    private void gameStarts() {
        if (gamestart < 6) {
            labelSetText("Я хожу первым!");
            enemyMove();
        } else {
            labelSetText("Ты ходишь!");
        }
    }

    private void enemyMove() {
        noOneWinsCheck();
        boolean isMoved = false;
        if (gamestart % 2 != 0) {
            sillyMove();
            isMoved = true;
        }
        if ((!isMoved) && (jbt.get(4).getText() == "")) {
            labelSetText("Ха! Средяя МОЯ!");
            markEnemyMove(jbt.get(4));
            isMoved = true;
        }
        if (!isMoved) {
            isMoved = gridCalculation("O");
        }
        if (!isMoved) {
            isMoved = gridCalculation("X");
        }
        if (!isMoved) {
            sillyMove();
        }
    }

    private void sillyMove() {
        int temp = 0;
        while (true) {
            temp = rnd.nextInt(9);
            if (jbt.get(temp).getText() == "") {
                markEnemyMove(jbt.get(temp));
                break;
            }
        }
    }

    private void markEnemyMove(JButton jbtnEnemy) {
        jbtnEnemy.setForeground(new Color(68, 93, 239));
        jbtnEnemy.setBackground(new Color(218, 51, 51));
        jbtnEnemy.setText("O");
        gameEndCheck();
    }

    private void gameEndCheck() {
        boolean result = checkFields("X");
        if (result) {
            labelSetText("Ты победил!");
            gameClose();
        }
        result = checkFields("O");
        if (result) {
            labelSetText("УРА! Я ПОБЕДИЛ!");
            gameClose();
        }
        noOneWinsCheck();
    }

    private void noOneWinsCheck() {
        int endingtemp = 0;
        for (int i = 0; i < 9; i++) {
            if (jbt.get(i).getText() != "") {
                endingtemp++;
            }
        }
        if (endingtemp > 8) {
            labelSetText("Оба просрали :(");
            gameClose();
        }
    }

    private void gameClose() {
        for (JButton jbtnexit : jbt) {
            for (ActionListener al : jbtnexit.getActionListeners()) {
                jbtnexit.removeActionListener(al);
            }
        }
        gameEnded = true;
    }

    private boolean checkFields(String x) {
        if ((jbt.get(0).getText().equals(x)) && (jbt.get(1).getText().equals(x)) && (jbt.get(2).getText().equals(x))) {
            return true;
        } else if ((jbt.get(0).getText().equals(x)) && (jbt.get(4).getText().equals(x)) && (jbt.get(8).getText().equals(x))) {
            return true;
        }
        if ((jbt.get(0).getText().equals(x)) && (jbt.get(3).getText().equals(x)) && (jbt.get(6).getText().equals(x))) {
            return true;
        }
        if ((jbt.get(1).getText().equals(x)) && (jbt.get(4).getText().equals(x)) && (jbt.get(7).getText().equals(x))) {
            return true;
        }
        if ((jbt.get(2).getText().equals(x)) && (jbt.get(4).getText().equals(x)) && (jbt.get(6).getText().equals(x))) {
            return true;
        }
        if ((jbt.get(2).getText().equals(x)) && (jbt.get(5).getText().equals(x)) && (jbt.get(8).getText().equals(x))) {
            return true;
        }
        if ((jbt.get(3).getText().equals(x)) && (jbt.get(4).getText().equals(x)) && (jbt.get(5).getText().equals(x))) {
            return true;
        }
        if ((jbt.get(6).getText().equals(x)) && (jbt.get(7).getText().equals(x)) && (jbt.get(8).getText().equals(x))) {
            return true;
        }
        return false;
    }

    private boolean gridCalculation(String pointer) {
        String crossPointer;
        if (pointer == "O") {
            crossPointer = "X";
        } else crossPointer = "O";
        for (int tempi = 0; tempi < 9; tempi++) {
            if (jbt.get(tempi).getText() != crossPointer) {

                switch (tempi) {

                    case 0:
                        if ((jbt.get(1).getText().equals(pointer)) && (jbt.get(2).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(0));
                            return true;
                        } else if ((jbt.get(3).getText().equals(pointer)) && (jbt.get(6).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(0));
                            return true;
                        } else if ((jbt.get(4).getText().equals(pointer)) && (jbt.get(8).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(0));
                            return true;
                        }
                        break;

                    case 1:
                        if ((jbt.get(0).getText().equals(pointer)) && (jbt.get(2).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(1));
                            return true;
                        } else if ((jbt.get(4).getText().equals(pointer)) && (jbt.get(7).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(1));
                            return true;
                        }
                        break;

                    case 2:
                        if ((jbt.get(0).getText().equals(pointer)) && (jbt.get(1).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(2));
                            return true;
                        } else if ((jbt.get(4).getText().equals(pointer)) && (jbt.get(6).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(2));
                            return true;
                        } else if ((jbt.get(5).getText().equals(pointer)) && (jbt.get(8).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(2));
                            return true;
                        }
                        break;

                    case 3:
                        if ((jbt.get(0).getText().equals(pointer)) && (jbt.get(6).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(3));
                            return true;
                        } else if ((jbt.get(4).getText().equals(pointer)) && (jbt.get(5).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(3));
                            return true;
                        }
                        break;


                    case 4:
                        if ((jbt.get(0).getText().equals(pointer)) && (jbt.get(8).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(4));
                            return true;
                        } else if ((jbt.get(1).getText().equals(pointer)) && (jbt.get(7).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(4));
                            return true;
                        } else if ((jbt.get(2).getText().equals(pointer)) && (jbt.get(6).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(4));
                            return true;
                        } else if ((jbt.get(3).getText().equals(pointer)) && (jbt.get(5).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(4));
                            return true;
                        }
                        break;

                    case 5:
                        if ((jbt.get(2).getText().equals(pointer)) && (jbt.get(8).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(5));
                            return true;
                        } else if ((jbt.get(3).getText().equals(pointer)) && (jbt.get(4).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(5));
                            return true;
                        }
                        break;

                    case 6:
                        if ((jbt.get(0).getText().equals(pointer)) && (jbt.get(3).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(6));
                            return true;
                        } else if ((jbt.get(2).getText().equals(pointer)) && (jbt.get(4).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(6));
                            return true;
                        } else if ((jbt.get(7).getText().equals(pointer)) && (jbt.get(8).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(6));
                            return true;
                        }
                        break;

                    case 7:
                        if ((jbt.get(1).getText().equals(pointer)) && (jbt.get(4).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(7));
                            return true;
                        } else if ((jbt.get(6).getText().equals(pointer)) && (jbt.get(8).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(7));
                            return true;
                        }
                        break;

                    case 8:
                        if ((jbt.get(0).getText().equals(pointer)) && (jbt.get(4).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(8));
                            return true;
                        } else if ((jbt.get(2).getText().equals(pointer)) && (jbt.get(5).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(8));
                            return true;
                        } else if ((jbt.get(6).getText().equals(pointer)) && (jbt.get(7).getText().equals(pointer))) {
                            markEnemyMove(jbt.get(8));
                            return true;
                        }
                        break;

                }
            }
        }
        return false;
    }


}