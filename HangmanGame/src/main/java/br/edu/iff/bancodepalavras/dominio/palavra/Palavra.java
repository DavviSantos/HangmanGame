package main.java.br.edu.iff.bancodepalavras.dominio.palavra;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import main.java.br.edu.iff.bancodepalavras.dominio.tema.Tema;
import main.java.br.edu.iff.bancodepalavras.dominio.letra.Letra;
import main.java.br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import main.java.br.edu.iff.dominio.ObjetoDominioImpl;

public class Palavra extends ObjetoDominioImpl{

    private Tema tema;
    private static LetraFactory letraFactory;
    private Letra encoberta;
    private int tamanho;
    private Letra[] palavra;

    
    public static void setLetraFactory(LetraFactory factory){
        letraFactory = factory;
    }

    public LetraFactory getLetraFactory(){
        return letraFactory;
    }

    public static Palavra criar (long id, String palavra, Tema tema){
        if(letraFactory==null){
            throw new RuntimeException("letraFactory ainda não foi inicializado, inicialize antes");
        }
        return new Palavra(id, palavra, tema);
    }

    public static Palavra reconstruir(long id, String palavra,Tema tema){
        if(letraFactory==null){
            throw new RuntimeException("letraFactory ainda não foi inicializado, inicialize antes");
        }
        return new Palavra(id, palavra, tema);
    }

    private Palavra(long id, String palavra, Tema tema){
        super(id);
        this.palavra = new Letra[palavra.length()];

        for(int letraAtual = 0; letraAtual < palavra.length(); letraAtual++){
            this.palavra[letraAtual] = letraFactory.getLetra(palavra.charAt(letraAtual));
        }
        this.tema = tema;
        this.encoberta = letraFactory.getLetraEncoberta();
        this.tamanho = this.palavra.length;
    }

    public Letra[] getLetras(){
        if(this.palavra==null){
            throw new RuntimeException("palavra ainda não foi inicializada, inicialize antes.");
        }
        return this.palavra.clone();
    }

    public Letra getLetra(int posicao){
        if(this.palavra==null){
            throw new RuntimeException("palavra ainda não foi inicializada, inicialize antes.");
        }
        if(posicao<0 || posicao>getTamanho()){
            throw new RuntimeException("Essa posição é inválida!");
        }
        return this.palavra[posicao];
    }

    public void exibir(Object contexto){
        if(this.palavra==null){
            throw new RuntimeException("palavra ainda não foi inicializada, inicialize antes."); 
        }

        for(int posicaoAtual = 0; posicaoAtual<getTamanho(); posicaoAtual++){
            this.palavra[posicaoAtual].exibir(contexto);
        }
    }

    public void exibir(Object contexto, boolean[] posicoes){
        if(this.palavra==null){
            throw new RuntimeException("palavra ainda não foi inicializada, inicialize antes.");
        }

        for(int posicaoAtual = 0; posicaoAtual < this.getTamanho(); posicaoAtual++){
            if(posicoes[posicaoAtual]){
                this.palavra[posicaoAtual].exibir(contexto);
            }
            else{
                this.encoberta.exibir(contexto);
            }
        }
    }

    public int[] tentar(char codigo){
        int[] posicoesEncontradasNaLista = new int[this.getTamanho()];
        if(this.palavra==null){
            return posicoesEncontradasNaLista;
        }
        
        for(int posicaoAtual = 0; posicaoAtual<this.getTamanho(); posicaoAtual++){
            if(this.palavra[posicaoAtual].getCodigo()==codigo){
                posicoesEncontradasNaLista[posicaoAtual] = 1;
            }else{
                posicoesEncontradasNaLista[posicaoAtual] = 0;
            }
        }
        return posicoesEncontradasNaLista;
    }
   

    public Tema getTema(){
        return tema;
    }

    public boolean comparar(String palavra){
        if(this.palavra==null){
            throw new RuntimeException("palavra ainda não foi inicializada, inicialize antes."); 
        }
        if((palavra==null) || (this.getTamanho()!=palavra.length())){
            return false;
        }
        
        for(int posicaoAtual = 0; posicaoAtual<this.getTamanho(); posicaoAtual++){
            if(this.palavra[posicaoAtual].getCodigo()!=palavra.charAt(posicaoAtual)){
                return false;
            }
        }
        return true;
    }
    public int getTamanho(){
        if(this.palavra==null){
            throw new RuntimeException("palavra ainda não foi inicializada, inicialize antes.");
        }
        return this.tamanho;
        
    }

}