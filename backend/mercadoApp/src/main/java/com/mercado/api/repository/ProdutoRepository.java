package com.mercado.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mercado.api.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
