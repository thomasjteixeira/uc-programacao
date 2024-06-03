public class Main {

    public static void main(String[] args) {
        Database.createTables();

        Cliente c1 = new Cliente("Thomas");
        c1.setNome("Thomas Alterado");
        Cliente c2 = new Cliente("Jefferson");

        ContaCorrente cc1 = new ContaCorrente(c1);
        ContaPoupanca poupanca1 = new ContaPoupanca(c2);

        cc1.depositar(1000);
        cc1.transferir(600, poupanca1);

        cc1.imprimirExtrato();
        poupanca1.imprimirExtrato();

    }
}
