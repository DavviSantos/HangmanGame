package main.java.br.edu.iff.jogoforca.dominio.boneco.imagem;

import main.java.br.edu.iff.jogoforca.dominio.boneco.Boneco;
import main.java.br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;

public class BonecoImagemFactory implements BonecoFactory {

    private static BonecoImagemFactory soleInstance;

    private BonecoImagemFactory() {
    }

    public static BonecoImagemFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BonecoImagemFactory();
        }
        return soleInstance;
    }

    // Implementação do método da interface BonecoFactory
    @Override
    public Boneco getBoneco() {
        return BonecoImagem.getSoleInstance();
    }
}
