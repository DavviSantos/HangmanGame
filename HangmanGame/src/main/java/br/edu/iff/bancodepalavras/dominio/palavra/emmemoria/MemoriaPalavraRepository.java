package main.java.br.edu.iff.bancodepalavras.dominio.palavra.emmemoria;

import main.java.br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import main.java.br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.Tema;
import main.java.br.edu.iff.repository.RepositoryException;

import java.util.HashMap;
import java.util.Map;

public class MemoriaPalavraRepository implements PalavraRepository {
    private static MemoriaPalavraRepository soleInstance = null;
    private final Map<Long, Palavra> pool; 

    private MemoriaPalavraRepository() {
        this.pool = new HashMap<>();
    }

    public static MemoriaPalavraRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaPalavraRepository();
        }
        return soleInstance;
    }

    @Override
    public Palavra getPorId(long id) {
        return pool.get(id);
    }

    @Override
    public Palavra[] getPorTema(Tema tema) {
        return pool.values().stream()
                .filter(palavra -> palavra.getTema().equals(tema))
                .toArray(Palavra[]::new);
    }

    @Override
    public Palavra[] getTodas() {
        return pool.values().toArray(new Palavra[0]);
    }

    @Override
    public Palavra getPalavra(String palavra) {
        return pool.values().stream()
                .filter(p -> p.toString().equalsIgnoreCase(palavra))
                .findFirst()
                .orElse(null);
    }

    @Override
    public synchronized void inserir(Palavra palavra) throws RepositoryException {
        if (pool.containsKey(palavra.getId())) {
            throw new RepositoryException("Palavra já existe no repositório.");
        }
        pool.put(palavra.getId(), palavra); // Adiciona diretamente no HashMap
    }

    @Override
    public synchronized void atualizar(Palavra palavra) throws RepositoryException {
        if (!pool.containsKey(palavra.getId())) {
            throw new RepositoryException("Palavra não encontrada para atualização.");
        }
        pool.put(palavra.getId(), palavra); // Atualiza diretamente no HashMap
    }

    @Override
    public synchronized void remover(Palavra palavra) throws RepositoryException {
        if (pool.remove(palavra.getId()) == null) {
            throw new RepositoryException("Palavra não encontrada no repositório para remoção.");
        }
    }

    @Override
    public long getProximoId() {
        return pool.keySet().stream()
                   .max(Long::compare)
                   .orElse(0L) + 1;
    }
}
