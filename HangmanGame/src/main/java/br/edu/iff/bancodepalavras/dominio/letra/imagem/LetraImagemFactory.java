package main.java.br.edu.iff.bancodepalavras.dominio.letra.imagem;

import main.java.br.edu.iff.bancodepalavras.dominio.letra.Letra;
import main.java.br.edu.iff.bancodepalavras.dominio.letra.LetraFactoryImpl;

public class LetraImagemFactory extends LetraFactoryImpl {
    
    private static LetraImagemFactory soleInstance;

    private LetraImagemFactory() {
        super();
    }

    public static LetraImagemFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new LetraImagemFactory();
        }
        return soleInstance;
    }

    // Implementação do método criarLetra para criar instâncias de LetraImagem
    @Override
    protected Letra criarLetra(char codigo) {
        return new LetraImagem(codigo);
    }
}
