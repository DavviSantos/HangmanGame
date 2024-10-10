package main.java.br.edu.iff.repository;

/**
 * Exceção personalizada para erros relacionados ao repositório.
 * 
 * Esta classe pode ser estendida por outras exceções específicas
 * dos repositórios no futuro.
 */
public class RepositoryException extends Exception {

    /**
     * Construtor padrão da exceção RepositoryException.
     */
    public RepositoryException() {
        super();
    }

    /**
     * Construtor com mensagem de erro.
     * 
     * @param message a mensagem de erro associada à exceção
     */
    public RepositoryException(String message) {
        super(message);
    }

    /**
     * Construtor com causa da exceção.
     * 
     * @param cause a causa raiz da exceção
     */
    public RepositoryException(Throwable cause) {
        super(cause);
    }

    /**
     * Construtor com mensagem de erro e causa da exceção.
     * 
     * @param message a mensagem de erro associada à exceção
     * @param cause a causa raiz da exceção
     */
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
