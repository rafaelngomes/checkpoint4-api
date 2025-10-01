package br.com.fiap.checkpoint4.dto;

import java.math.BigDecimal;

public class ProfissionalRequestCreate {

    private String nome;
    private String especialidade;
    private BigDecimal valorHora;

    public ProfissionalRequestCreate() {}

    public ProfissionalRequestCreate(String nome, String especialidade, BigDecimal valorHora) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.valorHora = valorHora;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public BigDecimal getValorHora() {
        return valorHora;
    }

    public void setValorHora(BigDecimal valorHora) {
        this.valorHora = valorHora;
    }
}
