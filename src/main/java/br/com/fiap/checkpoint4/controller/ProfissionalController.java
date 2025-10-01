package br.com.fiap.checkpoint4.controller;

import br.com.fiap.checkpoint4.dto.*;
import br.com.fiap.checkpoint4.service.ProfissionalService;
import br.com.fiap.checkpoint4.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/profissionais")
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    public ResponseEntity<ProfissionalResponse> cadastrar(@RequestBody ProfissionalRequestCreate dto) {
        return ResponseEntity.status(201).body(profissionalService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProfissionalResponse>> listar(@RequestParam(required = false, defaultValue = "asc") String sort) {
        return ResponseEntity.ok(profissionalService.listarTodos(sort));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfissionalResponse> buscarPorId(@PathVariable Long id) {
        ProfissionalResponse profissional = profissionalService.buscarPorId(id);
        if (profissional == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(profissional);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProfissionalResponse> atualizar(@PathVariable Long id, @RequestBody ProfissionalRequestUpdate dto) {
        ProfissionalResponse atualizado = profissionalService.atualizar(id, dto);
        if (atualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        boolean deletado = profissionalService.deletar(id);
        if (deletado) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/consultas")
    public ResponseEntity<List<ConsultaResponse>> consultarPorProfissional(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam(name = "data_de") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataDe,
            @RequestParam(name = "data_ate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataAte
    ) {
        List<ConsultaResponse> consultas = consultaService.buscarConsultasPorProfissional(id, status, dataDe, dataAte);
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/{id}/stats")
    public ResponseEntity<Map<String, Object>> estatisticas(@PathVariable Long id) {
        Map<String, Object> stats = profissionalService.obterEstatisticas(id);
        return ResponseEntity.ok(stats);
    }
}
