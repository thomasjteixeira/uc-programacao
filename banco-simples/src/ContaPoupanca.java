
public class ContaPoupanca extends Conta {

    private static final double LIMITE_SAQUE = 500;

    public ContaPoupanca(Cliente cliente) {
        super(cliente);
    }

    @Override
    public void sacar(double valor) {

        if (valor <= LIMITE_SAQUE) {
            super.sacar(valor);
            System.out.println("Valor sacado: " + valor);
        } else {
            System.out.println("Limite de saque excedido. O limite máximo é de: " + LIMITE_SAQUE);
        }
    }

    public void imprimirExtrato() {
        System.out.println("=== Extrato Conta Poupança ===");
        super.imprimirInfosComuns();
    }
}
