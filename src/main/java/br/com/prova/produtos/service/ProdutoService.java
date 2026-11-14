package br.com.prova.produtos.service;

import br.com.prova.produtos.exception.ProdutoInvalidoException;
import br.com.prova.produtos.exception.ProdutoNaoEncontradoException;
import br.com.prova.produtos.model.Produto;
import br.com.prova.produtos.repository.ProdutoRepository;

/**
 * Regras de negócio do gerenciamento de produtos:
 * cadastrar, consultar, alterar e excluir, validando os dados informados.
 *
 * O repositório é recebido pelo construtor, o que permite substituí-lo
 * por um mock (Mockito) nos testes unitários.
 */
public class ProdutoService {

    public static final String MSG_NOME_VAZIO = "O nome do produto não pode ser vazio";
    public static final String MSG_PRECO_NEGATIVO = "O preço do produto não pode ser negativo";
    public static final String MSG_QUANTIDADE_NEGATIVA = "A quantidade em estoque não pode ser negativa";
    public static final String MSG_CODIGO_EXISTENTE = "Já existe um produto com o código ";

    private final ProdutoRepository repositorio;

    public ProdutoService(ProdutoRepository repositorio) {
        this.repositorio = repositorio;
    }

    /** Cadastra um novo produto, desde que os dados sejam válidos e o código ainda não exista. */
    public void cadastrar(Produto produto) {
        validar(produto);
        if (repositorio.buscarPorCodigo(produto.getCodigo()).isPresent()) {
            throw new ProdutoInvalidoException(MSG_CODIGO_EXISTENTE + produto.getCodigo());
        }
        repositorio.salvar(produto);
    }

    /** Consulta um produto pelo código. Informa quando o produto não é encontrado. */
    public Produto consultar(int codigo) {
        return repositorio.buscarPorCodigo(codigo)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(codigo));
    }

    /** Altera os dados de um produto existente, identificado pelo código. */
    public void alterar(Produto produtoAlterado) {
        validar(produtoAlterado);
        consultar(produtoAlterado.getCodigo());
        repositorio.salvar(produtoAlterado);
    }

    /** Exclui um produto existente pelo código. */
    public void excluir(int codigo) {
        consultar(codigo);
        repositorio.excluirPorCodigo(codigo);
    }

    /** Rejeita nome vazio, preço negativo e quantidade negativa. */
    private void validar(Produto produto) {
        if (produto.getNome() == null || produto.getNome().isBlank()) {
            throw new ProdutoInvalidoException(MSG_NOME_VAZIO);
        }
        if (produto.getPreco() < 0) {
            throw new ProdutoInvalidoException(MSG_PRECO_NEGATIVO);
        }
        if (produto.getQuantidadeEstoque() < 0) {
            throw new ProdutoInvalidoException(MSG_QUANTIDADE_NEGATIVA);
        }
    }
}
