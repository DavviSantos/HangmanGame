package main.java.br.edu.iff.jogoforca;

import main.java.br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import main.java.br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import main.java.br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;

public interface RepositoryFactory {
    public PalavraRepository getPalavraRepository();
	
	public TemaRepository getTemaRepository();
	
	public RodadaRepository getRodadaRepository();
	
	public JogadorRepository getJogadorRepository();
}
