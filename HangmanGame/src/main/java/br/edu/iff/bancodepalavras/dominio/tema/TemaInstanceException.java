package main.java.br.edu.iff.bancodepalavras.dominio.tema;

public class TemaInstanceException extends RuntimeException {
    
    
    public TemaInstanceException(){
        super();
    }

    public TemaInstanceException(String message) {
        super(message);
    }

    public TemaInstanceException(Throwable cause) {
        super(cause);
    }

    public TemaInstanceException(String message, Throwable cause) {
        super(message, cause);
    }

}
