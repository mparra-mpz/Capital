package cl.fatman.capital.fund;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@NamedQueries(
	@NamedQuery(
		name = "get_fund_type_by_id",
		query = "select ft from FundType ft where id = :id"
	)
)

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