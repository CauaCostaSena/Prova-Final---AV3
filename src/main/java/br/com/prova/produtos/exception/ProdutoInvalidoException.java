package br.com.prova.produtos.exception;

/**
 * Lançada quando os dados de um produto não são válidos
 * (nome vazio, preço negativo ou quantidade negativa).
 */
public class ProdutoInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProdutoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
