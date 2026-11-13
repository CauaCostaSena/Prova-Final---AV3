package br.com.prova.produtos.repository;

import br.com.prova.produtos.model.Produto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Implementação do repositório que guarda os produtos em memória (em um Map).
 * Simula a camada de persistência sem depender de banco de dados externo.
 * É utilizada na execução dos cenários do Cucumber.
 */
public class ProdutoRepositoryEmMemoria implements ProdutoRepository {

    private final Map<Integer, Produto> produtos = new HashMap<>();

    @Override
    public void salvar(Produto produto) {
        produtos.put(produto.getCodigo(), produto);
    }

    @Override
    public Optional<Produto> buscarPorCodigo(int codigo) {
        return Optional.ofNullable(produtos.get(codigo));
    }

    @Override
    public void excluirPorCodigo(int codigo) {
        produtos.remove(codigo);
    }
}
