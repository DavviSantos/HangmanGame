package main.java.br.edu.iff.dominio;

/**
 * Classe abstrata que implementa a interface ObjetoDominio, fornecendo
 * a implementação do método getId() e um construtor para inicializar o ID.
 */
public abstract class ObjetoDominioImpl implements ObjetoDominio {

    // Atributo privado para o identificador único do objeto de domínio
    private final long id;

    /**
     * Construtor da classe ObjetoDominioImpl, que inicializa o ID do objeto.
     * 
     * @param id o identificador único do objeto de domínio
     */
    public ObjetoDominioImpl(long id) {
        this.id = id;
    }

    /**
     * Implementação do método getId() da interface ObjetoDominio.
     * Retorna o identificador único do objeto de domínio.
     * 
     * @return o ID do objeto de domínio
     */
    @Override
    public long getId() {
        return this.id;
    }
}