package main.java.br.edu.iff.jogoforca;

import main.java.br.edu.iff.bancodepalavras.dominio.letra.LetraFactory;
import main.java.br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import main.java.br.edu.iff.bancodepalavras.dominio.palavra.PalavraAppService;
import main.java.br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactory;
import main.java.br.edu.iff.bancodepalavras.dominio.palavra.PalavraFactoryImpl;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaFactory;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaFactoryImpl;
import main.java.br.edu.iff.jogoforca.dominio.boneco.BonecoFactory;
import main.java.br.edu.iff.jogoforca.dominio.jogador.JogadorFactory;
import main.java.br.edu.iff.jogoforca.dominio.jogador.JogadorFactoryImpl;
import main.java.br.edu.iff.jogoforca.dominio.rodada.Rodada;
import main.java.br.edu.iff.jogoforca.dominio.rodada.RodadaFactory;
import main.java.br.edu.iff.jogoforca.dominio.rodada.RodadaAppService;
import main.java.br.edu.iff.jogoforca.dominio.rodada.sorteio.RodadaSorteioFactory;
import main.java.br.edu.iff.jogoforca.embdr.BDRRepositoryFactory;
import main.java.br.edu.iff.jogoforca.emmemoria.MemoriaRepositoryFactory;
import main.java.br.edu.iff.jogoforca.imagem.ElementoGraficoImagemFactory;
import main.java.br.edu.iff.jogoforca.texto.ElementoGraficoTextoFactory;

public class Aplicacao {
    // Definição dos tipos disponíveis para as fábricas de rodada, elemento gráfico e repositório
    private static final String[] TIPOS_RODADA_FACTORY = {"sorteio"};
    private static final String[] TIPOS_ELEMENTO_GRAFICO_FACTORY = {"texto", "imagem"};
    private static final String[] TIPOS_REPOSITORY_FACTORY = {"memoria", "relacional"};

    private static Aplicacao soleInstance;

    // Atributos para armazenar os tipos selecionados atualmente
    private String tipoRodadaFactory = TIPOS_RODADA_FACTORY[0];
    private String tipoElementoGraficoFactory = TIPOS_ELEMENTO_GRAFICO_FACTORY[0];
    private String tipoRepositoryFactory = TIPOS_REPOSITORY_FACTORY[0];

    // Método para retornar a instância Singleton de Aplicacao
    public static Aplicacao getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new Aplicacao();
        }
        return soleInstance;
    }

    private Aplicacao() {
        configurar();
    }

	public void configurar() {
		RodadaSorteioFactory.createSoleInstance(this.getRepositoryFactory().getRodadaRepository(), this.getRepositoryFactory().getTemaRepository(), this.getRepositoryFactory().getPalavraRepository());
		TemaFactoryImpl.createSoleInstance(this.getRepositoryFactory().getTemaRepository());
		PalavraFactoryImpl.createSoleInstance(this.getRepositoryFactory().getPalavraRepository());
		JogadorFactoryImpl.createSoleInstance(this.getRepositoryFactory().getJogadorRepository());
		
		Palavra.setLetraFactory(this.getLetraFactory());
		Rodada.setBonecoFactory(this.getBonecoFactory());
		
		PalavraAppService.createSoleInstance(this.getRepositoryFactory().getTemaRepository(), this.getRepositoryFactory().getPalavraRepository(), this.getPalavraFactory());
		
		RodadaAppService.createSoleInstance(this.getRepositoryFactory().getRodadaRepository(), this.getRepositoryFactory().getJogadorRepository(), this.getRodadaFactory());
		
	}

    public String[] getTiposRepositoryFactory() {
        return TIPOS_REPOSITORY_FACTORY;
    }

    public void setTipoRepositoryFactory(String tipo) {
        tipoRepositoryFactory = tipo;
        configurar();
    }

    public RepositoryFactory getRepositoryFactory() {
        if(this.tipoRepositoryFactory.compareTo(TIPOS_REPOSITORY_FACTORY[0])==0) {	
			return MemoriaRepositoryFactory.getSoleInstance();
		}else if(this.tipoRepositoryFactory.compareTo(TIPOS_REPOSITORY_FACTORY[1])==0){
			return BDRRepositoryFactory.getSoleInstance();
		}else {
			throw new RuntimeException("Tipo de factory não existente.");
		}
    }

    public String[] getTiposElementoGraficoFactory() {
        return TIPOS_ELEMENTO_GRAFICO_FACTORY;
    }

    public void setTipoElementoGraficoFactory(String tipo) {
        tipoElementoGraficoFactory = tipo;
        configurar();
    }

    private ElementoGraficoFactory getElementoGraficoFactory() {
		if(this.tipoElementoGraficoFactory.compareTo(TIPOS_ELEMENTO_GRAFICO_FACTORY[0])==0) {
			return ElementoGraficoTextoFactory.getSoleInstance();
			
		}else if(this.tipoElementoGraficoFactory.compareTo(TIPOS_ELEMENTO_GRAFICO_FACTORY[1])==0){
			return ElementoGraficoImagemFactory.getSoleInstance();
			
		}else {
			throw new RuntimeException("Tipo de factory não existente.");
		}
    }

    public LetraFactory getLetraFactory() {
        return this.getElementoGraficoFactory();
    }

    public BonecoFactory getBonecoFactory() {
        return this.getElementoGraficoFactory();
    }

    public String[] getTiposRodadaFactory() {
        return TIPOS_RODADA_FACTORY;
    }

    // Métodos para definir o tipo de cada fábrica
    public void setTipoRodadaFactory(String tipo) {
        tipoRodadaFactory = tipo;
        configurar();
    }

    public RodadaFactory getRodadaFactory() {
		if(this.tipoRodadaFactory.compareTo(TIPOS_RODADA_FACTORY[0])==0) {
			return RodadaSorteioFactory.getSoleInstance();
		}else {
			throw new RuntimeException("Tipo de factory não existente.");
		}

    }


    public JogadorFactory getJogadorFactory() {
        return JogadorFactoryImpl.getSoleInstance();
    }

    public PalavraFactory getPalavraFactory() {
        return PalavraFactoryImpl.getSoleInstance();
    }

    public TemaFactory getTemaFactory() {
        return TemaFactoryImpl.getSoleInstance();
    }

}
