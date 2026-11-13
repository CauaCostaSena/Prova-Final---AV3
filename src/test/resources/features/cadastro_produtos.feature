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

  Cenário: Rejeitar cadastro de produto com nome vazio
    Quando cadastrar o produto de código 2, nome "", preço 50 e estoque 5
    Então o sistema deve informar a mensagem "O nome do produto não pode ser vazio"
    E o produto de código 2 não deve estar cadastrado

  Cenário: Rejeitar cadastro de produto com preço negativo
    Quando cadastrar o produto de código 3, nome "Mouse", preço -10 e estoque 5
    Então o sistema deve informar a mensagem "O preço do produto não pode ser negativo"
    E o produto de código 3 não deve estar cadastrado

  Cenário: Rejeitar cadastro de produto com quantidade negativa
    Quando cadastrar o produto de código 4, nome "Monitor", preço 900 e estoque -1
    Então o sistema deve informar a mensagem "A quantidade em estoque não pode ser negativa"
    E o produto de código 4 não deve estar cadastrado
