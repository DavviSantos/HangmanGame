package main.java.br.edu.iff.jogoforca.texto;

import main.java.br.edu.iff.bancodepalavras.dominio.letra.Letra;
import main.java.br.edu.iff.bancodepalavras.dominio.letra.texto.LetraTextoFactory;
import main.java.br.edu.iff.jogoforca.ElementoGraficoFactory;
import main.java.br.edu.iff.jogoforca.dominio.boneco.Boneco;
import main.java.br.edu.iff.jogoforca.dominio.boneco.texto.BonecoTextoFactory;

public class ElementoGraficoTextoFactory implements ElementoGraficoFactory {
    private final LetraTextoFactory letraTextoFactory;
    private final BonecoTextoFactory bonecoTextoFactory;

    private static ElementoGraficoTextoFactory soleInstance;
	
	public static ElementoGraficoTextoFactory getSoleInstance() {
		if(soleInstance==null) {
			soleInstance = new ElementoGraficoTextoFactory();
		}
		return soleInstance;
	}
    
    // Construtor
    public ElementoGraficoTextoFactory() {
        this.letraTextoFactory = LetraTextoFactory.getSoleInstance(); // Obter a instância única da fábrica de letras
        this.bonecoTextoFactory = BonecoTextoFactory.getSoleInstance(); // Obter a instância única da fábrica de bonecos
    }

    @Override
    public Boneco getBoneco() {
        return bonecoTextoFactory.getBoneco(); // Retorna um boneco da fábrica de bonecos
    }

    @Override
    public Letra getLetra(char codigo) {
        return letraTextoFactory.getLetra(codigo); // Retorna uma letra da fábrica de letras
    }

    @Override
    public Letra getLetraEncoberta() {
        return letraTextoFactory.getLetraEncoberta(); // Retorna a letra encoberta da fábrica de letras
    }
}
