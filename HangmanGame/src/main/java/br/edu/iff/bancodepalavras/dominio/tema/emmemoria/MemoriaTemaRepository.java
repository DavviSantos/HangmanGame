package main.java.br.edu.iff.bancodepalavras.dominio.tema.emmemoria;

import java.util.HashMap;
import java.util.Map;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.Tema;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import main.java.br.edu.iff.repository.RepositoryException;

public class MemoriaTemaRepository implements TemaRepository {
    private static MemoriaTemaRepository soleInstance; // Instância única
    private final Map<Long, Tema> temaPool; // Pool de temas, usando ID como chave

    // Construtor privado
    private MemoriaTemaRepository() {
        this.temaPool = new HashMap<>();
    }

    // Método Singleton para obter a única instância de MemoriaTemaRepository
    public static MemoriaTemaRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new MemoriaTemaRepository();
        }
        return soleInstance;
    }

    @Override
    public synchronized void inserir(Tema tema) throws RepositoryException {
        
        if ((temaPool.containsKey(tema.getId())) ){
            throw new RepositoryException("Tema já existe na pool.");
        }
        for (Map.Entry<Long,Tema> temaDaPool : temaPool.entrySet()){
            if(tema.getNome().equals(temaDaPool.getValue().getNome())){
                throw new RepositoryException("Tema já existe na pool.");
            }
        }

        
        temaPool.put(tema.getId(), tema);
    }

    @Override
    public synchronized void atualizar(Tema tema) throws RepositoryException {
        if (!temaPool.containsKey(tema.getId())) {
            throw new RepositoryException("Tema não encontrado para atualização.");
        }
        
    }

    @Override
    public synchronized void remover(Tema tema) throws RepositoryException {
        if (!temaPool.containsKey(tema.getId())) {
            throw new RepositoryException("Tema não encontrado para remoção.");
        }
        temaPool.remove(tema.getId());
    }

    @Override
    public Tema getPorId(long id) {
        return temaPool.get(id); // Retorna o tema pelo ID, ou null se não encontrado
    }

    @Override
    public Tema[] getPorNome(String nome) {
        return temaPool.values().stream()
            .filter(tema -> tema.getNome().equalsIgnoreCase(nome))
            .toArray(Tema[]::new);
    }

    @Override
    public Tema[] getTodos() {
        return temaPool.values().toArray(new Tema[temaPool.size()]); // Usa um array do tamanho exato
    }

    @Override
    public long getProximoId() {
        return temaPool.keySet().stream().max(Long::compare).orElse(0L) + 1;
    }
}
