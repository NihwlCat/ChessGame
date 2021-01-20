package chess;

import boardgame.Board;
import boardgame.Position;
import javafx.geometry.Pos;

public class King extends ChessPiece{
    private ChessMatch chessMatch; // Dependência para ChessMatch

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    private boolean testRookRocada(Position p){
        ChessPiece cP = (ChessPiece)getBoard().piece(p);
        return cP != null && cP instanceof Rook && cP.getColor() == getColor() && cP.getContagemMov() == 0;
    }

    @Override
    public String toString(){
        return "K";
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];

        Position p = new Position (0,0);

        //ROCADA
        if(getContagemMov() == 0 && !chessMatch.getCheque()){
            // ROCADA PEQUENA
            Position posTorre1 =  new Position (pos.getLinha(),pos.getColuna() + 3);
            if(testRookRocada(posTorre1)){
                Position p1 = new Position (pos.getLinha(),pos.getColuna() + 1);
                Position p2 = new Position (pos.getLinha(),pos.getColuna() + 2);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null){
                    mat[pos.getLinha()][pos.getColuna() + 2] = true;
                }
            }

            // ROCADA GRANDE
            Position posTorre2 =  new Position (pos.getLinha(),pos.getColuna() - 4);
            if(testRookRocada(posTorre2)){
                Position p1 = new Position (pos.getLinha(),pos.getColuna() - 1);
                Position p2 = new Position (pos.getLinha(),pos.getColuna() - 2);
                Position p3 = new Position (pos.getLinha(),pos.getColuna() - 3);
                if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null){
                    mat[pos.getLinha()][pos.getColuna() - 2] = true;
                }
            }
        }

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
