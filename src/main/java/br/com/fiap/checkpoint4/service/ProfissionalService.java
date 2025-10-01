package br.com.fiap.checkpoint4.service;

import br.com.fiap.checkpoint4.dto.*;
import br.com.fiap.checkpoint4.model.Consulta;
import br.com.fiap.checkpoint4.model.Profissional;
import br.com.fiap.checkpoint4.model.StatusConsulta;
import br.com.fiap.checkpoint4.repository.ConsultaRepository;
import br.com.fiap.checkpoint4.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProfissionalService {


    
    @Autowired
    private ProfissionalRepository repository;
    @Autowired
    private ConsultaRepository consultaRepository;

    public ProfissionalResponse criar(ProfissionalRequestCreate dto) {
        Profissional profissional = new Profissional();
        profissional.setNome(dto.getNome());
        profissional.setEspecialidade(dto.getEspecialidade());
        profissional.setValorHora(dto.getValorHora());
        profissional.setCreatedAt(LocalDateTime.now());
        profissional.setUpdatedAt(LocalDateTime.now());

        return toResponse(repository.save(profissional));
    }
    public Map<String, Object> obterEstatisticas(Long id) {
    // buscar todas as consultas relacionadas ao profissional
    List<Consulta> consultas = consultaRepository.findByProfissionalId(id);

    if (consultas.isEmpty()) return null;

    long total = consultas.size();
    long realizadas = consultas.stream().filter(c -> c.getStatusConsulta() == StatusConsulta.REALIZADA).count();
    long agendadas = consultas.stream().filter(c -> c.getStatusConsulta() == StatusConsulta.AGENDADA).count();
    long canceladas = consultas.stream().filter(c -> c.getStatusConsulta() == StatusConsulta.CANCELADA).count();

    BigDecimal valorTotal = consultas.stream()
        .filter(c -> c.getStatusConsulta() == StatusConsulta.REALIZADA)
        .map(c -> c.getValorConsulta())
        .reduce(BigDecimal.ZERO, BigDecimal::add);

    BigInteger totalHoras = consultas.stream()
        .filter(c -> c.getStatusConsulta() == StatusConsulta.REALIZADA)
        .map(Consulta::getQuantidadeHoras)
        .reduce(BigInteger.ZERO, BigInteger::add);

    Map<String, Object> resultado = new HashMap<>();
    resultado.put("profissionalId", id);
    resultado.put("totalConsultas", total);
    resultado.put("consultasRealizadas", realizadas);
    resultado.put("consultasAgendadas", agendadas);
    resultado.put("consultasCanceladas", canceladas);
    resultado.put("totalHorasTrabalhadas", totalHoras);
    resultado.put("valorTotalRecebido", valorTotal);

    return resultado;
}
    public List<ConsultaResponse> buscarConsultasPorProfissional(Long id, String status, LocalDate dataDe, LocalDate dataAte) {
    return consultaRepository.findByProfissionalIdAndStatusConsultaAndDataConsultaBetween(id, status, dataDe.atStartOfDay(), dataAte.atTime(23, 59))
            .stream()
            .map(this::toResponse)
            .toList();
}
    private ConsultaResponse toResponse(Consulta c) {
        return new ConsultaResponse(
            c.getId(),
            c.getPaciente().getId(),
            c.getProfissional().getId(),
            c.getDataConsulta(),
            c.getStatusConsulta().name(),
            c.getQuantidadeHoras(),
            c.getValorConsulta(),
            c.getCreatedAt(),
            c.getUpdatedAt()
        );
    }

    public List<ProfissionalResponse> listarTodos(String sort) {
        List<Profissional> lista = repository.findAll();
        if ("desc".equalsIgnoreCase(sort)) {
            lista.sort(Comparator.comparing(Profissional::getNome).reversed());
        } else {
            lista.sort(Comparator.comparing(Profissional::getNome));
        }
        return lista.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public ProfissionalResponse buscarPorId(Long id) {
        return repository.findById(id).map(this::toResponse).orElse(null);
    }

    public ProfissionalResponse atualizar(Long id, ProfissionalRequestUpdate dto) {
        return repository.findById(id).map(p -> {
            p.setNome(dto.getNome());
            p.setEspecialidade(dto.getEspecialidade());
            p.setValorHora(dto.getValorHora());
            p.setUpdatedAt(LocalDateTime.now());
            return toResponse(repository.save(p));
        }).orElse(null);
    }

    public boolean deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private ProfissionalResponse toResponse(Profissional p) {
        return new ProfissionalResponse(
                p.getId(),
                p.getNome(),
                p.getEspecialidade(),
                p.getValorHora(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
    }
}
