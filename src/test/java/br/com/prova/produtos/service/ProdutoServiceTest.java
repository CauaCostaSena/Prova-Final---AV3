package br.com.prova.produtos.service;

import br.com.prova.produtos.exception.ProdutoInvalidoException;
import br.com.prova.produtos.exception.ProdutoNaoEncontradoException;
import br.com.prova.produtos.model.Produto;
import br.com.prova.produtos.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Testes unitários do ProdutoService.
 * O repositório é simulado com Mockito, então nenhum banco de dados é usado:
 * - mock()       cria o repositório falso;
 * - when()       define o que o repositório falso deve retornar;
 * - thenReturn() informa o valor retornado;
 * - verify()     confere se o service chamou o repositório como esperado.
 */
class ProdutoServiceTest {

    private ProdutoRepository repositorio;
    private ProdutoService service;

    @BeforeEach
    void configurar() {
        repositorio = mock(ProdutoRepository.class);
        service = new ProdutoService(repositorio);
    }

    // ---------- Testes obrigatórios ----------

    @Test
    @DisplayName("Deve cadastrar produto com dados válidos")
    void deveCadastrarProdutoValido() {
        Produto produto = new Produto(1, "Teclado", 80.0, 10);
        when(repositorio.buscarPorCodigo(1)).thenReturn(Optional.empty());

        service.cadastrar(produto);

        verify(repositorio).salvar(produto);
    }

    @Test
    @DisplayName("Deve consultar produto existente pelo código")
    void deveConsultarProdutoExistente() {
        Produto produto = new Produto(1, "Teclado", 80.0, 10);
        when(repositorio.buscarPorCodigo(1)).thenReturn(Optional.of(produto));

        Produto encontrado = service.consultar(1);

        assertSame(produto, encontrado);
        assertEquals("Teclado", encontrado.getNome());
        verify(repositorio, never()).salvar(any());
    }

    @Test
    @DisplayName("Deve informar quando o produto consultado não existe")
    void deveInformarQuandoProdutoConsultadoNaoExiste() {
        when(repositorio.buscarPorCodigo(99)).thenReturn(Optional.empty());

        ProdutoNaoEncontradoException erro = assertThrows(
                ProdutoNaoEncontradoException.class, () -> service.consultar(99));

        assertEquals("Produto com código 99 não encontrado", erro.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar os dados de um produto existente")
    void deveAtualizarProdutoExistente() {
        Produto original = new Produto(1, "Teclado", 80.0, 10);
        Produto alterado = new Produto(1, "Teclado Mecânico", 250.0, 4);
        when(repositorio.buscarPorCodigo(1)).thenReturn(Optional.of(original));

        service.alterar(alterado);

        verify(repositorio).salvar(alterado);
    }

    @Test
    @DisplayName("Deve excluir produto existente pelo código")
    void deveExcluirProdutoExistente() {
        Produto produto = new Produto(1, "Teclado", 80.0, 10);
        when(repositorio.buscarPorCodigo(1)).thenReturn(Optional.of(produto));

        service.excluir(1);

        verify(repositorio).excluirPorCodigo(1);
    }

    // ---------- Valores no limite ----------

    @Test
    @DisplayName("Deve cadastrar produto com preço zero e estoque zero")
    void deveCadastrarProdutoComPrecoEEstoqueZero() {
        Produto produto = new Produto(5, "Brinde", 0.0, 0);
        when(repositorio.buscarPorCodigo(5)).thenReturn(Optional.empty());

        service.cadastrar(produto);

        verify(repositorio).salvar(produto);
    }

    // ---------- Dados inválidos ----------

    @Test
    @DisplayName("Não deve cadastrar produto com nome vazio, só com espaços ou nulo")
    void naoDeveCadastrarProdutoComNomeVazio() {
        for (String nome : new String[] {"", "   ", null}) {
            Produto produto = new Produto(2, nome, 50.0, 5);

            ProdutoInvalidoException erro = assertThrows(
                    ProdutoInvalidoException.class, () -> service.cadastrar(produto));

            assertEquals("O nome do produto não pode ser vazio", erro.getMessage());
        }
        verify(repositorio, never()).salvar(any());
    }

    @Test
    @DisplayName("Não deve cadastrar produto com preço negativo")
    void naoDeveCadastrarProdutoComPrecoNegativo() {
        Produto produto = new Produto(3, "Mouse", -10.0, 5);

        ProdutoInvalidoException erro = assertThrows(
                ProdutoInvalidoException.class, () -> service.cadastrar(produto));

        assertEquals("O preço do produto não pode ser negativo", erro.getMessage());
        verify(repositorio, never()).salvar(any());
    }

    @Test
    @DisplayName("Não deve cadastrar produto com quantidade negativa")
    void naoDeveCadastrarProdutoComQuantidadeNegativa() {
        Produto produto = new Produto(4, "Monitor", 900.0, -1);

        ProdutoInvalidoException erro = assertThrows(
                ProdutoInvalidoException.class, () -> service.cadastrar(produto));

        assertEquals("A quantidade em estoque não pode ser negativa", erro.getMessage());
        verify(repositorio, never()).salvar(any());
    }

    @Test
    @DisplayName("Não deve cadastrar produto com código já existente")
    void naoDeveCadastrarProdutoComCodigoJaExistente() {
        Produto existente = new Produto(1, "Teclado", 80.0, 10);
        when(repositorio.buscarPorCodigo(1)).thenReturn(Optional.of(existente));

        ProdutoInvalidoException erro = assertThrows(
                ProdutoInvalidoException.class, () -> service.cadastrar(new Produto(1, "Mouse", 50.0, 5)));

        assertEquals("Já existe um produto com o código 1", erro.getMessage());
        verify(repositorio, never()).salvar(any());
    }

    @Test
    @DisplayName("Não deve alterar produto com dados inválidos")
    void naoDeveAlterarProdutoComDadosInvalidos() {
        Produto alterado = new Produto(1, "Teclado", -5.0, 10);

        ProdutoInvalidoException erro = assertThrows(
                ProdutoInvalidoException.class, () -> service.alterar(alterado));

        assertEquals("O preço do produto não pode ser negativo", erro.getMessage());
        verify(repositorio, never()).salvar(any());
    }

    // ---------- Produto não encontrado em alteração e exclusão ----------

    @Test
    @DisplayName("Não deve alterar produto inexistente")
    void naoDeveAlterarProdutoInexistente() {
        Produto alterado = new Produto(99, "Headset", 150.0, 3);
        when(repositorio.buscarPorCodigo(99)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> service.alterar(alterado));

        verify(repositorio, never()).salvar(any());
    }

    @Test
    @DisplayName("Não deve excluir produto inexistente")
    void naoDeveExcluirProdutoInexistente() {
        when(repositorio.buscarPorCodigo(99)).thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class, () -> service.excluir(99));

        verify(repositorio, never()).excluirPorCodigo(anyInt());
    }
}
