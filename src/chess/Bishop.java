package chess;

import boardgame.Board;
import boardgame.Position;

public class Bishop extends ChessPiece {

    public Bishop(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
        Position p = new Position(0,0);

        // Parecido com a Torre, só alterar o comportamento dos movimentos!

        // NOROESTE
        p.setValues(pos.getLinha() - 1,pos.getColuna() - 1); // Pos é a posição da peça
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getLinha()][p.getColuna()] =  true;
            p.setValues(p.getLinha() - 1,p.getColuna() - 1);
        }
        if(getBoard().positionExists(p) && haUmaPecaInimiga(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // NORDESTE
        p.setValues(pos.getLinha() - 1 ,pos.getColuna() + 1); // Pos é a posição da peça
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getLinha()][p.getColuna()] =  true;
            p.setValues(p.getLinha() - 1,p.getColuna() + 1);
        }
        if(getBoard().positionExists(p) && haUmaPecaInimiga(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //SUDESTE

        p.setValues(pos.getLinha() + 1,pos.getColuna() + 1); // Pos é a posição da peça
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getLinha()][p.getColuna()] =  true;
            p.setValues(p.getLinha() + 1,p.getColuna() + 1);
        }
        if(getBoard().positionExists(p) && haUmaPecaInimiga(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //SUDOESTE
        p.setValues(pos.getLinha() + 1,pos.getColuna() - 1); // Pos é a posição da peça
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getLinha()][p.getColuna()] =  true;
            p.setValues(p.getLinha() + 1,p.getColuna() - 1);
        }
        if(getBoard().positionExists(p) && haUmaPecaInimiga(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        return mat;
    }



    @Override
    public String toString(){
        return "B";
    }
}
