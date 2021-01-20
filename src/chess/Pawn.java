package chess;

import boardgame.Board;
import boardgame.Position;

public class Pawn extends ChessPiece {

    public Pawn(Board board, Color color){
        super(board,color);
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
        Position p = new Position(0,0);

        if(getColor() == Color.WHITE){
            // Movimento padrão
            p.setValues(pos.getLinha() - 1, pos.getColuna());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Movimento padrão no início
            p.setValues(pos.getLinha() - 2, pos.getColuna());
            Position p2 = new Position(pos.getLinha() - 1, pos.getColuna());

            if(getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getContagemMov() == 0){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Diagonal Esquerda
            p.setValues(pos.getLinha() - 1, pos.getColuna() - 1);
            if(getBoard().positionExists(p) && haUmaPecaInimiga(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Diagonal Direita
            p.setValues(pos.getLinha() - 1, pos.getColuna() + 1);
            if(getBoard().positionExists(p) && haUmaPecaInimiga(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

        } else {
            // Movimento padrão
            p.setValues(pos.getLinha() + 1, pos.getColuna());
            if(getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Movimento padrão no início
            p.setValues(pos.getLinha() + 2, pos.getColuna());
            Position p2 = new Position(pos.getLinha() + 1, pos.getColuna());

            if(getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getContagemMov() == 0){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Diagonal Esquerda
            p.setValues(pos.getLinha() + 1, pos.getColuna() - 1);
            if(getBoard().positionExists(p) && haUmaPecaInimiga(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

            // Diagonal Direita
            p.setValues(pos.getLinha() + 1, pos.getColuna() + 1);
            if(getBoard().positionExists(p) && haUmaPecaInimiga(p)){
                mat[p.getLinha()][p.getColuna()] = true;
            }

        }
        return mat;

    }

    @Override
    public String toString(){
        return "P";
    }


}
