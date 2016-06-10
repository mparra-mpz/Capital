package cl.fatman.capital.fund;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Fund")
public class Fund {
	
	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private int id;
	private String name;
	private String run;
	private String series;
	private String institution;
	private String type;
	
	
	public Fund(String name, String run, String series, String institution, String type) {
		super();
		this.name = name;
		this.run = run;
		this.series = series;
		this.institution = institution;
		this.type = type;
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
}