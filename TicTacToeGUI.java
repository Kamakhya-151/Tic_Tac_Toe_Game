import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class TicTacToeGUI {
    private JButton[][] buttons;
    private char currentPlayer;
    private boolean gameFinished;

    public TicTacToeGUI() {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        currentPlayer = 'X';
        gameFinished = false;

        initializeButtons(frame);

        frame.setVisible(true);
    }

    private void initializeButtons(JFrame frame) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton button = new JButton();
                button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
                button.addActionListener(new ButtonClickListener(row, col));
                buttons[row][col] = button;
                frame.add(button);
            }
        }
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            JButton clickedButton = (JButton) event.getSource();

            if (clickedButton.getText().equals("") && !gameFinished) {
                clickedButton.setText(String.valueOf(currentPlayer));

                if (checkWinConditions()) {
                    gameFinished = true;
                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                } else if (isBoardFull()) {
                    gameFinished = true;
                    JOptionPane.showMessageDialog(null, "It's a tie!");
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            }
        }

        private boolean checkWinConditions() {
            String[][] board = new String[3][3];

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = buttons[i][j].getText();
                }
            }

            if (board[row][0].equals(board[row][1]) && board[row][1].equals(board[row][2]) && !board[row][0].equals("")) {
                highlightWinningCells(row, 0, row, 1, row, 2);
                return true;
            } else if (board[0][col].equals(board[1][col]) && board[1][col].equals(board[2][col]) && !board[0][col].equals("")) {
                highlightWinningCells(0, col, 1, col, 2, col);
                return true;
            } else if (row == col && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].equals("")) {
                highlightWinningCells(0, 0, 1, 1, 2, 2);
                return true;
            } else if (row + col == 2 && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].equals("")) {
                highlightWinningCells(0, 2, 1, 1, 2, 0);
                return true;
            }

            return false;
        }

        private boolean isBoardFull() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j].getText().equals("")) {
                        return false;
                    }
                }
            }
            return true;
        }

        private void highlightWinningCells(int r1, int c1, int r2, int c2, int r3, int c3) {
            buttons[r1][c1].setBackground(Color.GREEN);
            buttons[r2][c2].setBackground(Color.GREEN);
            buttons[r3][c3].setBackground(Color.GREEN);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TicTacToeGUI();
            }
        });
    }
}

