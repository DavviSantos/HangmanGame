package main.java.br.edu.iff.bancodepalavras.dominio.palavra;

import java.util.ArrayList;

import main.java.br.edu.iff.bancodepalavras.dominio.letra.Letra;
import main.java.br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.Tema;
import main.java.br.edu.iff.dominio.ObjetoDominioImpl;

//Classe do tipo Entity
public class Palavra extends ObjetoDominioImpl {

    private LetraFactory letraFactory;
    private Letra encoberta;
    private Letra[] palavra;
    private int tamanhoDaPalavra;
    private Tema tema;
    
    
    public void setLetraFactory(LetraFactory factory ){
        letraFactory = factory;
    }

    public LetraFactory getLetraFactory(){
        return letraFactory;
    }
    
    public Palavra criar(long id, String palavra, Tema tema){
        if(letraFactory==null) {
			throw new RuntimeException("Deve inicializar o letraFactory primeiro");
		}
		return new Palavra(id, palavra, tema);
    }
    
    public Palavra reconstruir(long id, String palavra, Tema tema){
        if(letraFactory==null) {
			throw new RuntimeException("Deve inicializar o letraFactory primeiro");
		}
		return new Palavra(id, palavra, tema);
    }

    private Palavra (long id, String palavra, Tema tema){
        super(id);
		this.tema = tema;
		this.tamanhoDaPalavra = palavra.length();
		this.palavra = new Letra[tamanhoDaPalavra];
		for(int letraAtual = 0; letraAtual < tamanhoDaPalavra; letraAtual++) {
			this.palavra[letraAtual] = letraFactory.getLetra(palavra.charAt(letraAtual));
		}
    }

    public Letra[] getLetras(){
        if(this.palavra==null) {
			throw new RuntimeException("Deve inicializar a palavra primeiro");
		}
		return this.palavra.clone();
	}

    public Letra getLetra(int posicao){
        if(this.palavra==null) {
			throw new RuntimeException("Deve inicializar a palavra primeiro");
		}
		if(posicao<0||posicao>getTamanho()) {
			throw new RuntimeException("Posição inválida");
		}
		return this.palavra[posicao];
    }

    public void exibir(Object contexto){
        if(this.palavra==null) {
			throw new RuntimeException("Deve inicializar a palavra primeiro");
		}
		for(int posicaoAtual = 0; posicaoAtual < this.getTamanho(); posicaoAtual++) {
			this.palavra[posicaoAtual].exibir(contexto);
		}
    }

    public void exibir(Object contexto, boolean[] posicoes){
        if(this.palavra==null) {
			throw new RuntimeException("Deve inicializar a palavra primeiro");
		}
		for(int posicaoAtual = 0; posicaoAtual < this.getTamanho(); posicaoAtual++) {
			if(posicoes[posicaoAtual]) {				
				this.palavra[posicaoAtual].exibir(contexto);
			}else {
				this.encoberta.exibir(contexto);
			}
		}
    }

    public int[] tentar(char codigo){
        if(this.palavra==null) {
			throw new RuntimeException("Deve inicializar a palavra primeiro");
		}
		ArrayList<Integer> posicoesEncontradasLista = new ArrayList<Integer>();
		for(int posicaoAtual = 0; posicaoAtual<this.getTamanho(); posicaoAtual++) {
			if(this.palavra[posicaoAtual].getCodigo()==codigo) {
				posicoesEncontradasLista.add(posicaoAtual);
			}
		}
        return posicoesEncontradasLista.stream().mapToInt(Integer::intValue).toArray();
    }

    public Tema getTema(){
        return tema;
    }

    public boolean comparar(String palavra){
        if(this.palavra==null) {
			throw new RuntimeException("Deve inicializar a palavra primeiro");
		}
		if(palavra==null) {
			return false;
		}
		if(this.getTamanho()!=tamanhoDaPalavra) {
			return false;
		}
		for(int posicaoAtual = 0; posicaoAtual<this.getTamanho(); posicaoAtual++) {
			if(this.palavra[posicaoAtual].getCodigo()!=palavra.charAt(posicaoAtual)) {
				return false;
			}
		}
		return true;
    }

    public int getTamanho(){
        if(this.palavra==null) {
			throw new RuntimeException("Deve inicializar a palavra primeiro");
		}
		return this.palavra.length;
    }

    @Override
    public String toString() {
        if(this.palavra==null) {
			throw new RuntimeException("Deve inicializar a palavra primeiro");
		}
		String palavra = "";
		for(int posicaoAtual = 0; posicaoAtual<this.getTamanho(); posicaoAtual++) {
			palavra+=this.palavra[posicaoAtual].getCodigo();
		}
		return palavra;
    }
}
