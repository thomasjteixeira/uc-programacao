/*
  Classe filha da classe Conta; Note que essa classe apenas implementa o método imprimirExtrato e um construtor.
  Todo o resto é herdado da classe pai (Conta)
*/
public class ContaCorrente extends Conta {

    public ContaCorrente(Cliente cliente) {
        super(cliente, "corrente");
    }
    
    public void imprimirExtrato() {
        System.out.println("=== Extrato Conta Corrente ===");
        super.imprimirInfosComuns();
    }

}
