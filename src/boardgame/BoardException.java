package boardgame;

public class BoardException extends RuntimeException {
    private static final long serialVersionUID = 1L; // Versão da exceção

    public BoardException(String msg){
        super(msg); // Repassar mensagem para o construtor da classe RuntimeException
    }

}
