package br.com.fiap.checkpoint4.service;

import br.com.fiap.checkpoint4.dto.*;
import br.com.fiap.checkpoint4.model.Paciente;
import br.com.fiap.checkpoint4.model.StatusConsulta;
import br.com.fiap.checkpoint4.model.Consulta;
import br.com.fiap.checkpoint4.repository.ConsultaRepository;
import br.com.fiap.checkpoint4.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository repository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public PacienteResponse criar(PacienteRequestCreate dto) {
        Paciente p = new Paciente();
        p.setNome(dto.getNome());
        p.setEndereco(dto.getEndereco());
        p.setBairro(dto.getBairro());
        p.setEmail(dto.getEmail());
        p.setTelefoneCompleto(dto.getTelefoneCompleto());
        p.setDataNascimento(dto.getDataNascimento());
        p.setCreatedAt(LocalDateTime.now());
        p.setUpdatedAt(LocalDateTime.now());
        return toResponse(repository.save(p));
    }

    public List<PacienteResponse> listarTodos(String sort) {
        return repository.findAll().stream()
                .sorted((a, b) -> "desc".equalsIgnoreCase(sort)
                        ? b.getNome().compareToIgnoreCase(a.getNome())
                        : a.getNome().compareToIgnoreCase(b.getNome()))
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public PacienteResponse buscarPorId(Long id) {
        return repository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }

    public PacienteResponse atualizar(Long id, PacienteRequestUpdate dto) {
        return repository.findById(id).map(p -> {
            p.setNome(dto.getNome());
            p.setEndereco(dto.getEndereco());
            p.setBairro(dto.getBairro());
            p.setEmail(dto.getEmail());
            p.setTelefoneCompleto(dto.getTelefoneCompleto());
            p.setDataNascimento(dto.getDataNascimento());
            p.setUpdatedAt(LocalDateTime.now());
            return toResponse(repository.save(p));
        }).orElse(null);
    }

    public boolean deletar(Long id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }

    public List<ConsultaResponse> buscarConsultasPorPaciente(Long pacienteId, String status, LocalDate dataDe, LocalDate dataAte) {
        StatusConsulta statusEnum = StatusConsulta.valueOf(status.toUpperCase());
        List<Consulta> consultas = consultaRepository.findByPacienteIdAndStatusConsultaAndDataConsultaBetween(
                pacienteId,
                statusEnum,
                dataDe.atStartOfDay(),
                dataAte.atTime(23, 59, 59)
        );

        return consultas.stream().map(this::toResponse).collect(Collectors.toList());
    }

    private PacienteResponse toResponse(Paciente p) {
        return new PacienteResponse(
                p.getId(),
                p.getNome(),
                p.getEndereco(),
                p.getBairro(),
                p.getEmail(),
                p.getTelefoneCompleto(),
                p.getDataNascimento(),
                p.getCreatedAt(),
                p.getUpdatedAt()
        );
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
}
