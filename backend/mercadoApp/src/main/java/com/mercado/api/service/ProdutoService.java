package com.mercado.api.service;

import org.springframework.stereotype.Service;

import com.mercado.api.dto.ProdutoDTO;
import com.mercado.api.model.Produto;
import com.mercado.api.repository.ProdutoRepository;

import lombok.RequiredArgsConstructor;

/**
 * Camada de serviço: contém a inteligência do sistema.
 * É aqui que validamos regras de mercado antes de chamar o banco de dados.
 */
@Service
@RequiredArgsConstructor
public class ProdutoService {

  private final ProdutoRepository respository;

  /**
   * Converte o DTO recebido da API em uma Entidade Produto e salva no banco.
   */
  public Produto salvar(ProdutoDTO dto) {
    Produto novoProduto = new Produto();
    novoProduto.setDescricao(dto.descricao());
    novoProduto.setPrecoCusto(dto.precoCusto());
    novoProduto.setPrecoVenda(dto.precoVenda());
    novoProduto.setQuantidadeEstoque(dto.quantidadeEstoque());
    return respository.save(novoProduto);
  }
}
