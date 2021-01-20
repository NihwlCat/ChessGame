package chess;

import boardgame.Board;
import boardgame.Position;

public class Knight extends ChessPiece {
    public Knight(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "N";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        // A movimentação do cavalo é no estilo 2 - 1 para todas as direções. É parecida com o Rei
        boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];

        Position p = new Position (0,0);

        p.setValues(pos.getLinha() - 1,pos.getColuna() - 2); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(pos.getLinha() - 2,pos.getColuna() - 1); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(pos.getLinha() - 2,pos.getColuna() + 1); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(pos.getLinha() - 1,pos.getColuna() + 2); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(pos.getLinha() + 1,pos.getColuna() + 2); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(pos.getLinha() + 2,pos.getColuna() + 1); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(pos.getLinha() + 2,pos.getColuna() - 1); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        p.setValues(pos.getLinha() + 1,pos.getColuna() - 2); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }

    private boolean canMove(Position p){
        ChessPiece cp = (ChessPiece) getBoard().piece(p);
        return cp == null || cp.getColor() != getColor();

    }
}
