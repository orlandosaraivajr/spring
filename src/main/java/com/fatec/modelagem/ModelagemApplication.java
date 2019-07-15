package com.fatec.modelagem;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.fatec.modelagem.domain.Categoria;
import com.fatec.modelagem.domain.Cidade;
import com.fatec.modelagem.domain.Cliente;
import com.fatec.modelagem.domain.Endereco;
import com.fatec.modelagem.domain.Estado;
import com.fatec.modelagem.domain.ItemPedido;
import com.fatec.modelagem.domain.Pagamento;
import com.fatec.modelagem.domain.PagamentoComBoleto;
import com.fatec.modelagem.domain.PagamentoComCartao;
import com.fatec.modelagem.domain.Pedido;
import com.fatec.modelagem.domain.Produto;
import com.fatec.modelagem.domain.enums.EstadoPagamento;
import com.fatec.modelagem.domain.enums.TipoCliente;
import com.fatec.modelagem.repositories.CategoriaRepository;
import com.fatec.modelagem.repositories.CidadeRepository;
import com.fatec.modelagem.repositories.ClienteRepository;
import com.fatec.modelagem.repositories.EnderecoRepository;
import com.fatec.modelagem.repositories.EstadoRepository;
import com.fatec.modelagem.repositories.ItemPedidoRepository;
import com.fatec.modelagem.repositories.PagamentoRepository;
import com.fatec.modelagem.repositories.PedidoRepository;
import com.fatec.modelagem.repositories.ProdutoRepository;

@SpringBootApplication
public class ModelagemApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriarepository;
	@Autowired
	private ProdutoRepository produtorepository;
	@Autowired
	private CidadeRepository cidaderepository;
	@Autowired
	private EstadoRepository estadorepository;
	@Autowired
	private ClienteRepository clienterepository;
	@Autowired
	private EnderecoRepository enderecorepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentorepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(ModelagemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p2.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriarepository.saveAll(Arrays.asList(cat1, cat2));
		produtorepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadorepository.saveAll(Arrays.asList(est1, est2));
		cidaderepository.saveAll(Arrays.asList(c1, c2, c3));
		
		Cliente cli1 = new Cliente(null,"Maria da Silva","maria@gmail.com","12345678911",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("123456789", "987654321"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "13800000", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "sala 800", "Centro", "13800012", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienterepository.saveAll(Arrays.asList(cli1));
		enderecorepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentorepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.0, 1, 2000.0);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.0, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.0, 1, 800.0);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip1));
		p3.getItens().addAll(Arrays.asList(ip3));
	
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}
}
