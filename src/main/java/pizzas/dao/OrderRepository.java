package pizzas.dao;

import org.springframework.data.repository.CrudRepository;

import pizzas.data.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {

}