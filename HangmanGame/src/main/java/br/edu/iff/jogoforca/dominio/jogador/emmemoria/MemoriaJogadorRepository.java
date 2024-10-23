package main.java.br.edu.iff.jogoforca.dominio.jogador.emmemoria;

import java.util.HashMap;
import java.util.Map;
import main.java.br.edu.iff.jogoforca.dominio.jogador.Jogador;
import main.java.br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import main.java.br.edu.iff.repository.RepositoryException;

public class MemoriaJogadorRepository implements JogadorRepository {
    private static MemoriaJogadorRepository soleInstance;
    private final Map<Long, Jogador> pool; // Pool de jogadores

    // Construtor privado para o padrão Singleton
    private MemoriaJogadorRepository() {
        pool = new HashMap<>();
    }

    // Método para obter a instância única
    public static MemoriaJogadorRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaJogadorRepository();
        }
        return soleInstance;
    }

    @Override
    public synchronized void inserir(Jogador jogador) throws RepositoryException {
        if (pool.containsKey(jogador.getId())) {
            throw new RepositoryException("Jogador já existe");
        }
        pool.put(jogador.getId(), jogador);
    }

    @Override
    public synchronized void atualizar(Jogador jogador) throws RepositoryException {
        if (!pool.containsKey(jogador.getId())) {
            throw new RepositoryException("Jogador não encontrado");
        }
        pool.put(jogador.getId(), jogador);
    }

    @Override
    public synchronized void remover(Jogador jogador) throws RepositoryException {
        if (!pool.containsKey(jogador.getId())) {
            throw new RepositoryException("Jogador não encontrado");
        }
        pool.remove(jogador.getId());
    }

    @Override
    public Jogador getPorId(long id) {
        return pool.get(id);
    }

    @Override
    public Jogador getPorNome(String nome) {
        for (Jogador jogador : pool.values()) {
            if (jogador.getNome().equals(nome)) {
                return jogador;
            }
        }
        return null; // Jogador não encontrado
    }

    @Override
    public long getProximoId() {
        return pool.keySet().stream().max(Long::compare).orElse(0L) + 1;
    }
}
