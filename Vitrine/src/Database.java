import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Database {
    private Connection connection;

    // Construtor que inicializa a conexão com o banco de dados
    public Database(String url, String user, String password) {
        try {
            // Use a URL correta do banco de dados
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database_teste?serverTimezone=UTC", "root", "@admin");
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    // Método para adicionar um cliente ao banco de dados
    public void adicionarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, cpf, email, celular) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getCelular());
            stmt.executeUpdate();
            System.out.println("Cliente adicionado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar cliente: " + e.getMessage());
        }
    }

    // Método para editar um cliente no banco de dados
    public void editarCliente(Cliente clienteNovo) {
        String sql = "UPDATE clientes SET nome = ?, email = ?, celular = ? WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, clienteNovo.getNome());
            stmt.setString(2, clienteNovo.getEmail());
            stmt.setString(3, clienteNovo.getCelular());
            stmt.setString(4, clienteNovo.getCpf());
            stmt.executeUpdate();
            System.out.println("Cliente editado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao editar cliente: " + e.getMessage());
        }
    }

    // Método para excluir um cliente do banco de dados
    public void excluirCliente(String cpf) {
        String sql = "DELETE FROM clientes WHERE cpf = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, cpf);
            stmt.executeUpdate();
            System.out.println("Cliente excluído com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
        }
    }
}
