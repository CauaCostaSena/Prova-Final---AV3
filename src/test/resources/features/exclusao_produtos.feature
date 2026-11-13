# language: pt
Funcionalidade: Exclusão de produtos
  Como usuário do sistema de gerenciamento de produtos
  Quero excluir produtos pelo código
  Para remover do sistema os produtos que não são mais vendidos

  Contexto:
    Dado que o sistema está disponível
    E que existe o produto de código 1, nome "Teclado", preço 80 e estoque 10

  Cenário: Excluir produto existente pelo código
    Quando excluir o produto de código 1
    Então a operação deve ser concluída com sucesso
    E o produto de código 1 não deve estar cadastrado

  Cenário: Excluir produto inexistente
    Quando excluir o produto de código 99
    Então o sistema deve informar a mensagem "Produto com código 99 não encontrado"
