package boardgame;

public class Board { // As linhas do tabuleiro não devem ser alteradas após a instancia.
    private int linhas;
    private int colunas;

    private Piece[][] pieces;

    public Board(int linhas, int colunas){
        if(linhas < 1 || colunas < 1){
            throw new BoardException("Erro ao criar tabuleiro. É necessário uma linha e uma coluna ao menos!");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        pieces = new Piece[linhas][colunas];
    }

    public int getLinhas() {
        return linhas;
    }

    public int getColunas() {
        return colunas;
    }

    public Piece piece(int l, int c){
        if(!positionExists(l,c)){
            throw new BoardException(String.format("Posição [%d] [%d] não existe no tabuleiro!",l,c));
        }
        return pieces[l][c];
    }

    public Piece piece (Position p){
        if(!positionExists(p)){
            throw new BoardException(String.format("Posição [%d] [%d] não existe no tabuleiro!",p.getLinha(),p.getColuna()));
        }
        return pieces[p.getLinha()][p.getColuna()];
        // Retorna a peça na matriz peças na posição indicada
    }

    public void placePiece(Piece piece, Position p){
        if(thereIsAPiece(p)){
            throw new BoardException("Já existe uma peça nessa ai: " + p.toString());
        }
        pieces[p.getLinha()][p.getColuna()] = piece;
        piece.pos = p;

    }
    private boolean positionExists(int linha, int coluna){
        return (linha >= 0 && linha < linhas) && (coluna >= 0 && coluna < colunas);
    }

    public boolean positionExists(Position p){
        return positionExists(p.getLinha(),p.getColuna());
    }

    public boolean thereIsAPiece(Position p){
        if(!positionExists(p)){
            throw new BoardException(String.format("Posição [%d] [%d] não existe no tabuleiro!",p.getLinha(),p.getColuna()));
        }
        return piece(p) != null;
    }

    public Piece removePiece(Position p){
        if(!positionExists(p)){
            throw new BoardException(String.format("Posição [%d] [%d] não existe no tabuleiro!",p.getLinha(),p.getColuna()));
        }
        if(piece(p) == null){ // Caso a posição esteja vazia retornar null
            return null;
        }
        Piece aux = piece(p);
        aux.pos = null;
        pieces[p.getLinha()][p.getColuna()] = null;
        return aux;
    }


}
