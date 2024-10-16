package main.java.br.edu.iff.jogoforca.dominio.boneco.texto;

import java.util.HashMap;
import java.util.Map;

import main.java.br.edu.iff.jogoforca.dominio.boneco.Boneco;

public class BonecoTexto implements Boneco {

    private static BonecoTexto soleInstance;

    private Map<Integer, String> partesBoneco;

    public static BonecoTexto getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BonecoTexto();
        }
        return soleInstance;
    }

    private BonecoTexto() {
        this.partesBoneco = new HashMap<>();
        partesBoneco.put(1, "cabeça");
        partesBoneco.put(2, "cabeça, olho esquerdo");
        partesBoneco.put(3, "cabeça, olho esquerdo, olho direito");
        partesBoneco.put(4, "cabeça, olho esquerdo, olho direito, nariz");
        partesBoneco.put(5, "cabeça, olho esquerdo, olho direito, nariz, boca");
        partesBoneco.put(6, "cabeça, olho esquerdo, olho direito, nariz, boca, tronco");
        partesBoneco.put(7, "cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo");
        partesBoneco.put(8, "cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito");
        partesBoneco.put(9, "cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito, perna esquerda");
        partesBoneco.put(10, "cabeça, olho esquerdo, olho direito, nariz, boca, tronco, braço esquerdo, braço direito, perna esquerda, perna direita");
    }

    @Override
    public void exibir(Object contexto, int partes) {
        if(!partesBoneco.containsKey(partes)){
            throw new RuntimeException("Index inválido");
        }
        System.err.println(partesBoneco.get(partes));
        
    }

}
