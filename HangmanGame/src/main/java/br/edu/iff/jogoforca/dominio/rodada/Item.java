package main.java.br.edu.iff.jogoforca.dominio.rodada;

import java.util.ArrayList;

import main.java.br.edu.iff.bancodepalavras.dominio.letra.Letra;
import main.java.br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import main.java.br.edu.iff.dominio.ObjetoDominioImpl;

public class Item extends ObjetoDominioImpl {
    private Palavra palavra;
    private boolean[] posicoesDescobertas;
    private String palavraArriscada = null;

    static Item criar(long id, Palavra palavra) {
        return new Item(id, palavra);
    }

    private Item(long id, Palavra palavra) {
        super(id);
        this.palavra = palavra; // Corrigido: inicializando a palavra
        this.posicoesDescobertas = new boolean[palavra.getTamanho()]; // Inicializando o array de posições
    }

    private Item(long id, Palavra palavra, int[] posicoesDescobertas, String palavraArriscada) {
        super(id);
        this.palavra = palavra;
        this.posicoesDescobertas = new boolean[palavra.getTamanho()];
        for (Integer i : posicoesDescobertas) {
            this.posicoesDescobertas[i] = true;
        }
        this.palavraArriscada = palavraArriscada;
    }

    public Palavra getPalavra() {
        return this.palavra;
    }

    // Melhor fazer com arraylist, já que o tamanho irá variar com as letras
    public Letra[] getLetrasDescobertas() {
        ArrayList<Letra> letras = new ArrayList<Letra>();
        for (int posicaoAtual = 0; posicaoAtual < this.palavra.getTamanho(); posicaoAtual++) {
            // onde 1 é verdadeiro e 0 falso
            if (this.posicoesDescobertas[posicaoAtual]) {
                letras.add((this.palavra.getLetra(posicaoAtual)));
            }
        }

        return letras.toArray(new Letra[letras.size()]);
    }

    public Letra[] getLetrasEncobertas() {
        ArrayList<Letra> letras = new ArrayList<Letra>();
        for (int posicaoAtual = 0; posicaoAtual < this.palavra.getTamanho(); posicaoAtual++) {
            // onde 1 é verdadeiro e 0 falso
            if (!this.posicoesDescobertas[posicaoAtual]) {
                letras.add((this.palavra.getLetra(posicaoAtual)));
            }
        }

        return letras.toArray(new Letra[letras.size()]);
    }

    public int qtdeLetrasEncobertas() {
        return getLetrasEncobertas().length;
    }

    public int calcularPontosLetrasEncobertas(int valorPorLetraEncoberta) {
        return this.qtdeLetrasEncobertas() * valorPorLetraEncoberta;
    }

    public boolean descobriu() {
        if ((acertou()) || this.qtdeLetrasEncobertas() == 0) {
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
