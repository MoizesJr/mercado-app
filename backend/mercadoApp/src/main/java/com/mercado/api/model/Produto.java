package com.mercado.api.model;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade que representa a tabela de produtos no banco de dados.
 * O Hibernate usa estas anotações para criar a tabela automaticamente.
 */
@Entity
@Table(name = "produtos")
@Data
@EqualsAndHashCode(of = "id") // Garante que a comparação entre produtos seja feita apenas pelo ID
public class Produto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String descricao;

  @Column(nullable = false)
  private BigDecimal precoCusto;

  @Column(nullable = false)
  private BigDecimal precoVenda;

  @Column(nullable = false)
  private Integer quantidadeEstoque;

}
