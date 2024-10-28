package main.java.br.edu.iff.bancodepalavras.dominio.palavra;

import main.java.br.edu.iff.bancodepalavras.dominio.tema.Tema;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import main.java.br.edu.iff.repository.RepositoryException;

public class PalavraAppService {
    private static PalavraAppService soleInstance;
    private TemaRepository temaRepository;
    private PalavraRepository palavraRepository;
    private PalavraFactory palavraFactory;

    private PalavraAppService(TemaRepository temaRepository, PalavraRepository palavraRepository, PalavraFactory palavraFactory) {
        this.temaRepository = temaRepository;
        this.palavraRepository = palavraRepository;
        this.palavraFactory = palavraFactory;
    }

    public static PalavraAppService getSoleInstance() {
        if (soleInstance == null) {
            throw new IllegalStateException("Instância ainda não foi criada.");
        }
        return soleInstance;
    }

    public static void createSoleInstance(TemaRepository temaRepository, PalavraRepository palavraRepository, PalavraFactory palavraFactory) {
        if (soleInstance == null) {
            soleInstance = new PalavraAppService(temaRepository, palavraRepository, palavraFactory);
        } else {
            throw new IllegalStateException("Instância já foi criada.");
        }
    }

    // Método para adicionar nova palavra
    public boolean novaPalavra(String palavra, long idTema) {
        // Verifica se a palavra não é nula ou vazia
        if (palavra == null || palavra.trim().isEmpty()) {
            throw new IllegalArgumentException("A palavra não pode ser nula ou vazia.");
        }

        // Verifica se a palavra já existe no repositório
        Palavra palavraExistente = palavraRepository.getPalavra(palavra);
        if (palavraExistente != null) {
            return true; // A palavra já existe, não faz nada
        }

        // Acessa o repositório de Tema para obter o Tema a partir de idTema
        Tema tema = temaRepository.getPorId(idTema);
        if (tema == null) {
            throw new IllegalArgumentException("Tema com ID " + idTema + " não encontrado.");
        }

        // Cria a nova palavra
        Palavra novaPalavra = palavraFactory.getPalavra(palavra, tema);
        try {
            // Insere a nova palavra no repositório
            palavraRepository.inserir(novaPalavra);
            return true; // Palavra inserida com sucesso
        } catch (RepositoryException e) {
            return false; // Ocorreu uma exceção ao tentar inserir
        }
    }
}
