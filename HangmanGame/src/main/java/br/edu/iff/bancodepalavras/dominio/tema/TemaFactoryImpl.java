package main.java.br.edu.iff.bancodepalavras.dominio.tema;

import main.java.br.edu.iff.factory.EntityFactory;

public class TemaFactoryImpl extends EntityFactory implements TemaFactory {

    private static TemaFactoryImpl soleInstance;

    public static void createSoleInstance(TemaRepository repository){
        if (soleInstance==null){
            soleInstance = new TemaFactoryImpl(repository);
        }
    }

    public static TemaFactoryImpl getSoleInstance(){
        if (soleInstance==null){
            throw new TemaInstanceException("Tema ainda não instanciado");
        }
        return soleInstance;
    }

    @Override
    public Tema getTema(String nome) {
        if (soleInstance==null){
            //Mudar para um erro mais específico
            throw new TemaInstanceException("Instancia Tema ainda não criada");
        }
        return Tema.criar(this.getProximoId(), nome);
    }

    @Override
    protected long getProximoId() {
        return this.getTemaRepository().getProximoId();
        
    }

    private TemaRepository getTemaRepository() {
        return (TemaRepository) this.getRepository();
    }

    private TemaFactoryImpl(TemaRepository repository){
        super(repository);
    }
    
}
