package boardgame;

public abstract class Piece {
    protected Position pos; // Posição da peça na matriz

    private Board board;

    public Piece(Board board){
        this.board = board;
        pos = null;
    }

    protected Board getBoard(){
        return board;
    }

    public abstract boolean[][] possiveisMovimentos();

    public boolean movimentoPossivel(Position p){
        return possiveisMovimentos()[p.getLinha()][p.getColuna()]; // HookMetods (EU N ENTENDI NADA DISSO SE FODER)
    }

    public boolean haPossivelMovimento(){
        boolean[][] mat = possiveisMovimentos();
        for(int i = 0;i<mat.length; i++){
            for(int j = 0;j<mat.length; j++){
                if(mat[i][j]){
                    return true;
                }
            }
        }
        return false;
    }


}
