package chess;

import boardgame.Board;
import boardgame.Position;

public class King extends ChessPiece{
    public King(Board board, Color color) {
        super(board, color);
    }

    @Override
    public String toString(){
        return "K";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];

        Position p = new Position (0,0);

        // ACIMA
        p.setValues(pos.getLinha() - 1,pos.getColuna()); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // ABAIXO
        p.setValues(pos.getLinha() + 1,pos.getColuna()); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // ESQUERDA
        p.setValues(pos.getLinha(),pos.getColuna() - 1); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // ESQUERDA
        p.setValues(pos.getLinha(),pos.getColuna() + 1); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // NW
        p.setValues(pos.getLinha() - 1,pos.getColuna() - 1); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // NE
        p.setValues(pos.getLinha() - 1,pos.getColuna() + 1); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // SW
        p.setValues(pos.getLinha() + 1,pos.getColuna() - 1); // Pos é a posição da peça
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }


        // SE
        p.setValues(pos.getLinha() + 1,pos.getColuna() + 1); // Pos é a posição da peça
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
