
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
    Classe Cliente do projeto. Essa classe define os atributos que descreve
    o que é um cliente no contexto do banco (id e nome - poderiam ser adicionadas
    muitas outras informações como contato, CPF, sexo, etc...). Além disso, a 
    classe define os métodos getters e setters e os métodos save e update que
    encapsulam as ações de adicionar um registro no banco e atualizar o mesmo.
*/
public class Cliente {

    // Atributos da classe
    private int id;
    private String nome;

    // Método construtor que recebe um nome como parâmetro
    // o atributo id é definido automaticamente usando o campo
    // id auto-incrementável do bando de dados. Veja o método 
    // createTable da classe Database.java para maiores detalhes
    public Cliente(String nome) {
        this.nome = nome;
        save();
    }

    // getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
        update();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*
      Método save que realiza a inserção de um novo registro ao banco na tabela cliente do banco de dados
    */
    private void save() {
        // Query SQL que será executada; O (?) indica um valor que será passado para o atributo nome da tabela
        String sql = "INSERT INTO cliente(nome) VALUES(?)";

        try (Connection conn = Database.connect();
            // Cria-se um objeto do tipo PreparedStatement que recebe a string sql gerada a cima
            PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, this.nome);

            // executa a query SQL em questão
            pstmt.executeUpdate();

            // Após a execução da query, o SGBD retorna um conjunto de dados dentro de um objeto ResultSet
            ResultSet rs = pstmt.getGeneratedKeys();

            // caso o objeto rs não esteja vazio, isso significa que a query de inserção de registro no 
            // banco foi realizada com sucesso. Assim, o id gerado pelo banco é então enfim atribuído 
            // ao atributo id da classe
            if (rs.next()) {
                this.id = rs.getInt(1);
            }
        } catch (SQLException e) {
            // Caso algum erro inesperado ocorra (falha na conexão, incompatibilidade de dados, etc), o
            // comando pstmt.executeUpdate() irá falhar, lançando uma exceção e culminando com a execução
            // desse código que apenas mostra o erro que ocorreu
            System.out.println(e.getMessage());
        }
    }

    // Método update que atualiza os dados de um cliente no banco de dados
    private void update() {
        // Criação da query de atualização de dados do cliente. Note o uso da cláusula "WHERE id =" para
        // atualizar somente o registro específico que possui o id do cliente
        String sql = "UPDATE cliente SET nome = ? WHERE id = ?";

        // estabelece a conexão com o banco de dados e executa a query acima via PreparedStatement
        try (Connection conn = Database.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Aqui é configurado o novo nome que deve ser atualizado no registro do cliente
            pstmt.setString(1, this.nome);
            // Aqui, o id é usado para identificar qual registro da tabela cliente deve er atualizado
            pstmt.setInt(2, this.id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // tratamento de erro similiar ao do método save
            System.out.println(e.getMessage());
        }
    }
}
