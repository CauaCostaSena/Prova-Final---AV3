# language: pt
Funcionalidade: Alteração de produtos
  Como usuário do sistema de gerenciamento de produtos
  Quero alterar os dados de um produto existente
  Para corrigir ou atualizar suas informações

  Contexto:
    Dado que o sistema está disponível
    E que existe o produto de código 1, nome "Teclado", preço 80 e estoque 10

  Cenário: Alterar os dados de um produto existente
    Quando alterar o produto de código 1 para nome "Teclado Mecânico", preço 250 e estoque 4
    Então a operação deve ser concluída com sucesso
    E o produto de código 1 deve ter nome "Teclado Mecânico", preço 250 e estoque 4

  Cenário: Alterar produto inexistente
    Quando alterar o produto de código 99 para nome "Headset", preço 150 e estoque 3
    Então o sistema deve informar a mensagem "Produto com código 99 não encontrado"
