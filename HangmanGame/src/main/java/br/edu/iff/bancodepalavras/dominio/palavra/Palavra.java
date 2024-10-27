package main.java.br.edu.iff.bancodepalavras.dominio.palavra;

import main.java.br.edu.iff.bancodepalavras.dominio.tema.Tema;
import main.java.br.edu.iff.bancodepalavras.dominio.letra.Letra;
import main.java.br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import main.java.br.edu.iff.dominio.ObjetoDominioImpl;

public class Palavra extends ObjetoDominioImpl {

    private Tema tema;
    private static LetraFactory letraFactory;
    private Letra encoberta;
    private Letra[] letras;

    // Construtor privado
    private Palavra(long id, String palavra, Tema tema) {
        super(id); // Chamada ao construtor da superclasse
        this.tema = tema;
        this.letras = new Letra[palavra.length()];
        
        // Criação das letras usando a letraFactory
        for (int i = 0; i < palavra.length(); i++) {
            this.letras[i] = letraFactory.getLetra(palavra.charAt(i));
        }
        
        this.encoberta = letraFactory.getLetraEncoberta();
    }

    public static void setLetraFactory(LetraFactory factory) {
        letraFactory = factory;
    }

    public static LetraFactory getLetraFactory() {
        return letraFactory;
    }

    public static Palavra criar(long id, String palavra, Tema tema) {
        if (letraFactory == null) {
            throw new RuntimeException("letraFactory não foi inicializado, inicialize antes");
        }
        return new Palavra(id, palavra, tema);
    }

    public static Palavra reconstituir(long id, String palavra, Tema tema) {
        if (letraFactory == null) {
            throw new RuntimeException("letraFactory não foi inicializado, inicialize antes");
        }
        return new Palavra(id, palavra, tema);
    }

    public int getTamanho() {
        return letras.length;
    }

    public boolean comparar(String palavra) {
        if (palavra == null || palavra.length() != getTamanho()) {
            return false;
        }

        for (int i = 0; i < getTamanho(); i++) {
            if (letras[i].getCodigo() != palavra.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public Tema getTema() {
        return tema;
    }

    public int[] tentar(char codigo) {
        int[] posicoes = new int[getTamanho()];
        int count = 0;

        for (int i = 0; i < getTamanho(); i++) {
            if (letras[i].getCodigo() == codigo) {
                posicoes[count++] = i;
            }
        }

        // Retorna um vetor com o tamanho correto
        int[] resultado = new int[count];
        System.arraycopy(posicoes, 0, resultado, 0, count);
        return resultado;
    }

    public void exibir(Object contexto) {
        for (Letra letra : letras) {
            letra.exibir(contexto);
        }
    }

    public void exibir(Object contexto, boolean[] posicoes) {
        for (int i = 0; i < getTamanho(); i++) {
            if (posicoes[i]) {
                letras[i].exibir(contexto);
            } else {
                encoberta.exibir(contexto);
            }
        }
    }

    public Letra getLetra(int posicao) {
        if (posicao < 0 || posicao >= getTamanho()) {
            throw new RuntimeException("Essa posição é inválida!");
        }
        return letras[posicao];
    }

    public Letra[] getLetras() {
        return letras.clone();
    }

    @Override
    public String toString() {
        StringBuilder palavraStr = new StringBuilder();
        for (Letra letra : letras) {
            palavraStr.append(letra.getCodigo());
        }
        return palavraStr.toString();
    }
}
