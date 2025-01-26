//Consulta alguma lista do banco de dados, obter alguma informação e salvar algum dado 

package com.projeto.sistema.repositorios;
import org.springframework.data.jpa.repository.JpaRepository;
import com.projeto.sistema.modelos.Estado;

public interface EstadoRepositorio extends JpaRepository<Estado, Long>{

}
