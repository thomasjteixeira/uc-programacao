
public class ContaCorrente extends Conta {

    public ContaCorrente(Cliente cliente) {
        super(cliente, "corrente");
    }
    
    public void imprimirExtrato() {
        System.out.println("=== Extrato Conta Corrente ===");
        super.imprimirInfosComuns();
    }

}