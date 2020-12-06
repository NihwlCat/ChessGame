package boardgame;

public class Piece {
    protected Position pos; // Posição da peça na matriz

    private Board board;

    public Piece(Board board){
        this.board = board;
        pos = null;
    }

    protected Board getBoard(){
        return board;
    }


}
