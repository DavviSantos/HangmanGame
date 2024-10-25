package main.java.br.edu.iff.bancodepalavras.dominio.tema.embdr;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.Tema;
import main.java.br.edu.iff.bancodepalavras.dominio.tema.TemaRepository;
import main.java.br.edu.iff.config.DatabaseConnectionFactory;
import main.java.br.edu.iff.repository.RepositoryException;

public class BDRTemaRepository implements TemaRepository {
    private static BDRTemaRepository soleInstance;

    // Construtor privado para o padrão Singleton
    private BDRTemaRepository() {}

    // Método para obter a instância única
    public static BDRTemaRepository getSoleInstance() {
        if (soleInstance == null) {
            soleInstance = new BDRTemaRepository();
        }
        return soleInstance;
    }

    @Override
    public void inserir(Tema tema) throws RepositoryException {
        String sql = "INSERT INTO Tema (id, nome) VALUES (?, ?)";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, tema.getId());
            statement.setString(2, tema.getNome());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao inserir tema: " + e.getMessage());
        }
    }

    @Override
    public void atualizar(Tema tema) throws RepositoryException {
        String sql = "UPDATE Tema SET nome = ? WHERE id = ?";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, tema.getNome());
            statement.setLong(2, tema.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao atualizar tema: " + e.getMessage());
        }
    }

    @Override
    public void remover(Tema tema) throws RepositoryException {
        String sql = "DELETE FROM Tema WHERE id = ?";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, tema.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException("Erro ao remover tema: " + e.getMessage());
        }
    }

    @Override
    public Tema[] getTodos() {
        List<Tema> temas = new ArrayList<>();
        String sql = "SELECT * FROM Tema";  // Corrigido para "Tema"
        
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            
            while (resultSet.next()) {
                temas.add(Tema.reconstituir(resultSet.getLong("id"),
                                            resultSet.getString("nome")));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os temas: " + e.getMessage());
        }
        
        return temas.toArray(Tema[]::new); // Conversão para array
    }

    @Override
    public Tema getPorId(long id) {
        String sql = "SELECT * FROM Tema WHERE id = ?";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Tema.reconstituir(resultSet.getLong("id"),
                                        resultSet.getString("nome"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar tema por ID: " + e.getMessage());
        }
        return null; // Tema não encontrado
    }

    @Override
    public Tema[] getPorNome(String nome) {
        List<Tema> temas = new ArrayList<>();
        String sql = "SELECT * FROM Tema WHERE nome LIKE ?";
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + nome + "%");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                temas.add(Tema.reconstituir(resultSet.getLong("id"),
                                            resultSet.getString("nome")));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar temas por nome: " + e.getMessage());
        }
        return temas.toArray(Tema[]::new);
    }

    @Override
    @SuppressWarnings("CallToPrintStackTrace")
    public long getProximoId() {
        try (Connection connection = DatabaseConnectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COALESCE(MAX(id), 0) + 1 AS proximo_id FROM Tema")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("proximo_id");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar próximo ID do Tema: " + e.getMessage());
        }
        return -1; // Retorna -1 em caso de erro
    }
}
