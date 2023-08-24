package tacos;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import lombok.Data;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.CreditCardNumber;

@Data
@Entity
public class TacoOrder implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotBlank(message = "delivery name is required")
	private String deliveryName;

	@NotBlank(message = "Street is required")
	private String deliveryStreet;

	@NotBlank(message = "City is required")
	private String deliveryCity;

	@NotBlank(message = "State is required")
	private String deliveryState;

	@NotBlank(message = "Zip code is required")
	private String deliveryZip;

	@CreditCardNumber(message = "Not a valid credit card number")
	private String ccNumber;

	@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$", message = "Must be formatted MM/YY")
	private String ccExpiration;

	@Digits(integer = 3, fraction = 0, message = "Invalid CVV")
	private String ccCVV;

	private Date placedAt = new Date();

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "taco_order")
	private List<Taco> tacos = new ArrayList<>();

	public void addTaco(Taco taco) {
		this.tacos.add(taco);
	}

}
