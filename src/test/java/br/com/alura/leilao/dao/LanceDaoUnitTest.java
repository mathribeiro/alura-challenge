package br.com.alura.leilao.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;
import br.com.alura.leilao.util.builder.LanceBuilder;
import br.com.alura.leilao.util.builder.LeilaoBuilder;
import br.com.alura.leilao.util.builder.UsuarioBuilder;

class LanceDaoUnitTest {

	private LanceDao lanceDao;
	private EntityManager em;

	@BeforeEach
	public void beforeEach() {
		this.em = JPAUtil.getEntityManager();
		this.lanceDao = new LanceDao(em);
		em.getTransaction().begin();
	}

	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}

	@Test
	void deveEncontrarOMaiorLance() {
		
			final String maiorLance = "1500";
			final String nomeDoCaraQueDeuMaiorLance = "ribeiro";
		
	     	Usuario usuario = new UsuarioBuilder()
	                .comNome("matheus")
	                .comEmail("matheus@email.com")
	                .comSenha("12345678")
	                .criar();
	     	
	     	Usuario usuario2 = new UsuarioBuilder()
	                .comNome("ribeiro")
	                .comEmail("ribeiro@email.com")
	                .comSenha("12345678")
	                .criar();

	        em.persist(usuario);
	        em.persist(usuario2);
	        

	        Leilao leilao = new LeilaoBuilder()
	                .comNome("Mochila")
	                .comValorInicial("500")
	                .comData(LocalDate.now())
	                .comUsuario(usuario)
	                .criar();

	        em.persist(leilao);

	        Lance lance = new LanceBuilder()
	                .comUsuario(usuario)
	                .comValor("900")
	                .comLeilao(leilao)
	                .criar();
	        
	        Lance lance2 = new LanceBuilder()
	                .comUsuario(usuario2)
	                .comValor("1500")
	                .comLeilao(leilao)
	                .criar();

	        em.persist(lance);
	        em.persist(lance2);

	        final Lance maiorLanceEncontrado = this.lanceDao.buscarMaiorLanceDoLeilao(leilao);
	        
	        assertNotNull(maiorLanceEncontrado);
	        assertEquals(maiorLanceEncontrado.getValor(), new BigDecimal(maiorLance));
	        assertEquals(maiorLanceEncontrado.getUsuario().getNome(), nomeDoCaraQueDeuMaiorLance);
	        
	}
	
	
}
