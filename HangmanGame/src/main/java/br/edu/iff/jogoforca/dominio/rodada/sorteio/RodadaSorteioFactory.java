package main.java.br.edu.iff.jogoforca.dominio.rodada.sorteio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

	public static void createSoleInstance(RodadaRepository repository, TemaRepository temaRepository,
			PalavraRepository palavraRepository) {
		if (soleInstance == null) {
			soleInstance = new RodadaSorteioFactory(repository, temaRepository, palavraRepository);
		}
	}

	public static RodadaSorteioFactory getSoleInstance() {
		if (soleInstance == null) {
			throw new RuntimeException("Precisa chamar o createSoleInstance primeiro.");
		}
		return soleInstance;
	}

	private RodadaSorteioFactory(RodadaRepository repository, TemaRepository temaRepository,
			PalavraRepository palavraRepository) {
		super(repository, temaRepository, palavraRepository);
	}

	@Override
	public Rodada getRodada(Jogador jogador) {
		Random random = new Random();

		// Obter todos os temas disponíveis
		Tema[] temas = super.getTemaRepository().getTodos();
		if (temas.length == 0) {
			throw new RuntimeException("Não há temas disponíveis");
		}

		// Escolher um tema aleatório
		Tema temaEscolhido = temas[random.nextInt(temas.length)];

		// Obter palavras do tema
		Palavra[] palavrasTema = super.getPalavraRepository().getPorTema(temaEscolhido);
		if (palavrasTema.length < 1) {
			throw new RuntimeException("Não há palavras disponíveis para o tema: " + temaEscolhido.getNome());
		}

		// Determinar quantas palavras serão usadas (entre 1 e 3, ou o máximo
		// disponível)
		int maxPalavrasPossiveis = Math.min(3, palavrasTema.length);
		int qtdPalavrasSorteadas = random.nextInt(maxPalavrasPossiveis) + 1;

		// Selecionar palavras aleatórias sem repetição
		List<Palavra> palavrasEscolhidas = new ArrayList<>();
		List<Palavra> palavrasDisponiveis = new ArrayList<>(Arrays.asList(palavrasTema));

		for (int i = 0; i < qtdPalavrasSorteadas; i++) {
			int index = random.nextInt(palavrasDisponiveis.size());
			palavrasEscolhidas.add(palavrasDisponiveis.remove(index));
		}

		// Converter para array e criar a rodada
		Palavra[] palavrasRodada = palavrasEscolhidas.toArray(new Palavra[0]);

		// Verificar se as palavras foram selecionadas corretamente
		for (Palavra p : palavrasRodada) {
			if (p == null) {
				throw new RuntimeException("Erro ao selecionar palavras para a rodada");
			}
		}

		return Rodada.criar(getProximoId(), palavrasRodada, jogador);
	}

	@Override
	protected long getProximoId() {
		return super.getRodadaRepository().getProximoId();
	}

}
