import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
  Classe conta. Essa classe define os atributos e métodos relativos ao funcionamento
  de uma conta no banco. Note o use de atributos protected com o intuito de possibilitar
  o acesso aos mesmo pelas classes filhas (ContaCorrente e ContaPoupanca). 
*/
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

    // método sacar que subtrai o valor sacado do saldo da conta; note a invocação do método
    // update que atualiza os dados no banco de dados da aplicação
    public void sacar(double valor) {
        saldo -= valor;
        update();
    }
    
    // método depositar que adiona o valor ao saldo da conta; Igualmente como ocorre no método sacar
    // o método update é invocado para atualizar o banco de dados. 
    public void depositar(double valor) {
        saldo += valor;
        update();
    }

    // O método transferir recebe o valor a ser transferido e a conta destino
    public void transferir(double valor, Conta contaDestino) {
        // Verifica se o saldo em conta é suficiente para realizar a transferência
        if (saldo >= valor) {
            // Em caso afirmativo (true) deduz o valor da conta atua e deposita o mesmo valor na conta destino;            
            this.sacar(valor);
            contaDestino.depositar(valor);
            System.out.println("Transferência realizada com sucesso. Valor transferido: " + valor);
        } else {
            // Caso o saldo não seja sufiente, apresenta a mensagem abaixo e não faz a transferência
            System.out.println("Saldo insuficiente para realizar a transferência.");
        }
    }

  /*
    Este método é utilizado para ler os dados do banco de dados e imprimir as informações no terminal da aplicação.
    Seu funcionamento segue de forma análoga ao save e update da classe Client; Note a criação de uma String
      sql, o estabelecimento da conexão e uso da classe PreparedStatement para execução da query
  */  
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
                // Para cada registro encontrado no banco, é criado um registro dentro do objeto rs
                // que detém os dados selecionados (nome, agencia, numero e saldo)
                String nome = rs.getString("nome");
                int agencia = rs.getInt("agencia");
                int numero = rs.getInt("numero");
                double saldo = rs.getDouble("saldo");

                // Os dados contido em rs são então salvos em variáveis de acordo com seu tipo e posteriormente
                // apresnetados na tela usando o println abaixo;
                System.out.println(String.format("Titular: %s", nome));
                System.out.println(String.format("Agencia: %d", agencia));
                System.out.println(String.format("Numero: %d", numero));
                System.out.println(String.format("Saldo: %.2f", saldo));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /*
      método save que funciona de forma análoga ao save da classe Client; Note a criação de uma String
      sql, o estabelecimento da conexão e uso da classe PreparedStatement para execução da query
    */
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

    /*
      método save que funciona de forma análoga ao save da classe Client; Note a criação de uma String
      sql, o estabelecimento da conexão e uso da classe PreparedStatement para execução da query
    */
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
