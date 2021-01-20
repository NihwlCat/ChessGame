package chess;

import boardgame.Board;
import boardgame.Position;

public class Queen extends ChessPiece{

    public Queen(Board board, Color color) {
        super(board, color);
    }

    @Override
    public boolean[][] possiveisMovimentos() {
        // A rainha é uma mistura de Bispo e Torre

        boolean[][] mat = new boolean[getBoard().getLinhas()][getBoard().getColunas()];
        Position p = new Position(0,0);

        // ACIMA
        p.setValues(pos.getLinha() - 1,pos.getColuna()); // Pos é a posição da peça
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getLinha()][p.getColuna()] =  true;
            p.setLinha(p.getLinha() - 1 );
        }
        if(getBoard().positionExists(p) && haUmaPecaInimiga(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        // ESQUERDA
        p.setValues(pos.getLinha(),pos.getColuna() - 1); // Pos é a posição da peça
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getLinha()][p.getColuna()] =  true;
            p.setColuna(p.getColuna() - 1 );
        }
        if(getBoard().positionExists(p) && haUmaPecaInimiga(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //DIREITA
        p.setValues(pos.getLinha(),pos.getColuna() + 1); // Pos é a posição da peça
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getLinha()][p.getColuna()] =  true;
            p.setColuna(p.getColuna() + 1 );
        }
        if(getBoard().positionExists(p) && haUmaPecaInimiga(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

        //BAIXO
        p.setValues(pos.getLinha() + 1,pos.getColuna()); // Pos é a posição da peça
        while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)){
            mat[p.getLinha()][p.getColuna()] =  true;
            p.setLinha(p.getLinha() + 1 );
        }
        if(getBoard().positionExists(p) && haUmaPecaInimiga(p)){
            mat[p.getLinha()][p.getColuna()] = true;
        }

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
        return "Q";
    }
}
