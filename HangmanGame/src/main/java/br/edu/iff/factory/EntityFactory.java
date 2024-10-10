package main.java.br.edu.iff.factory;

import main.java.br.edu.iff.repository.Repository;

public abstract class EntityFactory {
    protected Repository repository;

    // Construtor que recebe um repositório
    protected EntityFactory(Repository repository) {
        this.repository = repository;
    }

    // Método para obter o repositório
    protected Repository getRepository() {
        return repository;
    }

    // Método abstrato para obter o próximo ID
    protected abstract long getProximoId();
}