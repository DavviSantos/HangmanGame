package main.java.br.edu.iff.jogoforca.dominio.rodada.sorteio;

import java.util.Arrays;
import java.util.Random;

import main.java.br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import main.java.br.edu.iff.bancodepalavras.dominio.palavra.PalavraRepository;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.Tema;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import main.java.br.edu.iff.jogoforca.dominio.jogador.Jogador;
import main.java.br.edu.iff.jogoforca.dominio.rodada.Rodada;
import main.java.br.edu.iff.jogoforca.dominio.rodada.RodadaFactoryImpl;
import main.java.br.edu.iff.jogoforca.dominio.rodada.RodadaRepository;

public class RodadaSorteioFactory extends RodadaFactoryImpl {
    private static RodadaSorteioFactory soleInstance;
	
	public static void createSoleInstance(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
		if(soleInstance==null) {
			soleInstance = new RodadaSorteioFactory(repository, temaRepository, palavraRepository);
		}
	}
	
	public static RodadaSorteioFactory getSoleInstance() {
		if(soleInstance==null) {
			throw new RuntimeException("Precisa chamar o createSoleInstance primeiro.");
		}
		return soleInstance;
	}
	
	private RodadaSorteioFactory(RodadaRepository repository, TemaRepository temaRepository, PalavraRepository palavraRepository) {
		super(repository, temaRepository, palavraRepository);
	}

	@Override
	public Rodada getRodada(Jogador jogador) {
		Random random = new Random();
        Tema temaEsolhido = super.getTemaRepository().getTodos()[random.nextInt((int)super.getTemaRepository().getProximoId()-1)];
        int qtdPalavrasSorteadas = random.nextInt(3)+1;
        Palavra[] palavrasTema = super.getPalavraRepository().getPorTema(temaEsolhido);
        if(palavrasTema.length<qtdPalavrasSorteadas) {
        	throw new RuntimeException("Não há palavras o sufuciente no tema sorteado");
        }
        Palavra[] palavrasEscolhidas = new Palavra[qtdPalavrasSorteadas];
        for (int palavraAtual = 0; palavraAtual < qtdPalavrasSorteadas; palavraAtual++) {
			Palavra palavraSorteada;
        	do {
        		palavraSorteada = palavrasTema[random.nextInt(palavrasTema.length)];

        	}while(Arrays.asList(palavrasEscolhidas).contains(palavraSorteada));
        	palavrasEscolhidas[palavraAtual] = palavraSorteada;
		}

        return Rodada.criar(getRodadaRepository().getProximoId(), palavrasEscolhidas, jogador);
	}
}
