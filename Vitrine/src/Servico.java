public class Servico {
    public enum TipoServico {
        TRAFEGO_PAGO("Tráfego Pago"),
        SITES("Sites"),
        EDICAO_VIDEOS("Edição de Vídeos");

        private final String descricao;

        TipoServico(String descricao) {
            this.descricao = descricao;
        }

        public String getDescricao() {
            return descricao;
        }
    }

    private TipoServico tipoServico;

    public Servico(TipoServico tipoServico) {
        this.tipoServico = tipoServico;
    }

    // Getter
    public TipoServico getTipoServico() {
        return tipoServico;
    }
}
