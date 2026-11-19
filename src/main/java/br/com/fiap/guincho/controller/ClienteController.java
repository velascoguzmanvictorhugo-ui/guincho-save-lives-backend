package br.com.fiap.guincho.controller;

import br.com.fiap.guincho.dominio.Cliente;
import br.com.fiap.guincho.exception.RecursoNaoEncontradoException;
import br.com.fiap.guincho.exception.RegraDeNegocioException;
import br.com.fiap.guincho.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody Cliente cliente) {
        try {
            clienteService.adicionar(cliente);
            return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno inesperado ao cadastrar cliente.");
        }
    }

    @GetMapping
    public ResponseEntity<Object> obterTodos() {
        try {
            List<Cliente> clientes = clienteService.obterTodos();
            return ResponseEntity.ok(clientes);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno inesperado ao listar clientes.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> obterPorId(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.obterPorId(id);
            return ResponseEntity.ok(cliente);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno inesperado ao buscar cliente.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            clienteService.atualizar(id, cliente);
            return ResponseEntity.ok(cliente);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno inesperado ao atualizar cliente.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> excluir(@PathVariable Long id) {
        try {
            clienteService.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RegraDeNegocioException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro interno inesperado ao excluir cliente.");
        }
    }
}