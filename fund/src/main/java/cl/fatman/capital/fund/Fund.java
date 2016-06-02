package cl.fatman.capital.fund;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table( name = "Fund" )
public class Fund {
	
	private int id;
	private String name;
	private String run;
	private String series;
	private String institution;
	private String type;
	private String category;
	private double price;
	
	
	public Fund(String name, String run, String series, String institution, String type, String category,
			double price) {
		super();
		this.name = name;
		this.run = run;
		this.series = series;
		this.institution = institution;
		this.type = type;
		this.category = category;
		this.price = price;
	}
	
	
	public Fund() {
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
	
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Column(name = "RUN")
	public String getRun() {
		return run;
	}
	
	
	public void setRun(String run) {
		this.run = run;
	}
	
	
	@Column(name = "SERIES")
	public String getSeries() {
		return series;
	}
	
	
	public void setSeries(String series) {
		this.series = series;
	}
	
	
	@Column(name = "INSTITUTION")
	public String getInstitution() {
		return institution;
	}
	
	
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	
	
	@Column(name = "TYPE")
	public String getType() {
		return type;
	}
	
	
	public void setType(String type) {
		this.type = type;
	}
	
	
	@Column(name = "CATEGORY")
	public String getCategory() {
		return category;
	}
	
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	@Column(name = "PRICE")
	public double getPrice() {
		return price;
	}
	
	
	public void setPrice(double price) {
		this.price = price;
	}
}