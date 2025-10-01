package br.com.fiap.checkpoint4.dto;

import br.com.fiap.checkpoint4.model.StatusConsulta;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

public class ConsultaRequestUpdate {

    private LocalDate dataConsulta;
    private StatusConsulta statusConsulta;
    private BigInteger quantidadeHoras;
    private BigDecimal valorConsulta;

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDate dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public StatusConsulta getStatusConsulta() {
        return statusConsulta;
    }

    public void setStatusConsulta(StatusConsulta statusConsulta) {
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
