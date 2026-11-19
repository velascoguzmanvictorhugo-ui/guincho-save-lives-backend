package br.com.fiap.guincho.controller;

import br.com.fiap.guincho.dominio.Guincheiro;
import br.com.fiap.guincho.exception.RecursoNaoEncontradoException;
import br.com.fiap.guincho.exception.RegraDeNegocioException;
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
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno inesperado ao cadastrar guincheiro.");
        }
    }

    @GetMapping
    public ResponseEntity<Object> obterTodosGuincheiros(
            @RequestParam(required = false) String regiao) {
        try {
            List<Guincheiro> guincheiros;
            if (regiao != null && !regiao.isBlank()) {
                guincheiros = guincheiroService.buscarPorRegiaoOuCidade(regiao);
            } else {
                guincheiros = guincheiroService.obterTodosGuincheiros();
            }
            return ResponseEntity.ok(guincheiros);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno inesperado ao listar guincheiros.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPorId(@PathVariable long id) {
        try {
            Guincheiro guincheiro = guincheiroService.buscarPorId(id);
            return ResponseEntity.ok(guincheiro);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno inesperado ao buscar guincheiro.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable long id, @RequestBody Guincheiro guincheiro) {
        try {
            Guincheiro atualizado = guincheiroService.atualizar(id, guincheiro);
            return ResponseEntity.ok(atualizado);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno inesperado ao atualizar guincheiro.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable long id) {
        try {
            guincheiroService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno inesperado ao excluir guincheiro.");
        }
    }
}