package com.mercado.api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Objeto de transferência de dados (DTO).
 * Usado para receber dados do Frontend sem expor a Entidade diretamente.
 * Inclui validações para garantir a integridade dos dados antes de salvar.
 */
public record ProdutoDTO(
                @NotBlank String descricao, // Impede nomes vazios ou apenas com espaços em branco
                @NotNull @PositiveOrZero BigDecimal precoCusto, // Valor mínimo 0
                @NotNull @PositiveOrZero BigDecimal precoVenda,
                @NotNull @PositiveOrZero Integer quantidadeEstoque) {
}
