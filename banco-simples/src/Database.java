import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static final String URL = "jdbc:sqlite:bank.db";

    public static Connection connect() {
        Connection conn = null;
        try {
            // Carregar o driver JDBC
            Class.forName("org.sqlite.JDBC");
            // Estabelecer a conexão
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        }
        return conn;
    }

    public static void createTables() {
        String sqlCliente = "CREATE TABLE IF NOT EXISTS cliente ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " nome TEXT NOT NULL"
                + ");";

        String sqlConta = "CREATE TABLE IF NOT EXISTS conta ("
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + " agencia INTEGER NOT NULL,"
                + " numero INTEGER NOT NULL,"
                + " saldo REAL,"
                + " cliente_id INTEGER,"
                + " tipo TEXT NOT NULL,"
                + " FOREIGN KEY (cliente_id) REFERENCES cliente (id)"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sqlCliente);
            stmt.execute(sqlConta);
            System.out.println("Tables created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
