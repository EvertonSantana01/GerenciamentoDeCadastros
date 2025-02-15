package com.projeto.sistema.repositorios;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.projeto.sistema.modelos.ItemVenda;

@Repository
public interface ItemVendaRepositorio extends JpaRepository<ItemVenda, Long> {

    @Query("SELECT e FROM ItemVenda e WHERE e.venda.id = ?1")
    List<ItemVenda>buscarPorVenda(long id);
}
