package main.java.br.edu.iff.bancodepalavras.dominio.letra;

public abstract class Letra {
    private final char codigo;

    // Construtor protegido para Flyweight
    protected Letra(char codigo) {
        this.codigo = codigo;
    }

    public char getCodigo() {
        return codigo;
    }

    // Método abstrato para exibir no contexto apropriado
    public abstract void exibir(Object contexto);

    // Implementação do método equals
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Letra)) return false;
        Letra outra = (Letra) o;
        return this.codigo == outra.codigo && this.getClass().equals(outra.getClass());

    }

    // Implementação do método hashCode
    @Override
    public int hashCode() {

        return this.codigo+this.getClass().hashCode();
        
    }

    // Sobrescreve toString para retornar a letra como String
    @Override
    public final String toString() {
        return String.valueOf(codigo);
    }
}

