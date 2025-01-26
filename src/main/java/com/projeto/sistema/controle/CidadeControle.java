package com.projeto.sistema.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.projeto.sistema.modelos.Cidade;
import com.projeto.sistema.repositorios.CidadeRepositorio;
import com.projeto.sistema.repositorios.EstadoRepositorio;

@Controller
public class CidadeControle {
	
	//Para fazer conexao com um repositorio para salvar dados 
	@Autowired
	private CidadeRepositorio cidadeRepositorio;
	
	@Autowired
	private EstadoRepositorio estadoRepositorio;
	
	// Obter um mapeamneto 
	@GetMapping("/cadastroCidade")
	public ModelAndView cadastrar(Cidade cidade) {
		ModelAndView mv = new ModelAndView("/administrativo/cidades/cadastro");
		mv.addObject("cidade", cidade);
		mv.addObject("listaEstado", estadoRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/listaCidade")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/cidades/lista");
		mv.addObject("listaCidades", cidadeRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/editarCidade/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) { //@PathVariable vai capturar o valor de id e adicionar va variavel id
		Optional<Cidade> cidade = cidadeRepositorio.findById(id); // Vai fazer a consulta, se exitir...
		return cadastrar(cidade.get()); // joga a parte do cadastro e fornece as informacoes para ser editadas. 
	}
	
	@GetMapping("/removerCidade/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Cidade> cidade = cidadeRepositorio.findById(id);
		cidadeRepositorio.delete(cidade.get());
		return lista(); 
	}
	
	@PostMapping("/salvarCidade")
	public ModelAndView salvar(Cidade cidade, BindingResult result) {
		System.out.println("Cidade recebido: " + cidade);
	    System.out.println("Nome: " + cidade.getNome() + ", Sigla: " + cidade.getCep());
	    if (result.hasErrors()) {
	        return cadastrar(cidade);
	    }
	    cidadeRepositorio.saveAndFlush(cidade);
	    return cadastrar(new Cidade());
	}

}
