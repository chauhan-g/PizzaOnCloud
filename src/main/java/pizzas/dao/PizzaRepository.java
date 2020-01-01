package pizzas.dao;

import org.springframework.data.repository.CrudRepository;

import pizzas.data.Pizza;

public interface PizzaRepository extends CrudRepository<Pizza, Long> {
}