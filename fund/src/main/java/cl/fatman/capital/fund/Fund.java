package cl.fatman.capital.fund;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@NamedQueries(
	@NamedQuery(
		name = "get_fund_by_run_series",
		query = "select f from Fund f where run = :run and series = :series"
	)
)

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
	@ManyToOne
    @JoinColumn(name = "fund_type_id", foreignKey = @ForeignKey(name = "FUND_TYPE_ID_FK"))
	private FundType type;
	
	public Fund(String name, String run, String series, String institution, FundType type) {
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
	
	public FundType getType() {
		return type;
	}
	
	public void setType(FundType type) {
		this.type = type;
	}
}