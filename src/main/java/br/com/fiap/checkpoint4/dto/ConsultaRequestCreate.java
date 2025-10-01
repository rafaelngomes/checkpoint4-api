package br.com.fiap.checkpoint4.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

public class ConsultaRequestCreate {

    private Long pacienteId;
    private Long profissionalId;
    private LocalDateTime dataConsulta;
    private String statusConsulta;
    private BigInteger quantidadeHoras;
    private BigDecimal valorConsulta;

    public ConsultaRequestCreate() {}

    public ConsultaRequestCreate(Long pacienteId, Long profissionalId, LocalDateTime dataConsulta, String statusConsulta, BigInteger quantidadeHoras, BigDecimal valorConsulta) {
        this.pacienteId = pacienteId;
        this.profissionalId = profissionalId;
        this.dataConsulta = dataConsulta;
        this.statusConsulta = statusConsulta;
        this.quantidadeHoras = quantidadeHoras;
        this.valorConsulta = valorConsulta;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    public LocalDateTime getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDateTime dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public String getStatusConsulta() {
        return statusConsulta;
    }

    public void setStatusConsulta(String statusConsulta) {
        this.statusConsulta = statusConsulta;
    }

    public BigInteger getQuantidadeHoras() {
        return quantidadeHoras;
    }

    public void setQuantidadeHoras(BigInteger quantidadeHoras) {
        this.quantidadeHoras = quantidadeHoras;
    }

    public BigDecimal getValorConsulta() {
        return valorConsulta;
    }

    public void setValorConsulta(BigDecimal valorConsulta) {
        this.valorConsulta = valorConsulta;
    }
}
