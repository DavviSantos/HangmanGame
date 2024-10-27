package main.java.br.edu.iff.jogoforca.dominio.jogador;

import main.java.br.edu.iff.factory.EntityFactory;

public class JogadorFactoryImpl extends EntityFactory implements JogadorFactory {

    private static JogadorFactoryImpl soleInstance;

    public static void createSoleInstance(JogadorRepository repository) {
		if(soleInstance==null) {
			soleInstance = new JogadorFactoryImpl(repository);
		}
	}

    public static JogadorFactoryImpl getSoleInstance() {
		if(soleInstance==null) {
			throw new RuntimeException("Instancia de JogadorFactorImpl não encontrada");
		}
		return soleInstance;
	}

    private JogadorFactoryImpl(JogadorRepository repository) {
		super(repository);
	}

    private JogadorRepository getJogadorRepository() {
        return (JogadorRepository) this.getRepository();
    }
    @Override
    protected long getProximoId() {
        
        return this.getJogadorRepository().getProximoId();
    }

    @Override
    public Jogador getJogador(String nome) {
        return Jogador.criar(this.getJogadorRepository().getProximoId(), nome);
    }
    
}
