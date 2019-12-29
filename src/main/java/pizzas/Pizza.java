package pizzas;

import java.util.List;

import lombok.Data;

@Data
public class Pizza {

	private String name;
	private List<String> ingredients;

}
