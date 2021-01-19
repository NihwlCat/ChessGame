package chess;

import boardgame.Board;
import boardgame.ChessException;
import boardgame.Piece;
import boardgame.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private Board board;
    private int turn;
    private Color jogAtual;
    private boolean cheque;

    private List<ChessPiece> pecasTabuleiro = new ArrayList<>();
    private List<ChessPiece> pecasCapturadas = new ArrayList<>();


    public ChessMatch(){
        board = new Board(8,8);
        turn = 1;
        jogAtual = Color.WHITE;
        initialSetup();
    }

    public boolean getCheque(){
        return cheque;
    }

    public int getTurn(){
        return turn;
    }

    public Color getJogAtual(){
        return jogAtual;
    }

    public ChessPiece[][] getPieces(){
        ChessPiece[][] m = new ChessPiece[board.getLinhas()][board.getColunas()];
        for(int i=0;i<board.getLinhas();i++){
            for(int j=0;j<board.getColunas();j++){
                m[i][j] = (ChessPiece) board.piece(i,j); // Retorna a matriz pieces na posição i e j (Downcasting)
            }
        }
        return m;
    }

    public ChessPiece performarMovimento(ChessPosition origem, ChessPosition destino){
        Position source = origem.toPosition();
        Position target = destino.toPosition();

        validateSourcePosition(source);
        validateTargetPosition(source,target);

        Piece peca_capturada = makeMove(source,target);

        if(testeChuca(jogAtual)){
            undoMove(origem.toPosition(),destino.toPosition(),peca_capturada);
            throw new ChessException("Você não pode se colocar em cheque.");
        }

        cheque = (testeChuca(oponente(jogAtual))) ? true : false;


        nextTurn();

        return (ChessPiece) peca_capturada;

    }

    public boolean[][] movimentosPossiveis(ChessPosition origem){
        Position p = origem.toPosition();
        validateSourcePosition(p);
        return board.piece(p).possiveisMovimentos();

    }
    private void validateTargetPosition(Position s, Position t){
        if(!board.piece(s).movimentoPossivel(t)){ // Tentar entender isso aqui
            throw new ChessException("A peça escolhida não pode se mover para a posição de destino");
        }

    }

    private void nextTurn(){
        turn++;
        jogAtual = (jogAtual == Color.WHITE)? Color.BLACK : Color.WHITE; // Operador ternário
    }
    private void validateSourcePosition(Position source){
        if(!board.thereIsAPiece(source)){
            throw new ChessException("Não existe peça na posição de origem");
        }

        if(jogAtual != ((ChessPiece)board.piece(source)).getColor()){
            throw new ChessException("A peça escolhida não é sua");
        }

        if(!board.piece(source).haPossivelMovimento()){ // Acessa a peça na posição do tabuleiro e dentro da peça há um atributo que testa se há movimentos possíveis
            throw new ChessException("Não existe movimentos possíveis para a peça!");

        }
    }

    private Piece makeMove(Position origem, Position destino){
        Piece p = board.removePiece(origem);
        Piece t = board.removePiece(destino);

        if(t != null){
            pecasTabuleiro.remove(t);
            pecasCapturadas.add((ChessPiece)t); // Usando Downcast ou alterando o tipo da Lista.
        }

        board.placePiece(p,destino);
        return t;
    }

    private void undoMove(Position origem, Position destino, Piece cap){
        Piece p = board.removePiece(destino);
        board.placePiece(p, origem);

        if(cap != null){
            board.placePiece(cap,destino);
            pecasCapturadas.remove(cap);
            pecasTabuleiro.add((ChessPiece)cap);
        }

    }

    private Color oponente(Color c){
        return (c == Color.WHITE)? Color.BLACK : Color.WHITE;
    }

    private ChessPiece localKing(Color color){
        List<Piece> aux = pecasTabuleiro.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
        for(Piece p : aux){
            if( p instanceof  King){
                return (ChessPiece)p;
            }

        }
        throw new IllegalStateException("Erro. Não há rei " + color + " no tabuleiro.");

    }

    private boolean testeChuca(Color c){
        Position kP = localKing(c).getChessPosition().toPosition();
        List<Piece> oponenteP = pecasTabuleiro.stream().filter(x -> ((ChessPiece)x).getColor() == oponente(c)).collect(Collectors.toList());
        for(Piece p : oponenteP){
            boolean[][] mat = p.possiveisMovimentos();
            if(mat[kP.getLinha()][kP.getColuna()]){
                return true;
            }
        }
        return false;

        /*
        É criada uma posição kP que recebe o Rei da cor do jogador na posição de xadrez
        convertida para posição de matriz.

        É criada uma lista com as peças do oponente.

        Um for percorre a lista de peças do oponente. Uma matriz booleana recebe os movimentos possíveis da peça P.

        Se nessa matriz, a posição correspondente a posição do rei for True significa que ele está sendo atacado por uma peça.

        Caso percorra as peças do oponente e nenhuma posição esteja coincidindo com a posição do rei (false) então não está em cheque.
        */
    }

    private void placeNewPiece(char coluna, int linha, ChessPiece piece){
        board.placePiece(piece,new ChessPosition(coluna,linha).toPosition()); // Instanciando uma posição de xadrez e uma peça e logo em seguida chamando o método para converter para Position
        pecasTabuleiro.add(piece);
    }


    private void initialSetup(){
        //board.placePiece(new Rook(board,Color.WHITE),new Position(2,1));
        //board.placePiece(new King(board,Color.BLACK),new Position(2,1));
        //board.placePiece(new King(board,Color.BLACK),new Position(9,4));
        placeNewPiece('c',1,new Rook(board,Color.WHITE));
        placeNewPiece('c',2,new Rook(board,Color.WHITE));
        placeNewPiece('d',2,new Rook(board,Color.WHITE));
        placeNewPiece('e',2,new Rook(board,Color.WHITE));
        placeNewPiece('e',1,new Rook(board,Color.WHITE));
        placeNewPiece('d',1,new King(board,Color.WHITE));
        placeNewPiece('c',7,new Rook(board,Color.BLACK));
        placeNewPiece('c',8,new Rook(board,Color.BLACK));
        placeNewPiece('d',7,new Rook(board,Color.BLACK));
        placeNewPiece('e',7,new Rook(board,Color.BLACK));
        placeNewPiece('e',8,new Rook(board,Color.BLACK));
        placeNewPiece('d',8,new King(board,Color.BLACK));
    }
}
