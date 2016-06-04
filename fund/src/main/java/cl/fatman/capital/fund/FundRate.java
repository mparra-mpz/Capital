package cl.fatman.capital.fund;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Entity
@Table( name = "FundRate" )
public class FundRate {
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name="Id")
	private int id;
	
	@Column(name = "Value")
	private double value;
	
	@Column(name = "Date", unique = true)
	private LocalDate date;
	
	
	public FundRate(double value, LocalDate date) {
		super();
		this.value = value;
		this.date = date;
	}
	
	
	public FundRate() {
		super();
	}
	
	
	public int getId() {
		return id;
	}
	
	
	public void setId(int id) {
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