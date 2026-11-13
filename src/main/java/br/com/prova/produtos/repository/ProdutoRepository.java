package br.com.prova.produtos.repository;

import br.com.prova.produtos.model.Produto;

import java.util.Optional;

/**
 * Camada de persistência dos produtos.
 * Por ser uma interface, pode ser simulada com Mockito nos testes unitários
 * ou implementada em memória, sem banco de dados externo.
 */
public interface ProdutoRepository {

    /** Salva um produto novo ou substitui os dados de um produto já existente. */
    void salvar(Produto produto);

    /** Busca um produto pelo código. Retorna vazio quando o produto não existe. */
    Optional<Produto> buscarPorCodigo(int codigo);

    /** Remove o produto com o código informado. */
    void excluirPorCodigo(int codigo);
}
