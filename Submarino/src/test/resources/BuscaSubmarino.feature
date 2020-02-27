Feature: Compra no Site Submarino

  @compra @estorno
	Scenario: Busca por Produto
		Given que acesso site do Submarino
		When preencho o termo "smartphone" e clico na Lupa 
		Then exibe a lista de produtos 
		
	@estorno
	 Scenario: Busca por Produto Nao Encontrado
  	 Given que acesso site do Submarino 
     When preencho o termo "QADASDSERDSSASDERETERDSDSSAS" e clico na Lupa 
	   Then exibe a mensagem de produto nao encontrado 