package br.pucminas.pdd.pagamentodespachoservice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.pucminas.pdd.pagamentodespachoservice.entity.CarrinhoCompras;
import br.pucminas.pdd.pagamentodespachoservice.entity.Livro;
import br.pucminas.pdd.pagamentodespachoservice.entity.Pagamento;
import br.pucminas.pdd.pagamentodespachoservice.entity.SolicitacaoDespacho;

@RestController
@SpringBootApplication
@RequestMapping(value = "/api")
public class PagamentoDespachoLivrosServiceApplication {

	private static String BASE_AUTHENTICATION_URL = "http://localhost:9001/";
	
	RestTemplate restTemplate = new RestTemplate();

	private static int idPagamento = 0;
	private static Map<Integer, Pagamento> poolPagamentos = new HashMap<>();
	private static int idDespacho = 0;
	private static Map<Integer, SolicitacaoDespacho> poolSolicitacaoDespacho = new HashMap<>();
	
	@RequestMapping(value = "/pagamentos/solicitacao", method = RequestMethod.POST)
	@ResponseBody
	public Pagamento solicitarPagamento(@RequestParam String idCarrinhoCompras) {
		
		//if (!isAuthenticated()) {
		//	throw new UnsupportedOperationException("Usuário não autenticado");
		//}

		CarrinhoCompras carrinho = restTemplate.getForObject("http://localhost:5001/api/carrinhos/{id}", CarrinhoCompras.class, idCarrinhoCompras);
	    
	    
	    if (carrinho == null) {
	    	throw new IllegalArgumentException("Carrinho de compras não encontrado.");
	    }
	    
	    Pagamento pagamento = new Pagamento();
	    
	    pagamento.setLivros(carrinho.getLivros());
	    pagamento.setPago(false);
	    pagamento.setPreco(calcularPrecoProdutos(carrinho));
	    pagamento = insertPagamento(pagamento);
	    
	    return pagamento;
	}
	
	private Pagamento insertPagamento(Pagamento pagamento) {
		pagamento.setId(idPagamento++);
		poolPagamentos.put(pagamento.getId(), pagamento);
		return pagamento;
	}

	private Double calcularPrecoProdutos(CarrinhoCompras carrinho) {
		Double total = 0d;
		if (carrinho.getLivros() != null ) {
			for (Livro livro : carrinho.getLivros()) {
				total += livro.getPreco();
			}
		}
		return total;
	}

	@RequestMapping(value = "/despacho/solicitacao", method = RequestMethod.POST)
	@ResponseBody
	public SolicitacaoDespacho solicitarDespacho(@RequestBody SolicitacaoDespacho solicitacaoDespacho) {
		
		//if (!isAuthenticated()) {
		//	throw new UnsupportedOperationException("Usuário não autenticado");
		//}
		
		if (solicitacaoDespacho != null && solicitacaoDespacho.getLivro() != null) {
			
		    Livro livro = restTemplate.getForObject("http://localhost:5000/api/livros/{id}", Livro.class, solicitacaoDespacho.getLivro().getId());
		    
		    if (livro != null) {
		    	solicitacaoDespacho.setId(idDespacho++);
		    	solicitacaoDespacho.setLivro(livro);
		    	poolSolicitacaoDespacho.put(solicitacaoDespacho.getId(), solicitacaoDespacho);
		    	return solicitacaoDespacho;
			}
		}
	    return null;
	}
	
	private boolean isAuthenticated() {
		String result = restTemplate.getForObject(BASE_AUTHENTICATION_URL + "api/isauthenticated", String.class);
		return Boolean.parseBoolean(result);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(PagamentoDespachoLivrosServiceApplication.class, args);
	}
}
