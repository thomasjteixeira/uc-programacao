import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Conta {

    private static final int AGENCIA_PADRAO = 1;
    private static int SEQUENCIAL = 1;

    protected int agencia;
    protected int numero;
    protected double saldo;
    protected Cliente cliente;
    protected String tipo;

    public Conta(Cliente cliente, String tipo) {
        this.agencia = Conta.AGENCIA_PADRAO;
        this.numero = SEQUENCIAL++;
        this.cliente = cliente;
        this.tipo = tipo;
        save();
    }

    public void sacar(double valor) {
        saldo -= valor;
        update();
    }

    public void depositar(double valor) {
        saldo += valor;
        update();
    }

    public void transferir(double valor, Conta contaDestino) {
        if (saldo >= valor) {
            this.sacar(valor);
            contaDestino.depositar(valor);
            System.out.println("Transferência realizada com sucesso. Valor transferido: " + valor);
        } else {
            System.out.println("Saldo insuficiente para realizar a transferência.");
        }
    }

  protected void imprimirInfosComuns() {
        String sql = "SELECT c.nome, ct.agencia, ct.numero, ct.saldo "
                   + "FROM cliente c JOIN conta ct ON c.id = ct.cliente_id "
                   + "WHERE ct.agencia = ? AND ct.numero = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.agencia);
            pstmt.setInt(2, this.numero);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String nome = rs.getString("nome");
                int agencia = rs.getInt("agencia");
                int numero = rs.getInt("numero");
                double saldo = rs.getDouble("saldo");

                System.out.println(String.format("Titular: %s", nome));
                System.out.println(String.format("Agencia: %d", agencia));
                System.out.println(String.format("Numero: %d", numero));
                System.out.println(String.format("Saldo: %.2f", saldo));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void save() {
        String sql = "INSERT INTO conta(agencia, numero, saldo, cliente_id, tipo) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.agencia);
            pstmt.setInt(2, this.numero);
            pstmt.setDouble(3, this.saldo);
            pstmt.setInt(4, this.cliente.getId());
            pstmt.setString(5, this.tipo);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void update() {
        String sql = "UPDATE conta SET saldo = ? WHERE agencia = ? AND numero = ?";

        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, this.saldo);
            pstmt.setInt(2, this.agencia);
            pstmt.setInt(3, this.numero);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
