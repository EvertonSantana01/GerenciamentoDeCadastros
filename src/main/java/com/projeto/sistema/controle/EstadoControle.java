package com.projeto.sistema.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.projeto.sistema.modelos.Estado;
import com.projeto.sistema.repositorios.EstadoRepositorio;

@Controller
public class EstadoControle {
	
	//Para fazer conexao com um repositorio para salvar dados 
	@Autowired
	private EstadoRepositorio estadoRepositorio;
	
	// Obter um mapeamneto 
	@GetMapping("/cadastroEstado")
	public ModelAndView cadastrar(Estado estado) {
		ModelAndView mv = new ModelAndView("/administrativo/estados/cadastro");
		mv.addObject("estado", estado);
		return mv;
	}
	
	@GetMapping("/listaEstado")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/estados/lista");
		mv.addObject("listaEstados", estadoRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/editarEstado/{id}")
	public ModelAndView editar(@PathVariable("id")  Long id) { //@PathVariable vai capturar o valor de id e adicionar va variavel id
		Optional<Estado> estado = estadoRepositorio.findById(id); // Vai fazer a consulta, se exitir...
		return cadastrar(estado.get()); // joga a parte do cadastro e fornece as informacoes para ser editadas. 
	}
	
	@GetMapping("/removerEstado/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Estado> estado = estadoRepositorio.findById(id);
		estadoRepositorio.delete(estado.get());
		return lista(); 
	}
	
	@PostMapping("/salvarEstado")
	public ModelAndView salvar(Estado estado, BindingResult result) {
		System.out.println("Estado recebido: " + estado);
	    System.out.println("Nome: " + estado.getNome() + ", Sigla: " + estado.getSigla());
	    if (result.hasErrors()) {
	        return cadastrar(estado);
	    }
	    estadoRepositorio.saveAndFlush(estado);
	    return cadastrar(new Estado());
	}

}
