package br.com.prova.produtos.steps;

import br.com.prova.produtos.exception.ProdutoNaoEncontradoException;
import br.com.prova.produtos.model.Produto;
import br.com.prova.produtos.repository.ProdutoRepositoryEmMemoria;
import br.com.prova.produtos.service.ProdutoService;
import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Definições de passos (step definitions) que ligam as frases dos arquivos
 * .feature ao código Java do sistema de produtos.
 *
 * A persistência é simulada com o ProdutoRepositoryEmMemoria,
 * sem banco de dados externo. O Cucumber cria uma nova instância desta
 * classe para cada cenário, então um cenário não interfere no outro.
 */
public class ProdutoSteps {

    private ProdutoService service;
    private Produto produtoExibido;
    private RuntimeException erro;

    // ---------- Dado ----------

    @Dado("que o sistema está disponível")
    public void queOSistemaEstaDisponivel() {
        service = new ProdutoService(new ProdutoRepositoryEmMemoria());
    }

    @Dado("que existe o produto de código {int}, nome {string}, preço {double} e estoque {int}")
    public void queExisteOProduto(int codigo, String nome, double preco, int estoque) {
        service.cadastrar(new Produto(codigo, nome, preco, estoque));
    }

    // ---------- Quando ----------

    @Quando("cadastrar o produto de código {int}, nome {string}, preço {double} e estoque {int}")
    public void cadastrarOProduto(int codigo, String nome, double preco, int estoque) {
        try {
            service.cadastrar(new Produto(codigo, nome, preco, estoque));
        } catch (RuntimeException e) {
            erro = e;
        }
    }

    @Quando("consultar o produto de código {int}")
    public void consultarOProduto(int codigo) {
        try {
            produtoExibido = service.consultar(codigo);
        } catch (RuntimeException e) {
            erro = e;
        }
    }

    @Quando("alterar o produto de código {int} para nome {string}, preço {double} e estoque {int}")
    public void alterarOProduto(int codigo, String nome, double preco, int estoque) {
        try {
            service.alterar(new Produto(codigo, nome, preco, estoque));
        } catch (RuntimeException e) {
            erro = e;
        }
    }

    @Quando("excluir o produto de código {int}")
    public void excluirOProduto(int codigo) {
        try {
            service.excluir(codigo);
        } catch (RuntimeException e) {
            erro = e;
        }
    }

    // ---------- Então ----------

    @Entao("a operação deve ser concluída com sucesso")
    public void aOperacaoDeveSerConcluidaComSucesso() {
        assertNull(erro, () -> "Não era esperado erro, mas ocorreu: " + erro.getMessage());
    }

    @Entao("o sistema deve informar a mensagem {string}")
    public void oSistemaDeveInformarAMensagem(String mensagemEsperada) {
        assertNotNull(erro, "Era esperado que o sistema informasse um erro");
        assertEquals(mensagemEsperada, erro.getMessage());
    }

    @Entao("o produto de código {int} deve ter nome {string}, preço {double} e estoque {int}")
    public void oProdutoDeveTer(int codigo, String nome, double preco, int estoque) {
        Produto produto = service.consultar(codigo);
        assertEquals(nome, produto.getNome());
        assertEquals(preco, produto.getPreco());
        assertEquals(estoque, produto.getQuantidadeEstoque());
    }

    @Entao("o produto exibido deve ter nome {string}, preço {double} e estoque {int}")
    public void oProdutoExibidoDeveTer(String nome, double preco, int estoque) {
        assertNotNull(produtoExibido, "Nenhum produto foi exibido");
        assertEquals(nome, produtoExibido.getNome());
        assertEquals(preco, produtoExibido.getPreco());
        assertEquals(estoque, produtoExibido.getQuantidadeEstoque());
    }

    @Entao("o produto de código {int} não deve estar cadastrado")
    public void oProdutoNaoDeveEstarCadastrado(int codigo) {
        assertThrows(ProdutoNaoEncontradoException.class, () -> service.consultar(codigo));
    }
}
