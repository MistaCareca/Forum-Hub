package com.example.demo.controller;

import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.model.curso.Curso;
import com.example.demo.model.topico.*;
import com.example.demo.model.usuario.Usuario;
import com.example.demo.repository.*;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class TopicoController {

    private final TopicoRepository repository;
    private final CursoRepository cursoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    @Autowired
    public TopicoController(
            TopicoRepository repository,
            CursoRepository cursoRepository,
            UsuarioRepository usuarioRepository,
            PerfilRepository perfilRepository
    ) {
        this.repository = repository;
        this.cursoRepository = cursoRepository;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> postar(@RequestBody @Valid DadosTopico dados) {
        if (repository.existsByTituloAndMensagem(dados.titulo(), dados.mensagem())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        Curso curso = cursoRepository.findById(dados.curso().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado"));

        Usuario autor = usuarioRepository.findById(dados.autor().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        Topico topico = new Topico(dados.titulo(), dados.mensagem(), curso, autor);
        repository.save(topico);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public Page<DadosListagemTopico> listar(
            @PageableDefault(size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable paginacao) {
        return repository.findAllWithAutorAndCurso(paginacao).map(DadosListagemTopico::new);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosListagemTopico> detalhar(@PathVariable Long id) {
        return repository.findByIdWithAutorAndCurso(id)
                .map(topico -> ResponseEntity.ok(new DadosListagemTopico(topico)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Object> atualizar(@PathVariable Long id, @RequestBody @Valid DadosAttTopico dados) {
        return repository.findById(id).map(topico -> {
            Curso curso = cursoRepository.findById(dados.cursoId())
                    .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado"));
            topico.atualizarDados(dados.titulo(), dados.mensagem(), curso);
            repository.save(topico);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
