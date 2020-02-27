package Submarino;  // Pacote, ele é um agrupador de classes ELE FORMA AS PASTAS 

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.text.SimpleDateFormat;
// TODOS ESSES "IMPORT" SEVEM PARA TRANSFERIR OS COMANDOS,
//ELAS COMPARTILHAM CAPACIDADE E HABILIDADE DE EXECUTAR VARIAS TAREFAS
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

// A CLASSE PERMITE QUE VOCÊ CONTROLAR E PROGRAMAR,
// UM CONJUNTO DE ATRIBUTIOS A FUNCÇOES DE ALGUMA COISA QUE VOCE QUEIRA CONTROLAR;
//EX: EU POSSO TER UMA CLASSE "CLIENTE", "PRODUTO" OU UMA CLASSE "PEDIDO",
// UMA CLASSE É ALGO QUE EU QUERO CONTROLAR E INTERAGIR,
// CLASSES E MÉTODOS SERVEM TANTO PARA FAZER O SOFTWARE FUNCIONA;
// QUANTO PRA TESTA SE O SOFTWARE FUNCIONA
public class BuscaSubmarino {
	String url;					// ESSA 1° PARTE SÃO OS ATRIBUTOS, ALGUM TIPO DE VALOR OU PARÂMETRO
	WebDriver driver; 
	// StringnomePasta PARA CRIAR PASTAS COM METADADOS EX: 2020/02/18  -  12:15:25 SEGUNDOS,
	// OS METAS DADOS FICAM NA PASTA "TARGET" DENTRO DA PASTA EVIDENCIA COM AS " DATAS E HORARIOS "
	String nomePasta = new SimpleDateFormat("yyyy-MM-dd HH-mm").format(Calendar.getInstance().getTime());
	
	// Funções e metodo de apoio
	
	// Tirar Print da Tela
	// 2° PARTE VEM OS MÉTODOS SERVE MAIS PARA PODER SEPARAR 
	public void Print(String nomePrint) throws IOException {
		File foto = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(foto, new File("C:\\Users\\danielly\\eclipse-workspace\\Submarino\\target\\evidencias\\" + nomePasta + "\\" + nomePrint + ".png"));
	}
	
	@Before // @ QUER DIZER ANOTAÇÃO
	public void Iniciar() {
		url = "https://www.submarino.com.br";
		System.setProperty("webdriver.chrome.driver", 
				"C:\\Users\\danielly\\eclipse-workspace\\Submarino\\drivers\\chrome\\80\\chromedriver.exe");
		driver = new ChromeDriver();
		// Dar tempo para requisições/ carregamento
		driver.manage().timeouts().implicitlyWait(10000, TimeUnit.MILLISECONDS);
		// Maximizar a janela para tirar print
		driver.manage().window().maximize();
	}
	
	@After
	public void Finalizar() {
		driver.quit();
	}
	
	@Given("^que acesso site do Submarino$")
	public void que_acesso_site_do_Submarino() throws Throwable {
	    driver.get(url);
	    Print("Passo 1- Acessou o site do Submarino");
	    System.out.print("Passo 1 - Acessou o site do submarino");
	   
	}

	@When("^preencho o termo \"([^\"]*)\" e clico na Lupa$")
	public void preencho_o_termo_e_clico_na_Lupa(String termo) throws Throwable {
	    driver.findElement(By.id("h_search-input")).clear();
	    driver.findElement(By.id("h_search-input")).sendKeys(termo);
	    Print("Passo 2- Preencheu o termo de Busca");
	    driver.findElement(By.id("h_search-input")).sendKeys(Keys.ENTER);
	   
	    //driver.findElement(By.id("btnOK")).click();  
	    
	}

	@Then("^exibe a lista de produtos$")
	public void exibe_a_lista_de_produtos() throws Throwable {
		Thread.sleep(6000);
	    assertEquals("Smartphone com Ofertas Incríveis no Submarino.com", driver.getTitle());
		Print("Passo 3.positivo - Exibiu a lista de produtos");
	}
	
	@Then("^exibe a mensagem de produto nao encontrado$")
	public void exibe_a_mensagem_de_produto_nao_encontrado() throws Throwable {
		Thread.sleep(6000);
		assertTrue(driver.findElement(By.cssSelector("span.TextUI-sc-12tokcy-0.CIZtP")).getText().contains("Não encontramos nenhum resultado para "));
	    //assertEquals("smartphone", driver.findElement(By.cssSelector("ul.neemu-breadcrumb-container")).getText());
		Print("Passo 3.negativo - Exibiu a mensagem de erro"); 
	}

}
