package main.java.br.edu.iff.jogoforca.embdr;

import main.java.br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import main.java.br.edu.iff.jogoforca.RepositoryFactory;
import main.java.br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import main.java.br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;

public class BDRRepositoryFactory implements RepositoryFactory{

    private static BDRRepositoryFactory soleInstance;
	
	public static BDRRepositoryFactory getSoleInstance() {
		if(soleInstance==null) {
			soleInstance = new BDRRepositoryFactory();
		}
		return soleInstance;
	}
	
	private BDRRepositoryFactory() {
		
	}

    @Override
    public PalavraRepository getPalavraRepository() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPalavraRepository'");
    }

    @Override
    public TemaRepository getTemaRepository() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTemaRepository'");
    }

    @Override
    public RodadaRepository getRodadaRepository() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRodadaRepository'");
    }

    @Override
    public JogadorRepository getJogadorRepository() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getJogadorRepository'");
    }
    
}
