# language: pt
Funcionalidade: Cadastro de produtos
  Como usuário do sistema de gerenciamento de produtos
  Quero cadastrar produtos com código, nome, preço e quantidade em estoque
  Para manter o catálogo de produtos atualizado

  Contexto:
    Dado que o sistema está disponível

  Cenário: Cadastrar produto com dados válidos
    Quando cadastrar o produto de código 1, nome "Teclado", preço 80 e estoque 10
    Então a operação deve ser concluída com sucesso
    E o produto de código 1 deve ter nome "Teclado", preço 80 e estoque 10

  Esquema do Cenário: Rejeitar cadastro de produto com dados inválidos
    Quando cadastrar o produto de código <codigo>, nome "<nome>", preço <preco> e estoque <estoque>
    Então o sistema deve informar a mensagem "<mensagem>"
    E o produto de código <codigo> não deve estar cadastrado

    Exemplos:
      | codigo | nome    | preco | estoque | mensagem                                      |
      | 2      |         | 50    | 5       | O nome do produto não pode ser vazio          |
      | 3      | Mouse   | -10   | 5       | O preço do produto não pode ser negativo      |
      | 4      | Monitor | 900   | -1      | A quantidade em estoque não pode ser negativa |

  Cenário: Rejeitar cadastro de produto com código já existente
    Dado que existe o produto de código 1, nome "Teclado", preço 80 e estoque 10
    Quando cadastrar o produto de código 1, nome "Mouse", preço 50 e estoque 5
    Então o sistema deve informar a mensagem "Já existe um produto com o código 1"
    Mas o produto de código 1 deve ter nome "Teclado", preço 80 e estoque 10
