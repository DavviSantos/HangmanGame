package main.java.br.edu.iff.bancodepalavras.dominio.palavra;

import main.java.br.edu.iff.bancodepalavras.dominio.tema.Tema;

public interface PalavraFactory {

    public Palavra getPalavra(String palavra, Tema tema);
    
}
