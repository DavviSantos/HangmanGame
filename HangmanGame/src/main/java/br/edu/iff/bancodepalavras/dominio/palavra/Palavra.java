package main.java.br.edu.iff.bancodepalavras.dominio.palavra;

import main.java.br.edu.iff.bancodepalavras.dominio.letra.Letra;
import main.java.br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import main.java.br.edu.iff.dominio.ObjetoDominioImpl;

public class Palavra extends ObjetoDominioImpl {
    
    public void setLetraFactory(LetraFactory factory ){

    }

    public LetraFactory getLetraFactory(){
        return factory;
    }
    
    public Palavra criar(long id, String palavra, Tema tema){

    }
    
    public Palavra reconstruir(long id, String palavra, Tema tema){

    }

    public Palavra (long id, String palavra, Tema tema){

    }

    public Letra[] getLetras(){

    }

    public Letra getLetra(int posicao){

    }

    public void exibir(Object contexto){

    }

    public void exibir(Object contexto, boolean[] posicoes){

    }

    public int[] tentar(char codigo){

    }

    public Tema getTema(){

    }

    public boolean comparar(String palavra){

    }

    public int getTamanho(){

    }

    @Override
    public String toString() {
        return "Palavra []";
    }
}
