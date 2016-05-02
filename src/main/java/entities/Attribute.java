package entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Attribute {
	@Id
	@GeneratedValue
	private Long id;  
	private String type;
	private Boolean input;
	private String value;
	private String name;
	@ManyToOne
	private Function function;
	
	
	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}
	
	public String getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Attribute() {
		super();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getInput() {
		return input;
	}
	public void setInput(Boolean input) {
		this.input = input;
	}
	
	
}
