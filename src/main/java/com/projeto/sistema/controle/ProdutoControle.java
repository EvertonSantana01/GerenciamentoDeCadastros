package com.projeto.sistema.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.projeto.sistema.modelos.Produto;
import com.projeto.sistema.repositorios.ProdutoRepositorio;

@Controller
public class ProdutoControle {
	
	//Para fazer conexao com um repositorio para salvar dados 
	@Autowired
	private ProdutoRepositorio produtoRepositorio;
	
	// Obter um mapeamneto 
	@GetMapping("/cadastroProduto")
	public ModelAndView cadastrar(Produto produto) {
		ModelAndView mv = new ModelAndView("/administrativo/produtos/cadastro");
		mv.addObject("produto", produto);
		return mv;
	}
	
	@GetMapping("/listaProduto")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/produtos/lista");
		mv.addObject("listaProdutos", produtoRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/editarProduto/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) { //@PathVariable vai capturar o valor de id e adicionar va variavel id
		Optional<Produto> produto = produtoRepositorio.findById(id); // Vai fazer a consulta, se exitir...
		return cadastrar(produto.get()); // joga a parte do cadastro e fornece as informacoes para ser editadas. 
	}
	
	@GetMapping("/removerProduto/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		produtoRepositorio.delete(produto.get());
		return lista(); 
	}
	
	@PostMapping("/salvarProduto")
	public ModelAndView salvar(Produto produto, BindingResult result) {
	    if (result.hasErrors()) {
	        return cadastrar(produto);
	    }
	    produtoRepositorio.saveAndFlush(produto);
	    return cadastrar(new Produto());
	}
}