import java.util.Scanner;

import main.java.br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import main.java.br.edu.iff.jogoforca.Aplicacao;
import main.java.br.edu.iff.jogoforca.dominio.jogador.JogadorFactory;
import main.java.br.edu.iff.jogoforca.dominio.jogador.JogadorNaoEncontradoException;
import main.java.br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import main.java.br.edu.iff.jogoforca.dominio.rodada.Rodada;
import main.java.br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import main.java.br.edu.iff.repository.RepositoryException;

public class App {
    public static void main(String[] args) throws RepositoryException, JogadorNaoEncontradoException {
		Aplicacao app = Aplicacao.getSoleInstance();
		app.configurar();
		TemaRepository temas = app.getRepositoryFactory().getTemaRepository();
		JogadorRepository jogadores = app.getRepositoryFactory().getJogadorRepository();
		JogadorFactory jogadorFactory = app.getJogadorFactory();
		TemaFactory temaFactory = app.getTemaFactory();
		definirTemas(temas, temaFactory);
		jogar(jogadores, jogadorFactory);
	}

	public static void definirTemas(TemaRepository temas, TemaFactory temaFactory) {
		String[] listaDeTemas = { "Cores", "Roupas", "Animais", "Comida" };
		for (String tema : listaDeTemas) {
			try {
				temas.inserir(temaFactory.getTema(tema));
			} catch (RepositoryException error) {
				error.printStackTrace();
			}
		}

		String[] palavrasCores = { "azul", "vermelho", "verde", "branco", "roxo", "laranja", "amarelo" };
		for (String palavra : palavrasCores) {
			PalavraAppService.getSoleInstance().novaPalavra(palavra,
					temas.getPorNome(listaDeTemas[0])[0].getId());
		}
		String[] palavrasRoupas = { "camisa", "jaleco", "moletom", "vestido", "sobretudo" };
		for (String palavra : palavrasRoupas) {
			PalavraAppService.getSoleInstance().novaPalavra(palavra,
					temas.getPorNome(listaDeTemas[1])[0].getId());
		}
		String[] palavrasAnimais = { "cachorro", "gato", "peixe", "coelho", "passaro", "cavalo" };
		for (String palavra : palavrasAnimais) {
			PalavraAppService.getSoleInstance().novaPalavra(palavra,
					temas.getPorNome(listaDeTemas[2])[0].getId());
		}
		String[] palavrasComida = { "arroz", "feijao", "macarrao", "batata", "alface" };
		for (String palavra : palavrasComida) {
			PalavraAppService.getSoleInstance().novaPalavra(palavra,
					temas.getPorNome(listaDeTemas[3])[0].getId());
		}
	}

	public static void jogar(JogadorRepository jogadores, JogadorFactory jogadorFactory)
			throws RepositoryException, JogadorNaoEncontradoException {
		
		Rodada partida = RodadaAppService.getSoleInstance().novaRodada(obterJogadorDaRodada(jogadores, jogadorFactory));
		Scanner entrada = new Scanner(System.in);
		Object contexto = null;
		String escolha;
		String[] palavrasArriscadas = new String[partida.getPalavra().length];
		do {
			exibirInformacoes(partida, contexto);
			System.out.print("\nDigite 1 para tentar e 2 para arriscar:");
			escolha = entrada.nextLine();
			switch (escolha) {
				case "1":
					tentar(partida);
					break;
				case "2":
					arriscar(partida, palavrasArriscadas);
					break;
				default:
					System.out.println("<<Escolha inválida>>");
					break;
			}
			System.out.println("===============================");
		} while (!partida.encerrou());
		encerrar(partida, contexto);
		RodadaAppService.getSoleInstance().salvarRodada(partida);
		entrada.close();
	}

	public static String obterJogadorDaRodada(JogadorRepository jogadores, JogadorFactory jogadorFactory)
			throws RepositoryException {
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);

		System.out.println("Informe seu nome: ");
		String nomeJogador = entrada.nextLine();
		System.out.println();

		jogadores.inserir(jogadorFactory.getJogador(nomeJogador));
		return nomeJogador;
	}

	public static void exibirInformacoes(Rodada partida, Object contexto) {
		System.out.println("Tema: " + partida.getTema().getNome());
		System.out.println("Palavras: ");
		partida.exibirItens(contexto);
		System.out.print("Letras erradas: ");
		partida.exibirLetrasErradas(contexto);
		System.out.println("\nBoneco: ");
		partida.exibirBoneco(contexto);
	}

	public static void arriscar(Rodada partida, String[] palavrasArriscadas) {
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		System.out.println("## Arriscar ##");
		System.out.println("Digite as palavras:");
		for (int posicaoAtual = 0; posicaoAtual < palavrasArriscadas.length; posicaoAtual++) {
			System.out.print((posicaoAtual + 1) + "ª palavra: ");
			palavrasArriscadas[posicaoAtual] = entrada.nextLine();
		}
		partida.arriscar(palavrasArriscadas);
	}

	public static void tentar(Rodada partida) {
		@SuppressWarnings("resource")
		Scanner entrada = new Scanner(System.in);
		System.out.println("## Tentar ##");
		System.out.println("Número de tentativas restantes: " + partida.getQtdeTentativasRestantes());
		System.out.print("Digite a letra:");
		char letraTentada = entrada.nextLine().charAt(0);
		if (!(letraTentada >= 'a' && letraTentada <= 'z')) {
			System.out.println("<<Letra inválida>>");
		} else {
			partida.tentar(letraTentada);
		}
	}

	public static void encerrar(Rodada partida, Object contexto) {
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		if (partida.descobriu()) {
			mostrarResultadoVitoria(partida);
		} else {
			mostrarResultadoDerrota(partida, contexto);
		}
		System.out.println();
	}

	public static void mostrarResultadoVitoria(Rodada partida) {
		System.out.println("O Jogador " + partida.getJogador().getNome() + " venceu!");
		System.out.println("Número de tentativas: " + partida.getQtdeTentativas());
		System.out.println("Número de acertos: " + partida.getQtdeAcertos());
		System.out.println("Número de erros: " + partida.getQtdeErros());
		System.out.println("Pontos obtidos: " + partida.calcularPontos());
	}

	public static void mostrarResultadoDerrota(Rodada partida, Object contexto) {
		System.out.println("O Jogador " + partida.getJogador().getNome() + " perdeu!");
		System.out.println("Número de tentativas: " + partida.getQtdeTentativas());
		System.out.println("Número de acertos: " + partida.getQtdeAcertos());
		System.out.println("Palavras:");
		partida.exibirPalavras(contexto);
	}
}
