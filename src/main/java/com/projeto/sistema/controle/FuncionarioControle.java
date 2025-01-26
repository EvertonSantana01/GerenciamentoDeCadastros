package com.projeto.sistema.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.projeto.sistema.modelos.Funcionario;
import com.projeto.sistema.repositorios.FuncionarioRepositorio;
import com.projeto.sistema.repositorios.CidadeRepositorio;

@Controller
public class FuncionarioControle {
	
	//Para fazer conexao com um repositorio para salvar dados 
	@Autowired
	private FuncionarioRepositorio funcionarioRepositorio;
	
	@Autowired
	private CidadeRepositorio cidadeRepositorio;
	
	// Obter um mapeamneto 
	@GetMapping("/cadastroFuncionario")
	public ModelAndView cadastrar(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("/administrativo/funcionarios/cadastro");
		mv.addObject("funcionario", funcionario);
		mv.addObject("listaCidade", cidadeRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/listaFuncionario")
	public ModelAndView lista() {
		ModelAndView mv = new ModelAndView("/administrativo/funcionarios/lista");
		mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
		return mv;
	}
	
	@GetMapping("/editarFuncionario/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) { //@PathVariable vai capturar o valor de id e adicionar va variavel id
		Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id); // Vai fazer a consulta, se exitir...
		return cadastrar(funcionario.get()); // joga a parte do cadastro e fornece as informacoes para ser editadas. 
	}
	
	@GetMapping("/removerFuncionario/{id}")
	public String removerFuncionario(@PathVariable("id") Long id) {
	    funcionarioRepositorio.deleteById(id); // Exclui diretamente
	    return "redirect:/funcionarios";
	}
	
	@PostMapping("/salvarFuncionario")
	public ModelAndView salvar(Funcionario funcionario, BindingResult result) {
	    if (result.hasErrors()) {
	        return cadastrar(funcionario);
	    }
	    funcionarioRepositorio.saveAndFlush(funcionario);
	    return cadastrar(new Funcionario());
	}

}
