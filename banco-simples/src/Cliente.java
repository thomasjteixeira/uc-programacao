import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cliente {

    private int id;
    private String nome;

    public Cliente(String nome) {
        this.nome = nome;
        save();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        update();
    }
    
    public int getId(){
        return id;
    }

    private void save() {
        String sql = "INSERT INTO cliente(nome) VALUES(?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.nome);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void update() {
        String sql = "UPDATE cliente SET nome = ? WHERE id = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.nome);
            pstmt.setInt(2, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
