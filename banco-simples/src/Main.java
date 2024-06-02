public class Main {

    public static void main(String[] args) {
        Database.createTables();

        Cliente c1 = new Cliente("Thomas");
        c1.setNome("Thomas Alterado");

        ContaCorrente cc1 = new ContaCorrente(c1);
        ContaPoupanca poupanca1 = new ContaPoupanca(c1);

        cc1.depositar(100);
        cc1.transferir(100, poupanca1);

        cc1.imprimirExtrato();
        poupanca1.imprimirExtrato();

        Cliente c2 = new Cliente("Jefferson");

        ContaCorrente cc2 = new ContaCorrente(c2);
        ContaPoupanca poupanca2 = new ContaPoupanca(c2);

        cc2.depositar(500);
        cc2.transferir(100, poupanca2);

        cc2.imprimirExtrato();
        poupanca2.imprimirExtrato();
    }
}
