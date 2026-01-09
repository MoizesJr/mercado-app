package com.mercado.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercado.api.dto.ProdutoDTO;
import com.mercado.api.model.Produto;
import com.mercado.api.service.ProdutoService;

import jakarta.validation.Valid;

/**
 * Ponto de entrada da API para recursos de produtos.
 * Define a URL base /api/produtos para todas as operações.
 */
@RestController
@RequestMapping("/produtos")
@CrossOrigin(origins = "http://localhost:4200")
public class ProdutoController {

  private final ProdutoService service;

  public ProdutoController(ProdutoService produtoService) {
    this.service = produtoService;
  }

  @PostMapping
  public ResponseEntity<Produto> salvar(@RequestBody @Valid ProdutoDTO dados) {
    Produto produtoSalvo = service.salvar(dados);
    return ResponseEntity.status(HttpStatus.CREATED).body(produtoSalvo);
  }

}
