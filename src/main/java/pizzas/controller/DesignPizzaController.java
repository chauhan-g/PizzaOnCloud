package pizzas.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import lombok.extern.slf4j.Slf4j;
import pizzas.dao.IngredientRepository;
import pizzas.dao.PizzaRepository;
import pizzas.data.Ingredient;
import pizzas.data.Ingredient.Type;
import pizzas.data.Order;
import pizzas.data.Pizza;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignPizzaController {

	private final IngredientRepository ingredientRepo;

	private final PizzaRepository pizzaRepo;

	@Autowired
	public DesignPizzaController(IngredientRepository ingredientRepo, PizzaRepository pizzaRepo) {
		this.ingredientRepo = ingredientRepo;
		this.pizzaRepo = pizzaRepo;
	}

	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}

	@ModelAttribute(name = "design")
	public Pizza design() {
		return new Pizza();
	}

	@GetMapping
	public String showDesignForm(Model model) {

		/*
		 * List<Ingredient> ingredients = Arrays.asList(new Ingredient("FLTO",
		 * "Flour Tortilla", Type.WRAP), new Ingredient("COTO", "Corn Tortilla",
		 * Type.WRAP), new Ingredient("GRBF", "Ground Beef", Type.PROTEIN), new
		 * Ingredient("CARN", "Carnitas", Type.PROTEIN), new Ingredient("TMTO",
		 * "Diced Tomatoes", Type.VEGGIES), new Ingredient("LETC", "Lettuce",
		 * Type.VEGGIES), new Ingredient("CHED", "Cheddar", Type.CHEESE), new
		 * Ingredient("JACK", "Monterrey Jack", Type.CHEESE), new Ingredient("SLSA",
		 * "Salsa", Type.SAUCE), new Ingredient("SRCR", "Sour Cream", Type.SAUCE));
		 */

		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));

		Type[] types = Ingredient.Type.values();

		for (Type type : types) {

			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}

		model.addAttribute("design", new Pizza());

		return "design";

	}

	@PostMapping
	public String processDesign(@Valid Pizza design, Errors errors, @ModelAttribute Order order) {
		log.info("Processing design: " + design);

		if (errors.hasErrors()) {
			return "design";
		}

		Pizza saved = pizzaRepo.save(design);
		order.addDesign(saved);

		return "redirect:/orders/current";

	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
	}

}
