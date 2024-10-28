package main.java.br.edu.iff.jogoforca.dominio.jogador;

public class JogadorNaoEncontradoException extends RuntimeException {
    /**
     * Construtor padrão da exceção JogadorNaoEncontradoException.
     */
    public JogadorNaoEncontradoException() {
        super();
    }

    /**
     * Construtor com mensagem de erro.
     * 
     * @param message a mensagem de erro associada à exceção
     */
    public JogadorNaoEncontradoException(String message) {
        super(message);
    }

    /**
     * Construtor com causa da exceção.
     * 
     * @param cause a causa raiz da exceção
     */
    public JogadorNaoEncontradoException(Throwable cause) {
        super(cause);
    }

    /**
     * Construtor com mensagem de erro e causa da exceção.
     * 
     * @param message a mensagem de erro associada à exceção
     * @param cause a causa raiz da exceção
     */
    public JogadorNaoEncontradoException(String message, Throwable cause) {
        super(message, cause);
    }
}
