package br.com.fiap.guincho.controller;

import br.com.fiap.guincho.dominio.Guincheiro;
import br.com.fiap.guincho.service.GuincheiroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/guincheiros")
public class GuincheiroController {

    private final GuincheiroService guincheiroService;

    public GuincheiroController(GuincheiroService guincheiroService) {
        this.guincheiroService = guincheiroService;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody Guincheiro guincheiro) {
        try {
            guincheiroService.adicionar(guincheiro);
            return ResponseEntity.status(HttpStatus.CREATED).body(guincheiro);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Guincheiro>> obterTodosGuincheiros(
            @RequestParam(required = false) String regiao) {
        List<Guincheiro> guincheiros;
        if (regiao != null && !regiao.isEmpty()) {
            guincheiros = guincheiroService.buscarPorRegiaoOuCidade(regiao);
        } else {
            guincheiros = guincheiroService.obterTodosGuincheiros();
        }
        return ResponseEntity.ok(guincheiros);
    }
}