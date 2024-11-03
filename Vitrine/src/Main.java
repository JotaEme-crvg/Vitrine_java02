import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<Cliente> clientes = new ArrayList<>(); // Lista de clientes
    private static List<Agendamento> agendamentos = new ArrayList<>(); // Lista de agendamentos
    private static String[] servicosDisponiveis = {"Tráfego Pago", "Sites", "Edição de Vídeos"}; // Serviços disponíveis

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        // Menu principal
        do {
            System.out.println("1. Adicionar Cliente");
            System.out.println("2. Editar Cliente");
            System.out.println("3. Excluir Cliente");
            System.out.println("4. Agendar Serviço");
            System.out.println("5. Listar Agendamentos");
            System.out.println("6. Editar Agendamento");
            System.out.println("7. Excluir Agendamento");
            System.out.println("8. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            switch (opcao) {
                case 1:
                    adicionarCliente(scanner);
                    break;
                case 2:
                    editarCliente(scanner);
                    break;
                case 3:
                    excluirCliente(scanner);
                    break;
                case 4:
                    agendarServico(scanner);
                    break;
                case 5:
                    listarAgendamentos();
                    break;
                case 6:
                    editarAgendamento(scanner);
                    break;
                case 7:
                    excluirAgendamento(scanner);
                    break;
                case 8:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        } while (opcao != 8);

        scanner.close();
    }

    // Método para adicionar um cliente
    private static void adicionarCliente(Scanner scanner) {
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Celular: ");
        String celular = scanner.nextLine();

        Cliente cliente = new Cliente(nome, cpf, email, celular);
        clientes.add(cliente);
        System.out.println("Cliente adicionado com sucesso!");
    }

    // Método para editar um cliente
    private static void editarCliente(Scanner scanner) {
        listarClientes();
        System.out.print("Escolha o número do cliente para editar: ");
        int clienteIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Limpa o buffer

        if (clienteIndex < 0 || clienteIndex >= clientes.size()) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        Cliente cliente = clientes.get(clienteIndex);
        System.out.print("Novo Nome (atual: " + cliente.getNome() + "): ");
        String nome = scanner.nextLine();
        System.out.print("Novo CPF (atual: " + cliente.getCpf() + "): ");
        String cpf = scanner.nextLine();
        System.out.print("Novo Email (atual: " + cliente.getEmail() + "): ");
        String email = scanner.nextLine();
        System.out.print("Novo Celular (atual: " + cliente.getCelular() + "): ");
        String celular = scanner.nextLine();

        // Atualiza os dados do cliente
        cliente.setNome(nome);
        cliente.setCpf(cpf);
        cliente.setEmail(email);
        cliente.setCelular(celular);

        System.out.println("Cliente editado com sucesso!");
    }

    // Método para excluir um cliente
    private static void excluirCliente(Scanner scanner) {
        listarClientes();
        System.out.print("Escolha o número do cliente para excluir: ");
        int clienteIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Limpa o buffer

        if (clienteIndex < 0 || clienteIndex >= clientes.size()) {
            System.out.println("Cliente não encontrado!");
            return;
        }

        clientes.remove(clienteIndex);
        System.out.println("Cliente excluído com sucesso!");
    }

    // Método para agendar um serviço
    private static void agendarServico(Scanner scanner) {
        // Verifica se há clientes disponíveis
        if (clientes.isEmpty()) {
            System.out.println("É necessário ter pelo menos um cliente para agendar.");
            return;
        }

        // Lista os clientes e solicita escolha
        listarClientes();
        System.out.print("Escolha o número do cliente: ");
        int clienteIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Limpar o buffer

        // Solicita a data e hora do agendamento
        System.out.print("Digite a data do agendamento (DD/MM/YYYY): ");
        String dataInput = scanner.next();
        System.out.print("Digite a hora do agendamento (HH:MM): ");
        String horaInput = scanner.next();

        // Formatação para LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataInput + " " + horaInput, formatter);

        // Solicita o tipo de atendimento
        System.out.print("Digite o tipo de atendimento (presencial ou virtual): ");
        String tipoAtendimento = scanner.next();

        // Cria um novo agendamento com a referência ao cliente e o adiciona à lista
        Agendamento agendamento = new Agendamento(dataHora, tipoAtendimento, clientes.get(clienteIndex));
        agendamentos.add(agendamento);

        System.out.println("Agendamento realizado com sucesso!");
    }

    // Método para listar clientes
    private static void listarClientes() {
        System.out.println("Clientes:");
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ". " + clientes.get(i).getNome());
        }
    }

    // Método para listar todos os agendamentos
    private static void listarAgendamentos() {
        System.out.println("Agendamentos:");
        for (Agendamento agendamento : agendamentos) {
            System.out.println("Cliente: " + agendamento.getCliente().getNome() +
                    ", Data e Hora: " + agendamento.getDataHora() +
                    ", Tipo de Atendimento: " + agendamento.getTipoAtendimento());
        }
    }

    // Método para editar um agendamento
    private static void editarAgendamento(Scanner scanner) {
        listarAgendamentos();
        System.out.print("Escolha o número do agendamento para editar: ");
        int agendamentoIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Limpa o buffer

        if (agendamentoIndex < 0 || agendamentoIndex >= agendamentos.size()) {
            System.out.println("Agendamento não encontrado!");
            return;
        }

        Agendamento agendamento = agendamentos.get(agendamentoIndex);
        System.out.print("Nova Data e Hora (atual: " + agendamento.getDataHora() + "): ");
        String novaDataHora = scanner.nextLine();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(novaDataHora, formatter);
        System.out.print("Novo Tipo de Atendimento (atual: " + agendamento.getTipoAtendimento() + "): ");
        String novoTipoAtendimento = scanner.nextLine();

        // Atualiza os dados do agendamento
        agendamento = new Agendamento(dataHora, novoTipoAtendimento, agendamento.getCliente()); // Recria o agendamento
        agendamentos.set(agendamentoIndex, agendamento);

        System.out.println("Agendamento editado com sucesso!");
    }

    // Método para excluir um agendamento
    private static void excluirAgendamento(Scanner scanner) {
        listarAgendamentos();
        System.out.print("Escolha o número do agendamento para excluir: ");
        int agendamentoIndex = scanner.nextInt() - 1;
        scanner.nextLine(); // Limpa o buffer

        if (agendamentoIndex < 0 || agendamentoIndex >= agendamentos.size()) {
            System.out.println("Agendamento não encontrado!");
            return;
        }

        agendamentos.remove(agendamentoIndex);
        System.out.println("Agendamento excluído com sucesso!");

    }
}