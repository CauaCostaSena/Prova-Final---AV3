# language: pt
Funcionalidade: Consulta de produtos
  Como usuário do sistema de gerenciamento de produtos
  Quero consultar produtos pelo código
  Para ver os dados de um produto cadastrado

  Contexto:
    Dado que o sistema está disponível
    E que existe o produto de código 1, nome "Teclado", preço 80 e estoque 10

  Cenário: Consultar produto existente pelo código
    Quando consultar o produto de código 1
    Então a operação deve ser concluída com sucesso
    E o produto exibido deve ter nome "Teclado", preço 80 e estoque 10

  Cenário: Consultar produto inexistente
    Quando consultar o produto de código 99
    Então o sistema deve informar a mensagem "Produto com código 99 não encontrado"
