public class Main {
    // Método main. Início da execução do projeto. 
    public static void main(String[] args) {
        // Criação das tabelas do banco de dados
        Database.createTables();

        // Criação dos objetos que representam os clientes que serão 
        // usados no teste das funcionalidades da Conta;
        Cliente c1 = new Cliente("Thomas");
        c1.setNome("Thomas Alterado");
        Cliente c2 = new Cliente("Hissamu");

        // Criação das contas, corrente e poupança. Note que cada
        // conta recebe via contrutor um objeto cliente. 
        // cc1 é a conta corrente de Thomas e poupanca1 a conta de Hissamu
        ContaCorrente cc1 = new ContaCorrente(c1);
        ContaPoupanca poupanca1 = new ContaPoupanca(c2);

        // invocação dos métodos depositar e transferir do objeto cc1 
        // question: como cc1 sabe para qual conta deve transferir o dinheiro?
        cc1.depositar(1000);
        cc1.transferir(600, poupanca1);

        // invocação dos métodos de imprimir extrato das contas cc1 e poupanca1
        // Note que ambos os objetos possuem o método imprimirExtrato()
        // Isso é possível graças à herança criada entre Conta, ContaCorrente e 
        // ContaPoupanca. 
        cc1.imprimirExtrato();
        poupanca1.imprimirExtrato();

    }
}
