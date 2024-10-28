package main.java.br.edu.iff.jogoforca.emmemoria;

import main.java.br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import main.java.br.edu.iff.bancodepalavras.dominio.palavra.emmemoria.MemoriaPalavraRepository;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.emmemoria.MemoriaTemaRepository;
import main.java.br.edu.iff.jogoforca.RepositoryFactory;
import main.java.br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import main.java.br.edu.iff.jogoforca.dominio.jogador.emmemoria.MemoriaJogadorRepository;
import main.java.br.edu.iff.jogoforca.dominio.rodada.emmemoria.MemoriaRodadaRepository;
import main.java.br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;

public class MemoriaRepositoryFactory implements RepositoryFactory {
    
    private static MemoriaRepositoryFactory soleInstance;
	
	public static MemoriaRepositoryFactory getSoleInstance() {
		if(soleInstance==null) {
			soleInstance = new MemoriaRepositoryFactory();
		}
		return soleInstance;
	}
	
	private MemoriaRepositoryFactory() {
		
	}

	@Override
	public PalavraRepository getPalavraRepository() {
		return MemoriaPalavraRepository.getSoleInstance();
	}

	@Override
	public TemaRepository getTemaRepository() {
		return MemoriaTemaRepository.getSoleInstance();
	}

	@Override
	public RodadaRepository getRodadaRepository() {
		return MemoriaRodadaRepository.getSoleInstance();
	}

	@Override
	public JogadorRepository getJogadorRepository() {
		return MemoriaJogadorRepository.getSoleInstance();
	}
}
