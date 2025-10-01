package br.com.fiap.checkpoint4.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProfissionalResponse {

    private Long id;
    private String nome;
    private String especialidade;
    private BigDecimal valorHora;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

public ProfissionalResponse(Long id, String nome, String especialidade, BigDecimal valorHora, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.nome = nome;
    this.especialidade = especialidade;
    this.valorHora = valorHora;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
}



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
