package main.java.br.edu.iff.jogoforca.dominio.boneco.imagem;

import main.java.br.edu.iff.jogoforca.dominio.boneco.Boneco;

public class BonecoImagem implements Boneco {

    private static BonecoImagem soleInstance;

    private BonecoImagem() {
    
    }

    public static BonecoImagem getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BonecoImagem();
        }
        return soleInstance;
    }

    @Override
    public void exibir(Object contexto, int partes) {
        // Implementar lógica de exibição de imagem, se necessário
        // Por enquanto, o método pode ficar vazio
    }
}
