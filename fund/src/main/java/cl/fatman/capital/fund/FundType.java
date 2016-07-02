package cl.fatman.capital.fund;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fund_type")
public class FundType {
	
	@Id
	private int id;
	private String name;
	
	public FundType(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public FundType() {
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
}