package br.com.fiap.checkpoint4.service;

import br.com.fiap.checkpoint4.dto.ConsultaRequestCreate;
import br.com.fiap.checkpoint4.dto.ConsultaRequestUpdate;
import br.com.fiap.checkpoint4.dto.ConsultaResponse;
import br.com.fiap.checkpoint4.model.Consulta;
import br.com.fiap.checkpoint4.model.Paciente;
import br.com.fiap.checkpoint4.model.Profissional;
import br.com.fiap.checkpoint4.model.StatusConsulta;
import br.com.fiap.checkpoint4.repository.ConsultaRepository;
import br.com.fiap.checkpoint4.repository.PacienteRepository;
import br.com.fiap.checkpoint4.repository.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*import java.math.BigInteger;*/
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public ConsultaResponse criarConsulta(ConsultaRequestCreate dto) {
        Consulta consulta = new Consulta();
        consulta.setDataConsulta(dto.getDataConsulta());
        consulta.setStatusConsulta(StatusConsulta.valueOf(dto.getStatusConsulta()));
        consulta.setQuantidadeHoras(dto.getQuantidadeHoras());
        consulta.setValorConsulta(dto.getValorConsulta());
        consulta.setCreatedAt(LocalDateTime.now());
        consulta.setUpdatedAt(LocalDateTime.now());

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId()).orElse(null);
        Profissional profissional = profissionalRepository.findById(dto.getProfissionalId()).orElse(null);
        consulta.setPaciente(paciente);
        consulta.setProfissional(profissional);

        return toResponse(consultaRepository.save(consulta));
    }
    public List<ConsultaResponse> buscarPorFiltro(String status, LocalDate dataDe, LocalDate dataAte) {
    List<Consulta> consultas;

    boolean filtrarStatus = status != null && !status.isBlank();
    boolean filtrarDatas = dataDe != null && dataAte != null;

    if (filtrarStatus && filtrarDatas) {
        consultas = consultaRepository.findByStatusConsultaAndDataConsultaBetween(
            StatusConsulta.valueOf(status.toUpperCase()),
            dataDe.atStartOfDay(),
            dataAte.atTime(23, 59, 59)
        );
    } else if (filtrarStatus) {
        consultas = consultaRepository.findByStatusConsulta(
            StatusConsulta.valueOf(status.toUpperCase())
        );
    } else if (filtrarDatas) {
        consultas = consultaRepository.findByDataConsultaBetween(
            dataDe.atStartOfDay(),
            dataAte.atTime(23, 59, 59)
        );
    } else {
        consultas = consultaRepository.findAll();
    }

    return consultas.stream().map(this::toResponse).toList();
    }


    public List<ConsultaResponse> listarTodos() {
        return consultaRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public ConsultaResponse buscarPorId(Long id) {
        return consultaRepository.findById(id)
                .map(this::toResponse)
                .orElse(null);
    }

    public ConsultaResponse atualizarConsulta(Long id, ConsultaRequestUpdate dto) {
        return consultaRepository.findById(id).map(c -> {
            c.setDataConsulta(dto.getDataConsulta().atStartOfDay());
            c.setStatusConsulta(dto.getStatusConsulta());
            c.setQuantidadeHoras(dto.getQuantidadeHoras());
            c.setValorConsulta(dto.getValorConsulta());
            c.setUpdatedAt(LocalDateTime.now());
            return toResponse(consultaRepository.save(c));
        }).orElse(null);
    }

    public boolean deletarConsulta(Long id) {
        if (consultaRepository.existsById(id)) {
            consultaRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public List<ConsultaResponse> buscarConsultasPorPaciente(Long pacienteId, String status, LocalDate dataDe, LocalDate dataAte) {
        StatusConsulta statusConvertido = StatusConsulta.valueOf(status.toUpperCase());
        return consultaRepository.findByPacienteIdAndStatusConsultaAndDataConsultaBetween(
                pacienteId,
                statusConvertido,
                dataDe.atStartOfDay(),
                dataAte.atTime(LocalTime.MAX)
        ).stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<ConsultaResponse> buscarConsultasPorProfissional(Long profissionalId, String status, LocalDate dataDe, LocalDate dataAte) {
        StatusConsulta statusConvertido = StatusConsulta.valueOf(status.toUpperCase());
        return consultaRepository.findByProfissionalIdAndStatusConsultaAndDataConsultaBetween(
                profissionalId,
                statusConvertido,
                dataDe.atStartOfDay(),
                dataAte.atTime(LocalTime.MAX)
        ).stream().map(this::toResponse).collect(Collectors.toList());
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
