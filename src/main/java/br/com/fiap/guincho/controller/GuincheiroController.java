package br.com.fiap.guincho.controller;

import br.com.fiap.guincho.dominio.Guincheiro;
import br.com.fiap.guincho.dominio.RepositorioGuincheiros;
import br.com.fiap.guincho.infra.dao.GuincheiroDAO;
import br.com.fiap.guincho.service.GuincheiroService;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guincheiros")
public class GuincheiroController {

    private RepositorioGuincheiros guincheiroDao;
    private GuincheiroService guincheiroService;

    public GuincheiroController() {
        guincheiroDao = new GuincheiroDAO();
        guincheiroService = new GuincheiroService(guincheiroDao);
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody Guincheiro guincheiro) {
        try {
            guincheiroService.adicionar(guincheiro);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(guincheiro);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<ArrayList<Guincheiro>> obterTodosGuincheiros() {
        return ResponseEntity.ok(guincheiroService.obterTodosGuincheiros());
    }
}
