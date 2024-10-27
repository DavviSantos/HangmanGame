package main.java.br.edu.iff.jogoforca.dominio.rodada;

import java.util.ArrayList;

import main.java.br.edu.iff.bancodepalavras.dominio.letra.Letra;
import main.java.br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import main.java.br.edu.iff.dominio.ObjetoDominioImpl;

public class Item extends ObjetoDominioImpl {

    //pela necessidade, talvez tenha que ter um Palavra aqui
    private Palavra palavra;
    //Optei pelo boolean, pois na classe Palavra já esta setado assim e agora já foi
    private boolean[] posicoesDescobertas;
	private String palavraArriscada = null;

    static Item criar(long id, Palavra palavra){
        return new Item(id, palavra);
    }

    //Modifiquei por long, pois não faria sentido com o construtor Pai
    private Item (long id, Palavra palavra){
        super(id);
    }

    //Caso o item já venha com a palavra arriscada?
    //posicoes descobertas é um vetor de int ou boolean?
    private Item(long id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada) {
		super(id);
		this.posicoesDescobertas = new boolean[this.palavra.getTamanho()];
		for(Integer i : posicoesDescobertas) {
			this.posicoesDescobertas[i] = true;

            
		}
		this.palavraArriscada = palavraArriscada;
	}
    
    public Palavra getPalavra(){
        return this.palavra;
    }

    //Melhor fazer com arraylist, já que o tamanho irá variar com as letras
    public Letra[] getLetrasDescobertas(){
        ArrayList<Letra> letras = new ArrayList<Letra>();
        for(int posicaoAtual = 0; posicaoAtual < this.palavra.getTamanho(); posicaoAtual++) {
            // onde 1 é verdadeiro e 0 falso
			if(this.posicoesDescobertas[posicaoAtual]) {
				letras.add((this.palavra.getLetra(posicaoAtual)));
			}
		}

        return letras.toArray(new Letra[letras.size()]);
    }

    public Letra[] getLetrasEncobertas(){
        ArrayList<Letra> letras = new ArrayList<Letra>();
        for(int posicaoAtual = 0; posicaoAtual < this.palavra.getTamanho(); posicaoAtual++) {
            // onde 1 é verdadeiro e 0 falso
			if(!this.posicoesDescobertas[posicaoAtual]) {
				letras.add((this.palavra.getLetra(posicaoAtual)));
			}
		}

        return letras.toArray(new Letra[letras.size()]);
    }

    public int qtdeLetrasEncobertas(){
        return getLetrasEncobertas().length;
    }

    public int calcularPontosLetrasEncobertas(int valorPorLetraEncoberta) {
		return this.qtdeLetrasEncobertas()*valorPorLetraEncoberta;
	}

    public boolean descobriu() {
        if ((acertou()) || this.qtdeLetrasEncobertas() == 0){
            return true;
        }
		return false;
	}

    public void exibir(Object contexto) {
		this.palavra.exibir(contexto, this.posicoesDescobertas);
	}

    boolean tentar(char codigo) {
		int[] posicoes = palavra.tentar(codigo);
        for (int posicao : posicoes) {
            posicoesDescobertas[posicao] = true;
        }
        
        return posicoes.length > 0 ? true : false;
	}

    void arriscar(String palavra) {
		this.palavraArriscada = palavra;
	}

    public String getPalavraArriscada() {
		return this.palavraArriscada;
	}

	public boolean arriscou() {
		return this.palavraArriscada != null;
	}

    public boolean acertou() {
		return this.palavra.comparar(this.palavraArriscada);
	}


}
