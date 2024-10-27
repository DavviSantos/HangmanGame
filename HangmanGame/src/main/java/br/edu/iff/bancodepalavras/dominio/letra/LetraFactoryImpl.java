package main.java.br.edu.iff.bancodepalavras.dominio.letra;

import java.util.HashMap;
import java.util.Map;

public abstract class LetraFactoryImpl implements LetraFactory {

    private final Map<Character, Letra> pool = new HashMap<>();
    private Letra encoberta;

    // Construtor protegido
    protected LetraFactoryImpl() {

    }

    // Template method: verifica se a letra já existe no pool ou cria e armazena se necessário
    @Override
    public final Letra getLetra(char codigo) {
        if (isCodigoValido(codigo)) {
            return pool.computeIfAbsent(codigo, this::criarLetra);
        }
        throw new IllegalArgumentException("Código de letra inválido: " + codigo);
    }

    // Verifica se o código está dentro do intervalo válido (A-Z)
    private boolean isCodigoValido(char codigo) {
        return (codigo >= 'A' && codigo <= 'Z') || (codigo >= 'a' && codigo <= 'z');
    }

    // Retorna a letra encoberta
    @Override
    public final Letra getLetraEncoberta() {
        if (encoberta == null) {
            encoberta = criarLetra('*');
        }
        return encoberta;

    }

    // Factory method abstrato para ser implementado pelas subclasses
    protected abstract Letra criarLetra(char codigo);
}
