import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameActionListener implements ActionListener {

    private int row;
    private int col;
    private GameButton button;

    public GameActionListener(int row, int col, GameButton button) {
        this.row = row;
        this.col = col;
        this.button = button;
    }

        @Override
    public void actionPerformed(ActionEvent actionEvent) {
        GameBoard board = button.getBoard();

        if (board.isTurnable(row, col)) {
            updateByPlayersData(board);

            if (board.isFull()) {
                board.getGame().showMessage("Ничья!");
                board.emptyField();
            } else {
                updateByAIDate(board);
            }
        } else {
            board.getGame().showMessage("Некорректный ход");
        }
    }

    private void updateByAIDate(GameBoard board) {
        // генерация координат хода компьютера
        int x, y;
        Random rnd = new Random();

        do {
            x = rnd.nextInt(GameBoard.dimension);
            y = rnd.nextInt(GameBoard.dimension);
        } while (!board.isTurnable(x, y));

        // обновить матрицу игры
        board.updateGameField(x, y);

        // обновить содержимое кнопки
        int cellIndex = GameBoard.dimension * x + y;
        board.getButton(cellIndex).setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));

        // проверить победу
        if (board.checkWin()) {
            button.getBoard().getGame().showMessage("Компьюетр победил!");
            board.emptyField();
        } else {
            // передать ход
            board.getGame().passTurn();
        }
    }

    private void updateByPlayersData(GameBoard board) {
        // обновить матрицу игры
        board.updateGameField(row, col);

        button.setText(Character.toString(board.getGame().getCurrentPlayer().getPlayerSign()));
        if (board.checkWin()) {
            button.getBoard().getGame().showMessage("Вы выиграли!");
            board.emptyField();
        } else {
            board.getGame().passTurn();
        }
    }
}