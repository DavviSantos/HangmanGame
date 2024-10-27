package main.java.br.edu.iff.bancodepalavras.dominio.tema;

import main.java.br.edu.iff.dominio.ObjetoDominioImpl;

//Classe do tipo Entity
public class Tema extends ObjetoDominioImpl {
    //Define o atributo da classe Tema
    private String nome;

    // Define um construtor privado que recebe um id e um nome
    private Tema(long id, String nome) {
        super(id);
        this.nome = nome;
    }

    // Define um método estático para reconstituir um objeto Tema a partir de um id e um nome
    public static Tema reconstituir(long id, String nome) {
        Tema tema = new Tema(id, nome);
        return tema;
    }

    // Define um método estático para criar um objeto Tema a partir de um id e um nome
    public static Tema criar(long id, String nome) {
        Tema tema = new Tema(id, nome);
        return tema;
    }

    // Define um método para definir o nome do tema
    public void setNome(String nome) {
        this.nome = nome;
    }

    // Define um método para obter o nome do tema
    public String getNome() {
        return this.nome;
    }
}
