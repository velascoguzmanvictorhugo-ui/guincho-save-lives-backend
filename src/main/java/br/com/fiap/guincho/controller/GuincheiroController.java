package br.com.fiap.guincho.controller;

import br.com.fiap.guincho.dominio.Guincheiro;
import br.com.fiap.guincho.service.GuincheiroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

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
    public ResponseEntity<ArrayList<Guincheiro>> obterTodosGuincheiros(
            @RequestParam(required = false) String regiao) {
        // Se o usuário digitou algo na busca, filtra. Se vier vazio, traz todos.
        if (regiao != null && !regiao.isEmpty()) {
            return ResponseEntity.ok(guincheiroService.buscarPorRegiaoOuCidade(regiao));
        }
        return ResponseEntity.ok(guincheiroService.obterTodosGuincheiros());
    }
}