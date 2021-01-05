package chess;

import boardgame.Board;
import boardgame.ChessException;
import boardgame.Piece;
import boardgame.Position;

public class ChessMatch {

    private Board board;

    public ChessMatch(){
        board = new Board(8,8);
        initialSetup();
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

        return (ChessPiece) peca_capturada;

    }
    private void validateTargetPosition(Position s, Position t){
        if(!board.piece(s).movimentoPossivel(t)){ // Tentar entender isso aqui
            throw new ChessException("A peça escolhida não pode se mover para a posição de destino");
        }

    }
    private void validateSourcePosition(Position source){
        if(!board.thereIsAPiece(source)){
            throw new ChessException("Não existe peça na posição de origem");
        }

        if(!board.piece(source).haPossivelMovimento()){ // Acessa a peça na posição do tabuleiro e dentro da peça há um atributo que testa se há movimentos possíveis
            throw new ChessException("Não existe movimentos possíveis para a peça!");

        }
    }

    private Piece makeMove(Position origem, Position destino){
        Piece p = board.removePiece(origem);
        Piece t = board.removePiece(destino);

        board.placePiece(p,destino);
        return t;
    }

    private void placeNewPiece(char coluna, int linha, ChessPiece piece){
        board.placePiece(piece,new ChessPosition(coluna,linha).toPosition()); // Instanciando uma posição de xadrez e uma peça e logo em seguida chamando o método para converter para Position

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
