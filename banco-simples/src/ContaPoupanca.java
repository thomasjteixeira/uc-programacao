/*
  Classe filha da classe Conta; Note que essa classe apenas implementa o método imprimirExtrato e um construtor.
  Todo o resto é herdado da classe pai (Conta). Além disso, nesta classe existe uma sobrescrita do método sacar 
  apenas com intuito didádico de demonstração.
*/
public class ContaPoupanca extends Conta {

    private static final double LIMITE_SAQUE = 500;

    public ContaPoupanca(Cliente cliente) {
        super(cliente, "poupança");
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

        // O uso da palavra super, indica que o método imprimirInfosComuns está sendo invocado da super classe (Conta)
        super.imprimirInfosComuns();
    }
}
