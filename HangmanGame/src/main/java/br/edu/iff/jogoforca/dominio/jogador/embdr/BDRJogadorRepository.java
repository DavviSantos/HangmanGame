package main.java.br.edu.iff.jogoforca.dominio.jogador.embdr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import main.java.br.edu.iff.config.DatabaseConnectionFactory;
import main.java.br.edu.iff.jogoforca.dominio.jogador.Jogador;
import main.java.br.edu.iff.jogoforca.dominio.jogador.JogadorRepository;
import main.java.br.edu.iff.repository.RepositoryException;

public class BDRJogadorRepository implements JogadorRepository {
    private static BDRJogadorRepository soleInstance;

    // Construtor privado para o padrão Singleton
    private BDRJogadorRepository() {
        // Inicialização se necessário
    }

    // Método para obter a instância única
    public static BDRJogadorRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BDRJogadorRepository();
        }
        return soleInstance;
    }

    @Override
    public void inserir(Jogador jogador) throws RepositoryException {
        String sql = "INSERT INTO Jogador (id, nome, pontuacao) VALUES (?, ?, ?)";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, jogador.getId());
            statement.setString(2, jogador.getNome());
            statement.setInt(3, jogador.getPontuacao());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao inserir jogador: " + e.getMessage());
        }
    }

    @Override
    public void atualizar(Jogador jogador) throws RepositoryException {
        String sql = "UPDATE Jogador SET nome = ?, pontuacao = ? WHERE id = ?";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, jogador.getNome());
            statement.setInt(2, jogador.getPontuacao());
            statement.setLong(3, jogador.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao atualizar jogador: " + e.getMessage());
        }
    }

    @Override
    public void remover(Jogador jogador) throws RepositoryException {
        String sql = "DELETE FROM Jogador WHERE id = ?";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, jogador.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao remover jogador: " + e.getMessage());
        }
    }

    @Override
    public Jogador getPorId(long id) {
        String sql = "SELECT * FROM Jogador WHERE id = ?";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Jogador.reconstruir(resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getInt("pontuacao"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar jogador por ID: " +e.getMessage());
        }
        return null; // Jogador não encontrado
    }

    @Override
    public Jogador getPorNome(String nome) {
        String sql = "SELECT * FROM Jogador WHERE nome = ?";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nome);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Jogador.reconstruir(resultSet.getLong("id"),
                        resultSet.getString("nome"),
                        resultSet.getInt("pontuacao"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar jogador por nome: " +e.getMessage());
        }
        return null; // Jogador não encontrado
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public long getProximoId() {
        // Implemente a lógica para obter o próximo ID buscando o maior ID no banco de dados e somando 1
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COALESCE(MAX(id), 0) + 1 AS proximo_id FROM Jogador")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("proximo_id");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar proximo ID do jogador: " +e.getMessage());
        }
        return -1; // Retorna -1 em caso de erro
    }
}
