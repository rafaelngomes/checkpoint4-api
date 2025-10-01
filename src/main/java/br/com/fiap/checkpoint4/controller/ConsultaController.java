package br.com.fiap.checkpoint4.controller;

import br.com.fiap.checkpoint4.dto.ConsultaRequestCreate;
import br.com.fiap.checkpoint4.dto.ConsultaRequestUpdate;
import br.com.fiap.checkpoint4.dto.ConsultaResponse;
import br.com.fiap.checkpoint4.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService service;

    @PostMapping
    public ResponseEntity<ConsultaResponse> criarConsulta(@RequestBody ConsultaRequestCreate dto) {
        return ResponseEntity.status(201).body(service.criarConsulta(dto));
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> listarConsultas(
            @RequestParam(required = false) String status,
            @RequestParam(name = "data_de", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataDe,
            @RequestParam(name = "data_ate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate dataAte) {
        return ResponseEntity.ok(service.buscarPorFiltro(status, dataDe, dataAte));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponse> buscarConsultaPorId(@PathVariable Long id) {
        ConsultaResponse consulta = service.buscarPorId(id);
        return consulta != null ? ResponseEntity.ok(consulta) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponse> atualizarConsulta(@PathVariable Long id, @RequestBody ConsultaRequestUpdate dto) {
        ConsultaResponse atualizada = service.atualizarConsulta(id, dto);
        return atualizada != null ? ResponseEntity.ok(atualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConsulta(@PathVariable Long id) {
        boolean deletado = service.deletarConsulta(id);
        return deletado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
