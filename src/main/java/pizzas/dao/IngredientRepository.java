package pizzas.dao;

import org.springframework.data.repository.CrudRepository;

import pizzas.data.Ingredient;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
}
