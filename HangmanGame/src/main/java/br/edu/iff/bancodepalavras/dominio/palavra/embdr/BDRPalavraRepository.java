package main.java.br.edu.iff.bancodepalavras.dominio.palavra.embdr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.br.edu.iff.bancodepalavras.dominio.palavra.Palavra;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.Tema;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.embdr.BDRTemaRepository;
import main.java.br.edu.iff.config.DatabaseConnectionFactory;
import main.java.br.edu.iff.repository.RepositoryException;

public class BDRPalavraRepository {
    private static BDRPalavraRepository soleInstance;

    // Construtor privado para o padrão Singleton
    private BDRPalavraRepository() {}

    // Método para obter a instância única
    public static BDRPalavraRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BDRPalavraRepository();
        }
        return soleInstance;
    }

    public void inserir(Palavra palavra) throws RepositoryException {
        String sql = "INSERT INTO Palavra (id, texto, tema_id) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, palavra.getId());
            statement.setString(2, palavra.toString()); // Usar toString() para obter a palavra em string
            statement.setLong(3, palavra.getTema().getId()); // ID do tema associado
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao inserir palavra: " + e.getMessage());
        }
    }

    public void atualizar(Palavra palavra) throws RepositoryException {
        String sql = "UPDATE Palavra SET texto = ?, tema_id = ? WHERE id = ?";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, palavra.toString()); // Usar toString() para obter a palavra em string
            statement.setLong(2, palavra.getTema().getId()); // ID do tema associado
            statement.setLong(3, palavra.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao atualizar palavra: " + e.getMessage());
        }
    }

    public void remover(Palavra palavra) throws RepositoryException {
        String sql = "DELETE FROM Palavra WHERE id = ?";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, palavra.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao remover palavra: " + e.getMessage());
        }
    }

    public Palavra getPorId(long id) {
        String sql = "SELECT * FROM Palavra WHERE id = ?";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long temaId = resultSet.getLong("tema_id");
                Tema tema = BDRTemaRepository.getSoleInstance().getPorId(temaId); // Obter o tema associado
                return Palavra.reconstituir(resultSet.getLong("id"), resultSet.getString("texto"), tema);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar palavra por ID: " + e.getMessage());
        }
        return null; // Palavra não encontrada
    }

    public List<Palavra> getPorTema(Tema tema) {
        List<Palavra> palavras = new ArrayList<>();
        String sql = "SELECT * FROM Palavra WHERE tema_id = ?";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, tema.getId());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                palavras.add(Palavra.reconstituir(resultSet.getLong("id"),
                                                   resultSet.getString("texto"),
                                                   tema));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar palavras por tema: " + e.getMessage());
        }
        return palavras; // Retorna a lista de palavras
    }

    public long getProximoId() {
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COALESCE(MAX(id), 0) + 1 AS proximo_id FROM Palavra")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("proximo_id");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar próximo ID da Palavra: " + e.getMessage());
        }
        return -1; // Retorna -1 em caso de erro
    }
}
