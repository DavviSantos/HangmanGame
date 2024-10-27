package main.java.br.edu.iff.jogoforca.dominio.rodada;

import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import main.java.br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import main.java.br.edu.iff.factory.EntityFactory;


public abstract class RodadaFactoryImpl extends EntityFactory implements RodadaFactory {
    private TemaRepository temaRepository;
	private PalavraRepository palavraRepository;

	protected RodadaFactoryImpl(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
		super(repository);
		this.temaRepository = temaRepository;
		this.palavraRepository = palavraRepository;
	}

	protected RodadaRepository getRodadaRepository() {
		return (RodadaRepository) this.getRepository();
	}

	protected TemaRepository getTemaRepository() {
		return this.temaRepository;
	}

	protected PalavraRepository getPalavraRepository() {
		return this.palavraRepository;
	}
}
