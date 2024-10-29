package main.java.br.edu.iff.jogoforca.imagem;

import main.java.br.edu.iff.bancodepalavras.dominio.letra.Letra;
import main.java.br.edu.iff.jogoforca.ElementoGraficoFactory;
import main.java.br.edu.iff.jogoforca.dominio.boneco.Boneco;
import main.java.br.edu.iff.jogoforca.dominio.boneco.imagem.BonecoImagemFactory;
import main.java.br.edu.iff.bancodepalavras.dominio.letra.imagem.LetraImagemFactory;

public class ElementoGraficoImagemFactory implements ElementoGraficoFactory {

    private final LetraImagemFactory letraImagemFactory;
    private final BonecoImagemFactory bonecoImagemFactory;

    private static ElementoGraficoImagemFactory soleInstance;
	
	public static ElementoGraficoImagemFactory getSoleInstance() {
		if(soleInstance==null) {
			soleInstance = new ElementoGraficoImagemFactory();
		}
		return soleInstance;
	}    

    // Construtor
    public ElementoGraficoImagemFactory() {
        this.letraImagemFactory = LetraImagemFactory.getSoleInstance(); // Acessa o singleton de LetraImagemFactory
        this.bonecoImagemFactory = BonecoImagemFactory.getSoleInstance(); // Acessa o singleton de BonecoImagemFactory
    }

    @Override
    public Boneco getBoneco() {
        return bonecoImagemFactory.getBoneco(); // Retorna um boneco da fábrica
    }

    @Override
    public Letra getLetra(char codigo) {
        return letraImagemFactory.getLetra(codigo); // Retorna uma letra da fábrica
    }

    @Override
    public Letra getLetraEncoberta() {
        return letraImagemFactory.getLetraEncoberta(); // Retorna a letra encoberta da fábrica
    }
}
