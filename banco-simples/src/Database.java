
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
  Classe Database. Esta classe define os atibutos e operações essenciais para conexão e manipulação 
  de dados em um banco de dados. 
*/
public class Database {
    // String de conexão com o banco SQLite. Essa string é necessária para informar 
    // ao objeto Connection onde se encontra o banco ao qual ele deve se conectar
    private static final String URL = "jdbc:sqlite:bank.db";

    // Este método, faz uso da string URL acima para estabelecer a conexão da aplicação 
    // com o banco de dados. Nesse exemplo, um banco SQLite
    public static Connection connect() {
        Connection conn = null;
        try {
            // Carregar o driver JDBC            
            Class.forName("org.sqlite.JDBC");
            // Estabelecer a conexão
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }
        return conn;
    }

    // Este método cria as tabelas cliente e conta no banco de dados.
    public static void createTables() {
        // Query de criação da tabela cliente. Note o campo id definido como auto-incrementável
        String sqlCliente = "CREATE TABLE IF NOT EXISTS cliente ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nome TEXT NOT NULL"
                + ");";

        // Query de criação da tabela conta. Note que essa tabela estabelece uma relação com a 
        // tabela cliente por meio de uma chave estrangeira (FOREIGN KEY). 
        String sqlConta = "CREATE TABLE IF NOT EXISTS conta ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " agencia INTEGER NOT NULL,"
                + " numero INTEGER NOT NULL,"
                + " saldo REAL,"
                + " cliente_id INTEGER,"
                + " tipo TEXT NOT NULL,"
                + " FOREIGN KEY (cliente_id) REFERENCES cliente (id)"
                + ");";

        // Estabelecimento da conexão com o banco de dados. Note o uso do método connect para isso;
        try (Connection conn = connect();
            Statement stmt = conn.createStatement()) {
            // Execução das duas queries definidas acima. Após a execução das mesmas, espera-se que
            // as duas tabelas estejam devidamente criadas. 
            stmt.execute(sqlCliente);
            stmt.execute(sqlConta);
        } catch (SQLException e) {
            // Caso algum erro ocorra durante a conexão ou execução das queries de criação de tabelas
            // uma exceção é gerada e o código desvia para esse trecho, que apenas apresenta o que causou 
            // o erro em questão.
            System.out.println(e.getMessage());
        }
    }
}
