package main.java.br.edu.iff.repository;

/**
 * Interface Repository que define o contrato para os repositórios,
 * incluindo o método para obter o próximo ID disponível.
 */
public interface Repository {

    /**
     * Método para obter o próximo ID disponível para um novo objeto de domínio.
     * 
     * @return o próximo ID disponível
     */
    long getProximoId();
}
