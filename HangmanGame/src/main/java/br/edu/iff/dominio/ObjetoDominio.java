package main.java.br.edu.iff.dominio;

/**
 * Interface ObjetoDominio que define um contrato para todas as entidades de domínio.
 */
public interface ObjetoDominio {
    
    /**
     * Método para obter o identificador único da entidade.
     * 
     * @return o identificador único da entidade
     */
    long getId();
}
