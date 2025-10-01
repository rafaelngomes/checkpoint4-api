package br.com.fiap.checkpoint4.controller;

import br.com.fiap.checkpoint4.dto.PacienteRequestCreate;
import br.com.fiap.checkpoint4.dto.PacienteRequestUpdate;
import br.com.fiap.checkpoint4.dto.PacienteResponse;
import br.com.fiap.checkpoint4.dto.ConsultaResponse;
import br.com.fiap.checkpoint4.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @PostMapping
    public ResponseEntity<PacienteResponse> criar(@RequestBody PacienteRequestCreate dto) {
        PacienteResponse response = pacienteService.criar(dto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PacienteResponse>> listar(@RequestParam(defaultValue = "asc") String sort) {
        return ResponseEntity.ok(pacienteService.listarTodos(sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteResponse> buscarPorId(@PathVariable Long id) {
        PacienteResponse response = pacienteService.buscarPorId(id);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteResponse> atualizar(@PathVariable Long id, @RequestBody PacienteRequestUpdate dto) {
        PacienteResponse response = pacienteService.atualizar(id, dto);
        if (response == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = pacienteService.deletar(id);
        if (deletado) return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/consultas")
    public ResponseEntity<List<ConsultaResponse>> consultasPorPaciente(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(name = "data_de") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataDe,
            @RequestParam(name = "data_ate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataAte
    ) {
        List<ConsultaResponse> consultas = pacienteService.buscarConsultasPorPaciente(id, status, dataDe, dataAte);
        return ResponseEntity.ok(consultas);
    }
}
