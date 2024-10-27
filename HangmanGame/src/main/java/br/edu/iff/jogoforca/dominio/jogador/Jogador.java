package main.java.br.edu.iff.jogoforca.dominio.jogador;

import main.java.br.edu.iff.dominio.ObjetoDominioImpl;

public class Jogador extends ObjetoDominioImpl {
    private String nome;
    private int pontuacao;

    private Jogador (long id, String nome) {
        super(id);
        this.nome = nome;
        this.pontuacao = 0;
    }

    private Jogador (long id, String nome, int pontuacao) {
        super(id);
        this.nome = nome;
        this.pontuacao = pontuacao;
    }

    public static Jogador criar(long id, String nome) {
        return new Jogador(id, nome);
    }

    public static Jogador reconstruir(long id, String nome, int pontuacao) {
        return new Jogador(id, nome, pontuacao);
    }

    public int getPontuacao() {
        return pontuacao;
    }

    //pontuação = soma dos pontos de todas asrodadas deste jogador.
    public void setPontuacao(int pontuacao) {
        this.pontuacao += pontuacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}
