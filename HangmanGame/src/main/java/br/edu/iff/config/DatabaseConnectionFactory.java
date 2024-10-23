package main.java.br.edu.iff.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


//Conexão com database utilizando arquivo de configuração do database
//Singleton
public class DatabaseConnectionFactory {

    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            Properties properties = new Properties();
            try (InputStream input = DatabaseConnectionFactory.class.getClassLoader().getResourceAsStream("database.properties")) {
                properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao carregar as propriedades do banco de dados.", e);
            }

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            String driver = properties.getProperty("db.driver");

            try {
                // Carrega o driver JDBC
                Class.forName(driver);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("Driver JDBC não encontrado.", e);
            }

            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}
