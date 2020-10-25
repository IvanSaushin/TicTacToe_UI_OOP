public class GamePlayer {
    private char playerSign;
    private boolean realPlayer;

    public GamePlayer(boolean realPlayer, char playerSign) {
        this.playerSign  =playerSign;
        this.realPlayer = realPlayer;
    }

    public boolean isRealPlayer() {
        return realPlayer;
    }

    public char getPlayerSign() {
        return playerSign;
    }
}