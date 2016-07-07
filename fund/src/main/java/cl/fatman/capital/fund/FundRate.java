package cl.fatman.capital.fund;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.JoinColumn;
import javax.persistence.ForeignKey;
import javax.persistence.Table;

import java.time.LocalDate;

@NamedQueries(
	@NamedQuery(
		name = "get_fund_rate_update_date",
		query = "select fr.date from FundRate fr order by fr.date DESC"
	)
)

@Entity
@Table(name = "fund_rate")
public class FundRate {
	
	@Id
	private String id;
	private double value;
	private LocalDate date;
	@ManyToOne
    @JoinColumn(name = "fund_id", foreignKey = @ForeignKey(name = "FUND_ID_FK"))
	private Fund fund;
	
	public FundRate(double value, LocalDate date, Fund fund) {
		super();
		this.id = fund.getId() + "-" + date.toString();
		this.value = value;
		this.date = date;
		this.fund = fund;
	}
	
	public FundRate() {
		super();
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
	
	public Fund getFund() {
		return fund;
	}
	
	public void setFund(Fund fund) {
		this.fund = fund;
	}
}