import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Interface extends JFrame {
    private static List<Cliente> clientes = new ArrayList<>(); // Lista de clientes
    private static List<Agendamento> agendamentos = new ArrayList<>(); // Lista de agendamentos

    public Interface() {
        setTitle("Sistema de Clientes e Agendamentos");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Botões para cada funcionalidade
        JButton addClienteBtn = new JButton("Adicionar Cliente");
        JButton editClienteBtn = new JButton("Editar Cliente");
        JButton deleteClienteBtn = new JButton("Excluir Cliente");
        JButton scheduleBtn = new JButton("Agendar Serviço");
        JButton listAgendamentosBtn = new JButton("Listar Agendamentos");
        JButton editAgendamentoBtn = new JButton("Editar Agendamento");
        JButton deleteAgendamentoBtn = new JButton("Excluir Agendamento");

        // Adiciona botões à janela
        add(addClienteBtn);
        add(editClienteBtn);
        add(deleteClienteBtn);
        add(scheduleBtn);
        add(listAgendamentosBtn);
        add(editAgendamentoBtn);
        add(deleteAgendamentoBtn);

        // Ações para cada botão
        addClienteBtn.addActionListener(e -> adicionarCliente());
        editClienteBtn.addActionListener(e -> editarCliente());
        deleteClienteBtn.addActionListener(e -> excluirCliente());
        scheduleBtn.addActionListener(e -> agendarServico());
        listAgendamentosBtn.addActionListener(e -> listarAgendamentos());
        editAgendamentoBtn.addActionListener(e -> editarAgendamento());
        deleteAgendamentoBtn.addActionListener(e -> excluirAgendamento());
    }

    // Implementação das funções de interface
    private void adicionarCliente() {
        String nome = JOptionPane.showInputDialog("Nome:");
        String cpf = JOptionPane.showInputDialog("CPF:");
        String email = JOptionPane.showInputDialog("Email:");
        String celular = JOptionPane.showInputDialog("Celular:");

        Cliente cliente = new Cliente(nome, cpf, email, celular);
        Database database = new Database("jdbc:mysql://localhost:3306/seu_banco", "usuario", "senha");
        database.adicionarCliente(cliente);
        clientes.add(cliente);
        JOptionPane.showMessageDialog(this, "Cliente adicionado com sucesso!");
    }

    private void editarCliente() {
        listarClientes();
        String clienteIndexStr = JOptionPane.showInputDialog("Escolha o número do cliente para editar:");
        int clienteIndex = Integer.parseInt(clienteIndexStr) - 1;

        if (clienteIndex < 0 || clienteIndex >= clientes.size()) {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado!");
            return;
        }

        Cliente clienteAntigo = clientes.get(clienteIndex);
        String nome = JOptionPane.showInputDialog("Novo Nome (atual: " + clienteAntigo.getNome() + "):", clienteAntigo.getNome());
        String cpf = JOptionPane.showInputDialog("Novo CPF (atual: " + clienteAntigo.getCpf() + "):", clienteAntigo.getCpf());
        String email = JOptionPane.showInputDialog("Novo Email (atual: " + clienteAntigo.getEmail() + "):", clienteAntigo.getEmail());
        String celular = JOptionPane.showInputDialog("Novo Celular (atual: " + clienteAntigo.getCelular() + "):", clienteAntigo.getCelular());

        Cliente clienteNovo = new Cliente(nome, cpf, email, celular);

        // Chama o método para editar o cliente
        Database database = new Database("jdbc:mysql://localhost:3306/seu_banco", "usuario", "senha");
        database.editarCliente(clienteNovo); // Passa apenas o cliente novo
        clientes.set(clienteIndex, clienteNovo); // Atualiza a lista local
        JOptionPane.showMessageDialog(this, "Cliente editado com sucesso!");
    }

    private void excluirCliente() {
        listarClientes();
        String clienteIndexStr = JOptionPane.showInputDialog("Escolha o número do cliente para excluir:");
        int clienteIndex = Integer.parseInt(clienteIndexStr) - 1;

        if (clienteIndex < 0 || clienteIndex >= clientes.size()) {
            JOptionPane.showMessageDialog(this, "Cliente não encontrado!");
            return;
        }

        Cliente cliente = clientes.get(clienteIndex);
        Database database = new Database("jdbc:mysql://localhost:3306/seu_banco", "usuario", "senha");
        database.excluirCliente(cliente.getCpf()); // Passa o CPF para excluir
        clientes.remove(clienteIndex);
        JOptionPane.showMessageDialog(this, "Cliente excluído com sucesso!");
    }

    private void agendarServico() {
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "É necessário ter pelo menos um cliente para agendar.");
            return;
        }

        listarClientes();
        String clienteIndexStr = JOptionPane.showInputDialog("Escolha o número do cliente:");
        int clienteIndex = Integer.parseInt(clienteIndexStr) - 1;

        String dataInput = JOptionPane.showInputDialog("Digite a data do agendamento (DD/MM/YYYY):");
        String horaInput = JOptionPane.showInputDialog("Digite a hora do agendamento (HH:MM):");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(dataInput + " " + horaInput, formatter);

        String tipoAtendimento = JOptionPane.showInputDialog("Digite o tipo de atendimento (presencial ou virtual):");

        Agendamento agendamento = new Agendamento(dataHora, tipoAtendimento, clientes.get(clienteIndex));
        agendamentos.add(agendamento);

        JOptionPane.showMessageDialog(this, "Agendamento realizado com sucesso!");
    }

    private void listarAgendamentos() {
        StringBuilder lista = new StringBuilder("Agendamentos:\n");
        for (Agendamento agendamento : agendamentos) {
            lista.append("Cliente: ").append(agendamento.getCliente().getNome())
                    .append(", Data e Hora: ").append(agendamento.getDataHora())
                    .append(", Tipo de Atendimento: ").append(agendamento.getTipoAtendimento()).append("\n");
        }
        JOptionPane.showMessageDialog(this, lista.toString());
    }

    private void editarAgendamento() {
        listarAgendamentos();
        String agendamentoIndexStr = JOptionPane.showInputDialog("Escolha o número do agendamento para editar:");
        int agendamentoIndex = Integer.parseInt(agendamentoIndexStr) - 1;

        if (agendamentoIndex < 0 || agendamentoIndex >= agendamentos.size()) {
            JOptionPane.showMessageDialog(this, "Agendamento não encontrado!");
            return;
        }

        Agendamento agendamento = agendamentos.get(agendamentoIndex);
        String novaDataHora = JOptionPane.showInputDialog("Nova Data e Hora (atual: " + agendamento.getDataHora() + "):");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        LocalDateTime dataHora = LocalDateTime.parse(novaDataHora, formatter);
        String novoTipoAtendimento = JOptionPane.showInputDialog("Novo Tipo de Atendimento (atual: " + agendamento.getTipoAtendimento() + "):");

        agendamento = new Agendamento(dataHora, novoTipoAtendimento, agendamento.getCliente());
        agendamentos.set(agendamentoIndex, agendamento);

        JOptionPane.showMessageDialog(this, "Agendamento editado com sucesso!");
    }

    private void excluirAgendamento() {
        listarAgendamentos();
        String agendamentoIndexStr = JOptionPane.showInputDialog("Escolha o número do agendamento para excluir:");
        int agendamentoIndex = Integer.parseInt(agendamentoIndexStr) - 1;

        if (agendamentoIndex < 0 || agendamentoIndex >= agendamentos.size()) {
            JOptionPane.showMessageDialog(this, "Agendamento não encontrado!");
            return;
        }

        agendamentos.remove(agendamentoIndex);
        JOptionPane.showMessageDialog(this, "Agendamento excluído com sucesso!");
    }

    private void listarClientes() {
        StringBuilder lista = new StringBuilder("Clientes:\n");
        for (int i = 0; i < clientes.size(); i++) {
            lista.append((i + 1)).append(". ").append(clientes.get(i).getNome()).append("\n");
        }
        JOptionPane.showMessageDialog(this, lista.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Interface().setVisible(true));
    }
}
