package main.java.br.edu.iff.bancodepalavras.dominio.letra.texto;

import main.java.br.edu.iff.bancodepalavras.dominio.letra.Letra;

public class LetraTexto extends Letra {

    public LetraTexto(char codigo) {
        super(codigo);
    }

    @Override
    public void exibir(Object contexto) {
        // implementar exibir() com System.out.print
        System.out.print(getCodigo());
    }
}
