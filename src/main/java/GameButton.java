import javax.swing.*;

public class GameButton  extends JButton {
    private  int buttonIndex;
    private GameBoard board;

    public GameButton(int gameButtonIndex, GameBoard currentGameBoard) {
        this.buttonIndex = gameButtonIndex;
        this.board = currentGameBoard;

        int row = buttonIndex / currentGameBoard.getDimension();
        int column = buttonIndex % currentGameBoard.getDimension();

        setSize(currentGameBoard.cellSize - 5, currentGameBoard.cellSize -5);
        addActionListener(new GameActionListener(row, column, this)); // this - ссылка на текущую кнопку

    }

    public GameBoard getBoard () {
        return board;               // возвращает ссылку на игровое поле
    }
}
