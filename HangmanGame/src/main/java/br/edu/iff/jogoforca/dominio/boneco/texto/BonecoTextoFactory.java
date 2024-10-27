package main.java.br.edu.iff.jogoforca.dominio.boneco.texto;

import main.java.br.edu.iff.jogoforca.dominio.boneco.Boneco;
import main.java.br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;

public class BonecoTextoFactory implements BonecoFactory {

    private static BonecoTextoFactory soleInstance;

    private BonecoTextoFactory() {
        // Construtor privado para evitar instanciamento externo
    }

    public static BonecoTextoFactory getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BonecoTextoFactory();
        }
        return soleInstance;
    }

    @Override
    public Boneco getBoneco() {
        return BonecoTexto.getSoleInstance();
    }
}
