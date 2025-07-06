package com.example.demo.controller;

import com.example.demo.topico.DadosTopico;
import com.example.demo.topico.Topico;
import com.example.demo.topico.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private TopicoRepository repository;

    @PostMapping
    public void postar(@RequestBody DadosTopico dados){
        repository.save(new Topico(dados));
    }

}
