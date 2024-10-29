package main.java.br.edu.iff.jogoforca.dominio.rodada;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.java.br.edu.iff.bancodepalavras.dominio.letra.Letra;
import main.java.br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.Tema;
import main.java.br.edu.iff.dominio.ObjetoDominioImpl;
import main.java.br.edu.iff.jogoforca.dominio.boneco.Boneco;
import main.java.br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;
import main.java.br.edu.iff.jogoforca.dominio.jogador.Jogador;

public class Rodada extends ObjetoDominioImpl {

	private static int maxPalavras = 3;
	private static int maxErros = 10;
	private static int pontosQuandoDescobreTodasAsPalavras = 100;
	private static int pontosPorLetraEncoberta = 15;

	private Jogador jogador;
	private static BonecoFactory bonecoFactory;
	private Boneco boneco;
	private Item[] itens;
	private List<Letra> erradas;

	public static int getMaxPalavras() {
		return maxPalavras;
	}

	public static void setMaxPalavras(int max) {
		Rodada.maxPalavras = max;
	}

	public static int getMaxErros() {
		return maxErros;
	}

	public static void setMaxErros(int max) {
		Rodada.maxErros = max;
	}

	public static int getPontosQuandoDescobreTodasAsPalavras() {
		return pontosQuandoDescobreTodasAsPalavras;
	}

	public static void setPontosQuandoDescobreTodasAsPalavras(int pontos) {
		Rodada.pontosQuandoDescobreTodasAsPalavras = pontos;
	}

	public static int getPontosPorLetraEncoberta() {
		return pontosPorLetraEncoberta;
	}

	public static void setPontosPorLetraEncoberta(int pontos) {
		Rodada.pontosPorLetraEncoberta = pontos;
	}

	public static void setBonecoFactory(BonecoFactory bonecoFactory) {
		Rodada.bonecoFactory = bonecoFactory;
	}

	public static BonecoFactory getBonecoFactory() {
		return bonecoFactory;
	}

	public static Rodada criar(long id, Palavra[] palavras, Jogador jogador) {
		if (bonecoFactory == null) {
			throw new RuntimeException("Deve inicializar o bonecoFactory antes");
		}
		return new Rodada(id, palavras, jogador);
	}

	public static Rodada reconstituir(long id, Item[] itens, Letra[] erradas, Jogador jogador) {
		if (bonecoFactory == null) {
			throw new RuntimeException("Deve inicializar o bonecoFactory antes");
		}
		return new Rodada(id, itens, erradas, jogador);
	}

	private Rodada(long id, Palavra[] palavras, Jogador jogador) {
		super(id);

		// Verificar parâmetros
		if (palavras == null || palavras.length == 0) {
			throw new IllegalArgumentException("É necessário fornecer ao menos uma palavra");
		}
		if (jogador == null) {
			throw new IllegalArgumentException("É necessário fornecer um jogador");
		}

		// Criar itens
		this.itens = new Item[palavras.length];
		for (int i = 0; i < palavras.length; i++) {
			if (palavras[i] == null) {
				throw new IllegalArgumentException("Palavra não pode ser null");
			}
			this.itens[i] = Item.criar(i, palavras[i]);
		}

		// Verificar se todas as palavras têm o mesmo tema
		Tema temaTeste = this.itens[0].getPalavra().getTema();
		for (Item item : this.itens) {
			if (!item.getPalavra().getTema().equals(temaTeste)) {
				throw new IllegalArgumentException("Todas as palavras devem ter o mesmo tema");
			}
		}

		this.jogador = jogador;
		this.erradas = new ArrayList<>();
		this.boneco = bonecoFactory.getBoneco();
	}

	// Construtor privado para o reconstruir rodada
	private Rodada(long id, Item[] itens, Letra[] erradas, Jogador jogador) {
		super(id);
		this.itens = itens;
		Tema temaTeste = this.itens[0].getPalavra().getTema();
		for (Item item : this.itens) {
			if (item.getPalavra().getTema() != temaTeste) {
				throw new RuntimeException("Todas as palavras devem ter o mesmo tema");
			}
		}
		this.erradas = Arrays.asList(erradas);
		this.jogador = jogador;
		this.boneco = bonecoFactory.getBoneco();
	}

	public Jogador getJogador() {
		return this.jogador;
	}

	public Tema getTema() {
		if (this.getNumPalavras() == 0) {
			throw new RuntimeException("Deve ter pelo menos um item");
		}
		return this.itens[0].getPalavra().getTema();
	}

	public Palavra[] getPalavra() {
		Palavra[] palavras = new Palavra[this.getNumPalavras()];
		for (int palavraAtual = 0; palavraAtual < this.getNumPalavras(); palavraAtual++) {
			palavras[palavraAtual] = this.itens[palavraAtual].getPalavra();
		}
		return palavras;
	}

	public int getNumPalavras() {
		return this.itens.length;
	}

	public void tentar(char codigo) {
		if (this.encerrou()) {
			throw new RuntimeException("Não pode tentar depois que o jogo encerrou");
		}
		if (this.getNumPalavras() == 0) {
			throw new RuntimeException("Deve ter pelo menos um item");
		}
		boolean encontrou = false;
		for (Item item : this.itens) {
			if (item.tentar(codigo)) {
				encontrou = true;
			}
		}
		if (!encontrou) {
			// Usar o LetraFactory para criar a letra errada
			this.erradas.add(Palavra.getLetraFactory().getLetra(codigo));
		}
		if (this.encerrou()) {
			this.jogador.setPontuacao(this.jogador.getPontuacao() + this.calcularPontos());
		}
	}

	public void arriscar(String[] palavras) {
		if (this.encerrou()) {
			throw new RuntimeException("Não pode tentar depois que o jogo encerrou");
		}
		for (int palavraAtual = 0; palavraAtual < this.getNumPalavras(); palavraAtual++) {
			this.itens[palavraAtual].arriscar(palavras[palavraAtual]);
		}
		if (this.encerrou()) {
			this.jogador.setPontuacao(this.jogador.getPontuacao() + this.calcularPontos());
		}
	}

	public void exibirItens(Object contexto) {
		for (Item item : this.itens) {
			item.exibir(contexto);
			System.out.println();
		}
	}

	public void exibirBoneco(Object contexto) {
		this.boneco.exibir(contexto, this.erradas.size());
	}

	public void exibirPalavras(Object contexto) {
		for (Item item : this.itens) {
			item.getPalavra().exibir(contexto);
			System.out.println();
		}
	}

	public void exibirLetrasErradas(Object contexto) {
		for (Letra letra : this.erradas) {
			letra.exibir(contexto);
			System.out.print(" ");
		}
	}

	public Letra[] getTentativas() {
		Letra[] tentativas = new Letra[this.getCertas().length + this.getErradas().length];
		int letraAtual = 0;
		for (; letraAtual < this.getCertas().length; letraAtual++) {
			tentativas[letraAtual] = this.getCertas()[letraAtual];
		}
		for (; letraAtual < tentativas.length; letraAtual++) {
			tentativas[letraAtual] = this.getErradas()[letraAtual - this.getCertas().length];
		}
		return tentativas;
	}

	public Letra[] getCertas() {
		ArrayList<Letra> acertos = new ArrayList<Letra>();
		for (Item item : this.itens) {
			for (Letra letra : item.getLetrasDescobertas()) {
				if (!acertos.contains(letra)) {
					acertos.add(letra);
				}
			}
		}
		return acertos.toArray(new Letra[acertos.size()]);
	}

	public Letra[] getErradas() {
		return this.erradas.toArray(new Letra[this.erradas.size()]);
	}

	public int calcularPontos() {
		if (this.descobriu()) {
			int pontosTotaisPorLetrasEncobertas = 0;
			for (Item item : this.itens) {
				pontosTotaisPorLetrasEncobertas += item.calcularPontosLetrasEncobertas(pontosPorLetraEncoberta);
			}
			return pontosQuandoDescobreTodasAsPalavras + pontosTotaisPorLetrasEncobertas;
		} else {
			return 0;
		}
	}

	public boolean encerrou() {
		return this.arriscou() || this.descobriu() || (this.getQtdeTentativasRestantes() == 0);
	}

	public boolean descobriu() {
		for (Item item : this.itens) {
			if (!item.descobriu()) {
				return false;
			}
		}
		return true;
	}

	public boolean arriscou() {
		for (Item item : this.itens) {
			if (!item.arriscou()) {
				return false;
			}
		}
		return true;
	}

	public int getQtdeTentativasRestantes() {
		return maxErros - this.getQtdeErros();
	}

	public int getQtdeErros() {
		return this.getErradas().length;
	}

	public int getQtdeAcertos() {
		return this.getCertas().length;
	}

	public int getQtdeTentativas() {
		return this.getQtdeAcertos() + this.getQtdeErros();
	}
}
