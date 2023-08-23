package tacos.web;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import lombok.Data;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import tacos.data.IngredientRepository;
import tacos.Taco;
import tacos.TacoOrder;
import tacos.Ingredient;
import tacos.Ingredient.Type;

@SessionAttributes("tacoOrder")
@Controller
@RequestMapping("/design")
@Data
public class DesignTacoController {

	private final IngredientRepository ingredientRepo;

	@ModelAttribute(name = "tacoOrder")
	public TacoOrder order() {
		return new TacoOrder();
	}

	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}

	@GetMapping
	public String showDesignForm(Model model) {
		Iterable<Ingredient> ingredients = ingredientRepo.findAll();

		Type[] types = Ingredient.Type.values();
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(),
					filterByType(ingredients, type));
		}
		return "design";
	}

	@PostMapping
	public String processDesign(@Valid Taco taco, Errors errors, @ModelAttribute TacoOrder tacoOrder, Model model) {
		if (errors.hasErrors()) {
			return showDesignForm(model);
		}

		tacoOrder.addTaco(taco);
		return "redirect:/orders/current";
	}

	private Iterable<Ingredient> filterByType(Iterable<Ingredient> ingredients, Type type) {
		List<Ingredient> ingredientList = (List<Ingredient>) ingredients;
		return ingredientList.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}

}
