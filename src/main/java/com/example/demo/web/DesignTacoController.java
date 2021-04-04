package com.example.demo.web;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Ingredient.Type;
import com.example.demo.Order;
import com.example.demo.data.IngredientRepository;
import com.example.demo.data.TacoRepository;
import com.example.demo.Ingredient;
import com.example.demo.Tacos;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;


             //private static final org.slf4j.Logger log =
@Slf4j      //org.slf4j.LoggerFactory.getLogger(DesignTacoController.class);
@Controller
@SessionAttributes("order") //you need the order to be present across multiple requests so that you can create multiple tacos and add them to the order. The class-level @SessionAttributes annotation specifies any model objects like the order attribute that should be kept in session and available across multiple requests.
@RequestMapping("/design")
public class DesignTacoController {
	@Autowired
	private IngredientRepository ingredientRepo;
	@Autowired
	private TacoRepository tacoRepository;
	
//	@ModelAttribute(name="taco") //ensures that an Tacos object will be created in the model.
//	public Tacos taco() {
//		return new Tacos();
//	}
	
	@ModelAttribute(name="order")
	public Order order() {
		return new Order();
	}
//	@Autowired
//	public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepository) {
//		this.ingredientRepo = ingredientRepo;
//		this.tacoRepository = tacoRepository;
//	}

	@GetMapping // @RequestMapping(method=RequestMethod.GET) Alternative
	public String showDeisgnForm(Model model) { // All the data to be sent to the view is added to model but Thymleaf being framework, finds data in servlet response attribute where the data carried by model is copied to
		
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));
		
//		List<Ingredient> ingredients = Arrays.asList(
//				 new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//				 new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//				 new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//				 new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//				 new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//				 new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//				 new Ingredient("CHED", "Cheddar", Type.CHEESE),
//				 new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//				 new Ingredient("SLSA", "Salsa", Type.SAUCE),
//				 new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//				 );
		
		Type[] types = Ingredient.Type.values();
		for(Type type: types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}		
		model.addAttribute("design", new Tacos()); // as seen on the form attribute
		
		return "design";
	}
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {

	    return ingredients.stream()
	            .filter(x -> x.getType().equals(type))
	            .collect(Collectors.toList());

	}
	
	@PostMapping
	public String processDesign(@Valid Tacos design, Errors error, @ModelAttribute Order order) { // data from form of /design path is passed in Tacos object to processDesign()
		if(error.hasErrors()) {log.info(error.getFieldError().toString()); return "redirect:/design";} //The @Valid annotation tells Spring MVC to perform validation on the submitted Tacos object after it’s bound to the submitted form data and before the processDesign() method is called. If there are any validation errors, the details of those errors will be captured in an Errors object that’s passed into processDesign().
		log.info("Processing design: "+design); 
		Tacos saved = tacoRepository.save(design);
		order.addDesign(saved);
		return "redirect:/order/current";
	}
}
