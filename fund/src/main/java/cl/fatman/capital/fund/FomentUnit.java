package cl.fatman.capital.fund;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "foment_unit")
public class FomentUnit {
	
	@Id
	private String id;
	private double value;
	@Column(unique = true)
	private LocalDate date;
	
	public FomentUnit() {
		super();
	}
	
	public FomentUnit(double value, LocalDate date) {
		super();
		this.id = date.toString();
		this.value = value;
		this.date = date;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public double getValue() {
		return value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
}