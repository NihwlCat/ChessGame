package chess;

import boardgame.Board;
import boardgame.ChessException;
import boardgame.Piece;
import boardgame.Position;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ChessMatch {

    private Board board;
    private int turn;
    private Color jogAtual;
    private boolean cheque;
    private boolean chequeMate;

    private List<ChessPiece> pecasTabuleiro = new ArrayList<>();
    private List<ChessPiece> pecasCapturadas = new ArrayList<>();

    private ChessPiece enPassantVulneravel;
    private ChessPiece promocao;


    public ChessMatch(){
        board = new Board(8,8);
        turn = 1;
        jogAtual = Color.WHITE;
        initialSetup();
    }

    public ChessPiece getEnPassantVulneravel(){
        return enPassantVulneravel;
    }

    public ChessPiece getPromocao(){
        return promocao;
    }


    public boolean getCheque(){
        return cheque;
    }

    public boolean getChequeMate(){
        return chequeMate;
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

        ChessPiece movida = (ChessPiece)board.piece(target);

        // PROMOÇÃO
        promocao = null;
        if(movida instanceof Pawn){
            if((movida.getColor() == Color.WHITE && target.getLinha() == 0) || (movida.getColor() == Color.BLACK && target.getLinha() == 7)){
                promocao = (ChessPiece)board.piece(target);
                promocao = replacePiece("Q");
            }
        }


        cheque = (testeChuca(oponente(jogAtual))) ? true : false;

        if (testeChequeMate(oponente(jogAtual))){
            chequeMate = true;
        } else {
            nextTurn();
        }

        // enPassant

        if(movida instanceof Pawn && (target.getLinha() == source.getLinha() - 2 || target.getLinha() == source.getLinha() + 2)){
            enPassantVulneravel = movida;
        } else {
            enPassantVulneravel = null;
        }

        return (ChessPiece) peca_capturada;

    }

    public ChessPiece replacePiece(String tipo){
        if (promocao == null){
            throw new IllegalStateException("Nao tem peca a ser promovida");
        }

        if(!tipo.equals("B") && !tipo.equals("N") && !tipo.equals("R") && !tipo.equals("Q")){
            throw new InvalidParameterException("Tipo invalido para promocao");
        }

        Position pos = promocao.getChessPosition().toPosition();
        Piece p = board.removePiece(pos);
        pecasTabuleiro.remove(p);

        ChessPiece novaPeca = novaPeca(tipo, promocao.getColor());
        board.placePiece(novaPeca,pos);

        return novaPeca;

    }

    private ChessPiece novaPeca(String tipo, Color cor){
        if(tipo.equals("B")) return new Bishop(board,cor);
        if(tipo.equals("N")) return new Knight(board,cor);
        if(tipo.equals("Q")) return new Queen(board,cor);
        return new Rook(board,cor);
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
        ChessPiece p = (ChessPiece)board.removePiece(origem);
        p.plusMove();
        Piece t = board.removePiece(destino);

        if(t != null){
            pecasTabuleiro.remove(t);
            pecasCapturadas.add((ChessPiece)t); // Usando Downcast ou alterando o tipo da Lista.
        }

        board.placePiece(p,destino);
        // ROCADA PEQUENA
        if(p instanceof King && destino.getColuna() == origem.getColuna() + 2 ){
            Position sT = new Position(origem.getLinha(), origem.getColuna() +3);
            Position tT = new Position(origem.getLinha(), origem.getColuna() +1);
            ChessPiece rook = (ChessPiece)board.removePiece(sT);
            board.placePiece(rook, tT);
            rook.plusMove();
        }

        // ROCADA GRANDE
        if(p instanceof King && destino.getColuna() == origem.getColuna() - 2 ){
            Position sT = new Position(origem.getLinha(), origem.getColuna() -4);
            Position tT = new Position(origem.getLinha(), origem.getColuna() -1);
            ChessPiece rook = (ChessPiece)board.removePiece(sT);
            board.placePiece(rook, tT);
            rook.plusMove();
        }
        // enPassant TRATAMENTO
        if( p instanceof Pawn){
            if(origem.getColuna() != destino.getColuna() && t == null){
                Position posicaoPeao;
                if(p.getColor() == Color.WHITE){
                    posicaoPeao = new Position(destino.getLinha() + 1, destino.getColuna());
                } else {
                    posicaoPeao = new Position(destino.getLinha() - 1, destino.getColuna());
                }
                t = board.removePiece(posicaoPeao);
                pecasCapturadas.add((ChessPiece)t);
                pecasTabuleiro.remove(t);
            }
        }
        return t;
    }

    private void undoMove(Position origem, Position destino, Piece cap){
        ChessPiece p = (ChessPiece)board.removePiece(destino);
        p.minusMove();

        board.placePiece(p, origem);

        if(cap != null){
            board.placePiece(cap,destino);
            pecasCapturadas.remove(cap);
            pecasTabuleiro.add((ChessPiece)cap);
        }

        // ROCADA PEQUENA - DESFAZER
        if(p instanceof King && destino.getColuna() == origem.getColuna() +2){
            Position sT = new Position(origem.getLinha(), origem.getColuna() +3);
            Position tT = new Position(origem.getLinha(), origem.getColuna() +1);
            ChessPiece rook = (ChessPiece)board.removePiece(tT);
            board.placePiece(rook, sT);
            rook.minusMove();
        }

        // ROCADA GRANDE - DESFAZER
        if(p instanceof King && destino.getColuna() == origem.getColuna() -2){
            Position sT = new Position(origem.getLinha(), origem.getColuna() -4);
            Position tT = new Position(origem.getLinha(), origem.getColuna() -1);
            ChessPiece rook = (ChessPiece)board.removePiece(tT);
            board.placePiece(rook, sT);
            rook.minusMove();
        }
        // ESTUDAR MOVIMENTO ESPECIAL DE ROCADA!

        // enPassant TRATAMENTO - REMOÇÃO
        if( p instanceof Pawn){
            if(origem.getColuna() != destino.getColuna() && cap == enPassantVulneravel){
                ChessPiece pawn = (ChessPiece)board.removePiece(destino);
                Position posicaoPeao;
                if(p.getColor() == Color.WHITE){
                    posicaoPeao = new Position(3, destino.getColuna());
                } else {
                    posicaoPeao = new Position(4, destino.getColuna());
                }
                board.placePiece(pawn,posicaoPeao);
            }
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

    private boolean testeChequeMate(Color c){
        if(!testeChuca(c)){
            return false;
        }

        List<Piece> aux = pecasTabuleiro.stream().filter(x -> ((ChessPiece)x).getColor() == c).collect(Collectors.toList());
        for(Piece p : aux){
            boolean[][] mat = p.possiveisMovimentos();
            for(int i = 0; i < board.getLinhas(); i++){
                for(int j = 0; j < board.getColunas(); j++){
                    if(mat[i][j]){ // É um movimento possível?
                        // Esse movimento possível tira do cheque?
                        Position source = ((ChessPiece)p).getChessPosition().toPosition();
                        Position target = new Position (i,j);
                        Piece cap = makeMove(source,target);
                        boolean testCheck = testeChuca(c);
                        undoMove(source,target,cap);

                        if(!testCheck){ // Se o teste for false então o if se torna verdadeiro.
                            return false;
                        }

                    }
                }
            }

            // ANALISAR LÓGICA DE CHEQUEMATE
        }

        return true;

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
        placeNewPiece('a',1,new Rook(board,Color.WHITE));
        placeNewPiece('e',1,new King(board,Color.WHITE, this));
        placeNewPiece('h',1,new Rook(board,Color.WHITE));
        placeNewPiece('c',1,new Bishop(board,Color.WHITE));
        placeNewPiece('f',1,new Bishop(board,Color.WHITE));
        placeNewPiece('a',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('b',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('c',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('d',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('e',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('f',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('g',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('h',2,new Pawn(board,Color.WHITE,this));
        placeNewPiece('b',1,new Knight(board,Color.WHITE));
        placeNewPiece('g',1,new Knight(board,Color.WHITE));
        placeNewPiece('d',1,new Queen(board,Color.WHITE));

        placeNewPiece('a',8,new Rook(board,Color.BLACK));
        placeNewPiece('e',8,new King(board,Color.BLACK, this));
        placeNewPiece('h',8,new Rook(board,Color.BLACK));
        placeNewPiece('c',8,new Bishop(board,Color.BLACK));
        placeNewPiece('f',8,new Bishop(board,Color.BLACK));
        placeNewPiece('a',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('b',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('c',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('d',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('e',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('f',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('g',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('h',7,new Pawn(board,Color.BLACK,this));
        placeNewPiece('b',8,new Knight(board,Color.BLACK));
        placeNewPiece('g',8,new Knight(board,Color.BLACK));
        placeNewPiece('d',8,new Queen(board,Color.BLACK));
    }
}
