package main.java.br.edu.iff.bancodepalavras.dominio.palavra;

import main.java.br.edu.iff.bancodepalavras.dominio.tema.Tema;
import main.java.br.edu.iff.repository.Repository;
import main.java.br.edu.iff.repository.RepositoryException;

public interface PalavraRepository extends Repository {

    public Palavra getPorId(long id);
    public Palavra[] getPorTema(Tema tema);
    public Palavra[] getTodas();
    public Palavra getPalavra(String palavra);
    public void inserir (Palavara palavra) throws RepositoryException;
    public void atualizar (Palavara palavra) throws RepositoryException;
    public void remover (Palavara palavra) throws RepositoryException;
    
}
