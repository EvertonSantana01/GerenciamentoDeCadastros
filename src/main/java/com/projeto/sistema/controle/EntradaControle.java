package com.projeto.sistema.controle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.projeto.sistema.modelos.Entrada;
import com.projeto.sistema.modelos.ItemEntrada;
import com.projeto.sistema.modelos.Produto;
import com.projeto.sistema.repositorios.EntradaRepositorio;
import com.projeto.sistema.repositorios.FornecedorRepositorio;
import com.projeto.sistema.repositorios.ClienteRepositorio;
import com.projeto.sistema.repositorios.FuncionarioRepositorio;
import com.projeto.sistema.repositorios.ItemEntradaRepositorio;
import com.projeto.sistema.repositorios.ProdutoRepositorio;


@Controller
public class EntradaControle {
	
	@Autowired
	private EntradaRepositorio entradaRepositorio;
	@Autowired
	private ItemEntradaRepositorio itemEntradaRepositorio;
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	@Autowired
	private ClienteRepositorio clienteRepositorio;
	@Autowired
	private FornecedorRepositorio fornecedorRepositorio;
	
	private List<ItemEntrada> listaItemEntrada = new ArrayList<>();

	@GetMapping("/cadastroEntrada")
	public ModelAndView cadastrar(Entrada entrada, ItemEntrada itemEntrada) {
		ModelAndView mv = new ModelAndView("/administrativo/Entradas/cadastro");
		mv.addObject("entrada", entrada);
		mv.addObject("itemEntrada", itemEntrada);
		mv.addObject("listaItemEntrada", this.listaItemEntrada);
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		mv.addObject("listaClientees", clienteRepositorio.findAll());
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		mv.addObject("listarFornecedores", fornecedorRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/listarEntrada")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("/administrativo/entradas/lista");
		mv.addObject("listaEntradas", entradaRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/editarEntrada/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Entrada> entrada = entradaRepositorio.findById(id);
		this.listaItemEntrada = itemEntradaRepositorio.buscarPorEntrada(id);
		return cadastrar(entrada.get(), new ItemEntrada());
	}
	
	@GetMapping("/removerEntrada/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
	    // Excluir todos os itens relacionados à entrada
	    List<ItemEntrada> itensRelacionados = itemEntradaRepositorio.buscarPorEntrada(id);
	    itemEntradaRepositorio.deleteAll(itensRelacionados);
	    // Excluir a entrada
	    entradaRepositorio.deleteById(id); 
	    return listar();
	}
	
	@PostMapping("/salvarEntrada")
	public ModelAndView salvar(String acao, Entrada entrada, ItemEntrada itemEntrada, BindingResult result) {
		if (result.hasErrors()) {
		    return cadastrar(entrada, itemEntrada);
		}
		//itemEntrada e oque ta entrando (laele)
		//entrada e oque ja entrou (laele)
		if (acao.equals("itens")) {
			itemEntrada.setEntrada(entrada);
		    this.listaItemEntrada.add(itemEntrada);
		    entrada.setValorTotal(entrada.getValorTotal() + (itemEntrada.getValor() * itemEntrada.getQuantidade()));
		    entrada.setQuantidadeTotal(entrada.getQuantidadeTotal() + itemEntrada.getQuantidade());
		} else if (acao.equals("salvar")) {
		    entradaRepositorio.saveAndFlush(entrada);
		    
		    System.out.print("listar " + listar());
		    for (ItemEntrada it : listaItemEntrada) {
		        it.setEntrada(entrada);
		        itemEntradaRepositorio.saveAndFlush(it);

		        Optional<Produto> prod = produtoRepositorio.findById(it.getProduto().getId());
		        Produto produto = prod.orElseThrow(() -> new RuntimeException("Produto não encontrado"));
		        produto.setEstoque(produto.getEstoque() + it.getQuantidade());
		        produto.setPrecoVenda(it.getValor());
		        produto.setPrecoCusto(it.getValorCusto());
		        produtoRepositorio.saveAndFlush(produto);
			    this.listaItemEntrada = new ArrayList<>();
		    }
		    return cadastrar(new Entrada(), new ItemEntrada());
		}
		return cadastrar(entrada, new ItemEntrada());
	}

	public List<ItemEntrada> getListaItemEntrada() {
		return listaItemEntrada;
	}

	public void setListaItemEntrada(List<ItemEntrada> listaItemEntrada) {
		this.listaItemEntrada = listaItemEntrada;
	}
}