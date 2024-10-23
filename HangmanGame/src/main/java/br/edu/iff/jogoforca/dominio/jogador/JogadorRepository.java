package main.java.br.edu.iff.jogoforca.dominio.jogador;

import main.java.br.edu.iff.repository.Repository;
import main.java.br.edu.iff.repository.RepositoryException;

public interface JogadorRepository extends Repository {

    /**
     * Insere um novo jogador no repositório.
     * 
     * @param jogador O jogador a ser inserido.
     * @throws RepositoryException Se ocorrer um erro ao inserir o jogador.
     */
    void inserir(Jogador jogador) throws RepositoryException;

    /**
     * Atualiza os dados de um jogador existente no repositório.
     * 
     * @param jogador O jogador com os dados atualizados.
     * @throws RepositoryException Se ocorrer um erro ao atualizar o jogador.
     */
    void atualizar(Jogador jogador) throws RepositoryException;

    /**
     * Remove um jogador do repositório.
     * 
     * @param jogador O jogador a ser removido.
     * @throws RepositoryException Se ocorrer um erro ao remover o jogador.
     */
    void remover(Jogador jogador) throws RepositoryException;

    /**
     * Obtém um jogador pelo seu nome.
     * 
     * @param nome O nome do jogador a ser buscado.
     * @return O jogador correspondente ao nome fornecido.
     */
    Jogador getPorNome(String nome);

    /**
     * Obtém um jogador pelo seu ID.
     * 
     * @param id O ID do jogador a ser buscado.
     * @return O jogador correspondente ao ID fornecido.
     */
    Jogador getPorId(long id);
 
}
