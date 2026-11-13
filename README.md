# Prova Final AV3: Gherkin e Mockito

Projeto prático da avaliação **Gherkin e Mockito** da disciplina **Teste de Sistemas**
(Curso Técnico em Desenvolvimento de Sistemas).

Sistema Java de **gerenciamento de produtos** testado com:

- **Gherkin + Cucumber**: cenários de comportamento (BDD) em arquivos `.feature`
- **JUnit 5 + Mockito**: testes unitários com o repositório simulado (mock)

A camada de persistência é simulada, **sem banco de dados externo**:
- nos cenários do Cucumber, com um repositório em memória (`ProdutoRepositoryEmMemoria`);
- nos testes unitários, com um mock do Mockito (`mock(ProdutoRepository.class)`).

## Requisitos para executar

- **Java 17** ou superior (JDK)
- **Maven 3.6.3** ou superior

Para conferir as versões instaladas:

```bash
java -version
mvn -version
```

## Como executar

Na pasta raiz do projeto (onde está o `pom.xml`), rode:

```bash
mvn test
```

Esse comando compila o projeto e executa **todos os testes**: os 10 testes unitários com
Mockito e os 10 cenários Gherkin com Cucumber. Ao final, o Maven mostra `BUILD SUCCESS`.

Para rodar só uma parte:

```bash
# Apenas os testes unitários com Mockito
mvn test -Dtest=ProdutoServiceTest

# Apenas os cenários do Cucumber
mvn test -Dtest=RunCucumberTest
```

Também é possível rodar pela IDE (IntelliJ, Eclipse ou VS Code), importando como projeto
Maven e executando as classes `ProdutoServiceTest` e `RunCucumberTest`.

## Evidências dos testes

Depois do `mvn test`:

- **Console**: os cenários aparecem passo a passo (plugin `pretty` do Cucumber) e o resumo
  de cada classe de teste aparece no final (`Tests run: ..., Failures: 0, Errors: 0`).
- **Relatório HTML do Cucumber**: `target/cucumber-reports/cucumber.html`
  (abrir no navegador).
- **Relatórios do Maven Surefire**: `target/surefire-reports/`.

## Estrutura do projeto

```
pom.xml
README.md
src
├── main/java/br/com/prova/produtos
│   ├── model/Produto.java                          # código, nome, preço e estoque
│   ├── repository/ProdutoRepository.java           # interface de persistência
│   ├── repository/ProdutoRepositoryEmMemoria.java  # persistência simulada em memória
│   ├── service/ProdutoService.java                 # regras: cadastrar, consultar, alterar, excluir
│   └── exception/
│       ├── ProdutoInvalidoException.java           # nome vazio, preço ou quantidade negativos
│       └── ProdutoNaoEncontradoException.java      # produto não encontrado
└── test
    ├── java/br/com/prova/produtos
    │   ├── service/ProdutoServiceTest.java         # testes unitários com JUnit + Mockito
    │   ├── steps/ProdutoSteps.java                 # definições de passos do Cucumber
    │   └── runner/RunCucumberTest.java             # executa os cenários pelo JUnit/Maven
    └── resources/features
        ├── cadastro_produtos.feature
        ├── consulta_produtos.feature
        ├── alteracao_produtos.feature
        └── exclusao_produtos.feature
```

## Requisitos funcionais atendidos

| Requisito | Onde é implementado | Cenários Gherkin | Testes unitários (Mockito) |
|---|---|---|---|
| Cadastrar produto (código, nome, preço, estoque) | `ProdutoService.cadastrar` | Cadastrar produto com dados válidos | `deveCadastrarProdutoValido` |
| Consultar produto pelo código | `ProdutoService.consultar` | Consultar produto existente pelo código | `deveConsultarProdutoExistente` |
| Alterar produto existente | `ProdutoService.alterar` | Alterar os dados de um produto existente | `deveAtualizarProdutoExistente` |
| Excluir produto pelo código | `ProdutoService.excluir` | Excluir produto existente pelo código | `deveExcluirProdutoExistente` |
| Rejeitar nome vazio | `ProdutoService.validar` | Rejeitar cadastro de produto com nome vazio | `naoDeveCadastrarProdutoComNomeVazio` |
| Rejeitar preço negativo | `ProdutoService.validar` | Rejeitar cadastro de produto com preço negativo | `naoDeveCadastrarProdutoComPrecoNegativo` |
| Rejeitar quantidade negativa | `ProdutoService.validar` | Rejeitar cadastro de produto com quantidade negativa | `naoDeveCadastrarProdutoComQuantidadeNegativa` |
| Informar produto não encontrado | `ProdutoNaoEncontradoException` | Consultar, alterar e excluir produto inexistente | `deveInformarQuandoProdutoConsultadoNaoExiste`, `naoDeveAlterarProdutoInexistente`, `naoDeveExcluirProdutoInexistente` |

## Itens exigidos na avaliação

**Gherkin e Cucumber**
- 10 cenários em 4 arquivos `.feature` (mínimo exigido: 6), com casos de sucesso e de falha
- Cenários escritos em português (`# language: pt`)
- Definições de passos em Java em `ProdutoSteps.java`
- Execução pelo Maven por meio de `RunCucumberTest.java`

**Mockito e JUnit**
- 10 testes unitários em `ProdutoServiceTest.java` (mínimo exigido: 5), incluindo os 5 obrigatórios:
  cadastro válido, consulta existente, consulta inexistente, atualização e exclusão
- Repositório simulado com `mock()`; retornos definidos com `when()` e `thenReturn()`
- `verify()` usado para conferir as chamadas ao repositório, inclusive com `never()` nos casos de erro
- Casos de dados inválidos: nome vazio, preço negativo e quantidade negativa

## Tecnologias e versões

| Tecnologia | Versão |
|---|---|
| Java | 17 |
| JUnit (Jupiter / Platform) | 5.11.4 |
| Mockito | 5.14.2 |
| Cucumber (cucumber-java + junit-platform-engine) | 7.20.1 |
| Maven Surefire Plugin | 3.5.2 |
