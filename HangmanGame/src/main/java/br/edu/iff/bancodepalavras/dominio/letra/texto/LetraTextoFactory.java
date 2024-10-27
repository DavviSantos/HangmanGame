package main.java.br.edu.iff.bancodepalavras.dominio.letra.texto;

import main.java.br.edu.iff.bancodepalavras.dominio.letra.Letra;
import main.java.br.edu.iff.bancodepalavras.dominio.letra.LetraFactoryImpl;

public class LetraTextoFactory extends LetraFactoryImpl {

    private static LetraTextoFactory soleInstance;

    private LetraTextoFactory() {
        super();
    }

    public static LetraTextoFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new LetraTextoFactory();
        }
        return soleInstance;
    }

    // Implementação do método criarLetra para criar instâncias de LetraTexto
    @Override
    protected Letra criarLetra(char codigo) {
        return new LetraTexto(codigo);
    }
}
