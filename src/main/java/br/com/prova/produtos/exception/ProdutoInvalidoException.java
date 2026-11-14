package br.com.prova.produtos.exception;

/**
 * Lançada quando os dados de um produto não são válidos
 * (nome vazio, preço negativo, quantidade negativa ou código já cadastrado).
 */
public class ProdutoInvalidoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ProdutoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
