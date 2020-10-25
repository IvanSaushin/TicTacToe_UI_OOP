import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {

    static int dimension = 3;
    static int cellSize = 150;
    char[][] gameField;
    private GameButton[] gameButtons;

    static char nullSymbol = '\u0000'; // null символ

    private Game game;

    public GameBoard(Game currentGame) {
        this.game = currentGame;
        initField();
    }

    private void initField() {
        // задаем основные настройки окна игры
        setBounds(cellSize * dimension, cellSize*dimension, 400, 300);
        setTitle("Крестики нолики");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel controlPane = new JPanel(); // панель управления игрой
        JButton newGameButton = new JButton("New game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                emptyField();
            }
        });

        controlPane.setLayout(new BoxLayout(controlPane, BoxLayout.X_AXIS));
        controlPane.add(newGameButton);
        controlPane.setSize(cellSize*dimension, 150);

        JPanel gameFieldPanel = new JPanel();
        gameFieldPanel.setLayout(new GridLayout(dimension, dimension));
        gameFieldPanel.setSize(cellSize*dimension, cellSize*dimension);

        gameField = new char[dimension][dimension];
        gameButtons = new GameButton[dimension*dimension];

        for (int i = 0; i < gameButtons.length; i++) {
            GameButton fieldButton = new GameButton(i, this);// передаем текущее поле кнопки в ее конструктор
            gameFieldPanel.add(fieldButton); // добавляем кнопку на игровое поле
            gameButtons[i] = fieldButton; // добавляем ссылку кнопки в массив кнопок
        }

        getContentPane().add(controlPane, BorderLayout.NORTH);
        getContentPane().add(gameFieldPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    void emptyField() {
        for (int i = 0; i < (dimension*dimension); i++) {
            gameButtons[i].setText("");

            int row = i / GameBoard.dimension;
            int col = i % GameBoard.dimension;

            gameField[row][col] = nullSymbol;
        }
    }

    // Геттер для игры
    Game getGame() {
        return game;
    }

    // в клетку можно пойти
    boolean isTurnable(int x, int y) {
        boolean result = false;

        if (gameField[y][x] == nullSymbol) { result = true;}
        return result;
    }

    // обновление поля ходом
    void updateGameField(int x, int y) {
        gameField[y][x] = game.getCurrentPlayer().getPlayerSign();
    }

    // проверка победы
    boolean checkWin() {
        boolean result = false;

        char plS = getGame().getCurrentPlayer().getPlayerSign();

        if ( checkWinDiagonal(plS) || checkWinLines(plS) ) {
            result = true;
        }
        return result;
    }

    private boolean checkWinDiagonal(char playerSymbol) {
        boolean result = false;
        boolean leftRight = true;
        boolean rightLeft = true;

        for (int i = 0; i < dimension; i++) {
            leftRight &= (gameField[i][i] == playerSymbol);
            rightLeft &= (gameField[i][dimension-i-1] == playerSymbol);
        }
        if (leftRight || rightLeft) {
            result = true;
        }
        return result;
    }

    private boolean checkWinLines(char playerSymbol) {
        boolean result = false;

        for (int row = 0; row < dimension; row++) {
            boolean horizonLine = true;
            boolean verticalLine = true;

            for (int column = 0; column < dimension; column++) {
                horizonLine &= (gameField[row][column] == playerSymbol);
                verticalLine &= (gameField[column][row] == playerSymbol);
            }
            if (horizonLine || verticalLine) {
                result = true;
                break;
            }
        }
        return result;
    }

    // проверка поля на заполненность
    boolean isFull() {
        boolean result = true;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (gameField[i][j] == nullSymbol) {
                    result = false;
                }
            }
        }
        return  result;
    }

    public int getDimension() {
        return dimension;
    }

    public GameButton getButton( int buttonIndex) {
        return gameButtons[buttonIndex];
    }
}
