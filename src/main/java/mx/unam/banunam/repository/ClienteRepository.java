package mx.unam.banunam.repository;

import mx.unam.banunam.model.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClienteRepository extends CrudRepository<Cliente,Integer>, PagingAndSortingRepository<Cliente, Integer> {

}
