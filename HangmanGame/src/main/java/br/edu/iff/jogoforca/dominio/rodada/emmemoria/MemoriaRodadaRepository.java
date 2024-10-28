package main.java.br.edu.iff.jogoforca.dominio.rodada.emmemoria;

import java.util.HashMap;
import java.util.Map;
import main.java.br.edu.iff.jogoforca.dominio.jogador.Jogador;
import main.java.br.edu.iff.jogoforca.dominio.rodada.Rodada;
import main.java.br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;
import main.java.br.edu.iff.repository.RepositoryException;

public class MemoriaRodadaRepository implements RodadaRepository {
    private static MemoriaRodadaRepository soleInstance;
    private final Map<Long, Rodada> rodadaPool;

    private MemoriaRodadaRepository() {
        this.rodadaPool = new HashMap<>();
    }

    public static MemoriaRodadaRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaRodadaRepository();
        }
        return soleInstance;
    }

    @Override
    public synchronized long getProximoId() {
        return rodadaPool.keySet().stream().max(Long::compare).orElse(0L) + 1;
    }

    @Override
    public synchronized Rodada getPorId(long id) {
        return rodadaPool.get(id); // Retorna a rodada pelo ID, ou null se não encontrado
    }

    @Override
    public synchronized Rodada[] getPorJogador(Jogador jogador) {
        return rodadaPool.values().stream()
            .filter(rodada -> rodada.getJogador().equals(jogador)) // Filtra rodadas pelo jogador
            .toArray(Rodada[]::new);
    }

    @Override
    public synchronized void inserir(Rodada rodada) throws RepositoryException {
        if (rodadaPool.containsKey(rodada.getId())) {
            throw new RepositoryException("Rodada já existe na pool.");
        }
        rodadaPool.put(rodada.getId(), rodada);
    }

    @Override
    public synchronized void atualizar(Rodada rodada) throws RepositoryException {
        if (!rodadaPool.containsKey(rodada.getId())) {
            throw new RepositoryException("Rodada não encontrada para atualização.");
        }
        rodadaPool.put(rodada.getId(), rodada);
    }

    @Override
    public synchronized void remover(Rodada rodada) throws RepositoryException {
        if (!rodadaPool.containsKey(rodada.getId())) {
            throw new RepositoryException("Rodada não encontrada para remoção.");
        }
        rodadaPool.remove(rodada.getId());
    }
}
