package br.com.leilao.Teste;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.leilao.servico.Avaliador;
import br.com.leilao.dominio.CriadorDeLeilao;
import br.com.leilao.dominio.Lance;
import br.com.leilao.dominio.Leilao;
import br.com.leilao.dominio.Usuario;

public class TesdeDoAvaliador {
	
	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;
	private Object Leilao;
	private Object leilao;

	@Before
	public void criaAvaliador(){
		
		 this.leiloeiro = new Avaliador(); 
		 System.out.println("cria avaliador");
	}
	
	
	@Test(expected=RuntimeException.class)
	public void naoDeveAvaliarLeilioesSemNenhumLanceDado(){
	
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 Novo").constroi();
		
		leiloeiro.avalia(leilao);
		
	}
	
	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		//parte 1: cenário
		
			//criação do usuário passou para o @Before
		 //this.joao = new Usuario("João");
		 //this.jose = new Usuario("Jose");
		 //this.maria = new Usuario ("Maria");
		
		Leilao leilao = new Leilao("Playstation 3 Novo");
		
		leilao.propoe(new Lance(joao, 250));
		leilao.propoe(new Lance(jose, 300));
		leilao.propoe(new Lance(maria, 400));
		
		//parte 2: ação
		leiloeiro.avalia(leilao);
		leiloeiro.media(leilao);
		
		//parte 3: validadção
		double maiorEsperado = 400;
		double menorEsperado = 250;
		double media = 316.6666666666667;
				
		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
		assertEquals(media,leiloeiro.getMediaLance(), 0.00001);

		
	}
	
	@Test
	public void deveEntenderLeiaoComApenasUmLance(){
		//this.joao = new Usuario("Joao");
		
		Leilao leilao = new Leilao("Playstation 3 Novo");
		
		leilao.propoe(new Lance(joao, 1000));
		
		
		leiloeiro.avalia(leilao);
		
		assertEquals(1000, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(1000, leiloeiro.getMenorLance(), 0.00001);
		
	}
	
	@Test 
	public void deveEncontrarOsTresMaioresLances(){
		
		//this.joao = new Usuario("Joao");
		//this.maria = new Usuario("Maria");
		
		/*Leilao leilao = new Leilao("Playstation 3 Novo");
		leilao.propoe(new Lance(joao, 100));
		leilao.propoe(new Lance(maria, 200));
		leilao.propoe(new Lance(joao, 300));
		leilao.propoe(new Lance(maria, 400));
		*/
		
		
		//Usando test data builder
		Leilao leilao = new CriadorDeLeilao().para("Playstation 3 novo ")
				.lance(joao, 100.0)
				.lance(maria, 200.0)
				.lance(joao, 300.0)
				.lance(maria, 400.0)
				.constroi();
		
		leiloeiro.avalia(leilao);
		
		List<Lance> maiores = leiloeiro.getTresMaiores();
		assertEquals(3, maiores.size());
		assertEquals(400, maiores.get(0).getValor(), 0.00001);		
		assertEquals(300, maiores.get(1).getValor(), 0.00001);	
		assertEquals(200, maiores.get(2).getValor(), 0.00001);	
	}

	


	@Test
    public void deveEntenderLeilaoComLancesEmOrdemRandomica() {
        //this.joao = new Usuario("Joao"); 
        //this.maria = new Usuario("Maria"); 
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao,200.0));
        leilao.propoe(new Lance(maria,450.0));
        leilao.propoe(new Lance(joao,120.0));
        leilao.propoe(new Lance(maria,700.0));
        leilao.propoe(new Lance(joao,630.0));
        leilao.propoe(new Lance(maria,230.0));

       
        leiloeiro.avalia(leilao);

        assertEquals(700.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(120.0, leiloeiro.getMenorLance(), 0.0001);
    }
    
    @Test
    public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {
        //this.joao = new Usuario("Joao"); 
        //this.maria = new Usuario("Maria"); 
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao,400.0));
        leilao.propoe(new Lance(maria,300.0));
        leilao.propoe(new Lance(joao,200.0));
        leilao.propoe(new Lance(maria,100.0));

       
        leiloeiro.avalia(leilao);

        assertEquals(400.0, leiloeiro.getMaiorLance(), 0.0001);
        assertEquals(100.0, leiloeiro.getMenorLance(), 0.0001);
    }
    
    @Test
    public void deveDevolverTodosLancesCasoNaoHajaNoMinimo3() {
        //this.joao = new Usuario("João");
        //this.maria = new Usuario("Maria");
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 100.0));
        leilao.propoe(new Lance(maria, 200.0));

        criaAvaliador();        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(2, maiores.size());
        assertEquals(200, maiores.get(0).getValor(), 0.00001);
        assertEquals(100, maiores.get(1).getValor(), 0.00001);
    }

    @Test
    public void deveDevolverListaVaziaCasoNaoHajaLances() {
        Leilao leilao = new Leilao("Playstation 3 Novo");

        
        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(0, maiores.size());
    }
    
    @After
    public void finaliza() {
      System.out.println("fim");
    }
}
