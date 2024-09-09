package com.example.filmoretst01.model;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private String anoLancamento;

    public Serie(DadosSerie dadosSerie) {
        this.titulo = dadosSerie.titulo();
        this.totalTemporadas = dadosSerie.totalTemporadas();

        try {
            this.avaliacao = dadosSerie.avaliacao();
        } catch (Exception e) {
            this.avaliacao = 0.0;
        }

        this.anoLancamento = dadosSerie.anoLancamento();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public void setTotalTemporadas(Integer totalTemporadas) {
        this.totalTemporadas = totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public String getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(String anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    @Override
    public String toString() {
        return
                "titulo: " + titulo + "\n" +
                        "totalTemporadas: " + totalTemporadas + "\n" +
                        "avaliacao: " + avaliacao + "\n" +
                        "anoLancamento: " + anoLancamento + "\n";
    }
}
