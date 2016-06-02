package cl.fatman.capital.fund;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Table( name = "Rate" )
public class Rate {
	
	private int id;
	private double value;
	private LocalDate date;
	
	
	public Rate(double value, LocalDate date) {
		super();
		this.value = value;
		this.date = date;
	}
	
	
	public Rate() {
		super();
	}
	
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name="ID")
	public int getId() {
		return id;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	@Column(name = "VALUE")
	public double getValue() {
		return value;
	}
	
	
	public void setValue(double value) {
		this.value = value;
	}
	
	
	@Column(name = "DATE", unique = true)
	public LocalDate getDate() {
		return date;
	}
	
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
}