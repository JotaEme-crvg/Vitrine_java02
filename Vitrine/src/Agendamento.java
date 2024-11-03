import java.time.LocalDateTime;

public class Agendamento {
    private LocalDateTime dataHora; // Data e hora do agendamento
    private String tipoAtendimento; // Tipo de atendimento (presencial ou virtual)
    private Cliente cliente; // Referência ao cliente associado

    // Construtor da classe Agendamento
    public Agendamento(LocalDateTime dataHora, String tipoAtendimento, Cliente cliente) {
        this.dataHora = dataHora; // Inicializa a data e hora
        this.tipoAtendimento = tipoAtendimento; // Inicializa o tipo de atendimento
        this.cliente = cliente; // Inicializa o cliente
    }

    // Método getter para obter a data e hora
    public LocalDateTime getDataHora() {
        return dataHora;
    }

    // Método getter para obter o tipo de atendimento
    public String getTipoAtendimento() {
        return tipoAtendimento;
    }

    // Método getter para obter o cliente
    public Cliente getCliente() {
        return cliente;
    }

    // Método para representar o agendamento como uma String
    @Override
    public String toString() {
        return "Agendamento{" +
                "dataHora=" + dataHora +
                ", tipoAtendimento='" + tipoAtendimento + '\'' +
                ", cliente=" + cliente.getNome() +
                '}';
    }
}
