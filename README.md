# batch-application
===================

 Aplicação de processamento de arquivos CSV
 
 ## Descrição
 
 A idéia do projeto é ler informações bancárias de um arquivo csv, que serão enviadas a uma API externa e com base no retorno da API é realizado
 a escrita de um novo arquivo csv.
 
 Para atender esse contexto  foi utilizado o sub-projeto 'spring batch integration' que disponibiliza o modelo assíncrono e que nesse cenário se torna 
 o mais recomendado. 
 
 ### Dependências
 
 * Java 1.8
 * Maven 3.5
 * Spring boot starter batch 2.3.4.RELEASE
 * Spring batch integration 2.3.4.RELEASE
 * Lombok 1.18.12
 * Junit 5
  
 ### Construção
 
 Para construir e executar a aplicação:

    mvn clean install
    
    Na pasta 'target' do projeto vai está o .jar, então o comando abaixo com path do arquivo será necessário informar:
    
    java -jar batch-application-001-SNAPSHOT.jar file=src/main/resources/reader-file.csv
    
    Para fins de teste o arquivo de escrita ficará na raiz do projeto com o nome 'account-update.csv'.
    
### Informações do arquivo csv

Leitura:

	|agencia|conta|saldo|status
	1425;25874-7;350,00;A
	
Escrita:

	|agencia|conta|saldo|status|update
	1425;25874-7;350,00;A;true
    
 
 
 
 