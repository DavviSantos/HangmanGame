package main.java.br.edu.iff.bancodepalavras.dominio.tema;

import main.java.br.edu.iff.repository.Repository;
import main.java.br.edu.iff.repository.RepositoryException;

public interface TemaRepository extends Repository {
    
    /**
     * Insere um novo tema no repositório.
     *
     * @param tema O tema a ser inserido.
     * @throws RepositoryException Se ocorrer um erro ao inserir o tema.
     */
    void inserir(Tema tema) throws RepositoryException;

    /**
     * Atualiza os dados de um tema existente no repositório.
     *
     * @param tema O tema com os dados atualizados.
     * @throws RepositoryException Se ocorrer um erro ao atualizar o tema.
     */
    void atualizar(Tema tema) throws RepositoryException;

    /**
     * Remove um tema do repositório.
     *
     * @param tema O tema a ser removido.
     * @throws RepositoryException Se ocorrer um erro ao remover o tema.
     */
    void remover(Tema tema) throws RepositoryException;

    /**
     * Obtém um Tema pelo seu nome.
     *
     * @param nome O nome do tema a ser buscado.
     * @return O tema correspondente ao nome fornecido.
     */
    Tema[] getPorNome(String nome);

    /**
     * Obtém um tema pelo seu ID.
     *
     * @param id O ID do tema a ser buscado.
     * @return O tema correspondente ao ID fornecido.
     */
    Tema getPorId(long id);

    /**
     * Obtém todos os temas do repositório.
     *
     * @return Um array com todos os temas.
     */
    Tema[] getTodos();
}
