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
	
	@Id
	@GeneratedValue(generator="increment")
	@GenericGenerator(name="increment", strategy = "increment")
	@Column(name="Id")
	private int id;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "Run")
	private String run;
	
	@Column(name = "Series")
	private String series;
	
	@Column(name = "Institution")
	private String institution;
	
	@Column(name = "Type")
	private String type;
	
	@Column(name = "Category")
	private String category;
	
	@Column(name = "Price")
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
	
	
	public int getId() {
		return id;
	}
	
	
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getName() {
		return name;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getRun() {
		return run;
	}
	
	
	public void setRun(String run) {
		this.run = run;
	}
	
	
	public String getSeries() {
		return series;
	}
	
	
	public void setSeries(String series) {
		this.series = series;
	}
	
	
	public String getInstitution() {
		return institution;
	}
	
	
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	
	
	public String getType() {
		return type;
	}
	
	
	public void setType(String type) {
		this.type = type;
	}
	
	
	public String getCategory() {
		return category;
	}
	
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	public double getPrice() {
		return price;
	}
	
	
	public void setPrice(double price) {
		this.price = price;
	}
}