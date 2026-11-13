package br.com.prova.produtos.exception;

/**
 * Lançada quando o produto solicitado não é encontrado.
 */
public class ProdutoNaoEncontradoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProdutoNaoEncontradoException(int codigo) {
        super("Produto com código " + codigo + " não encontrado");
    }
}
