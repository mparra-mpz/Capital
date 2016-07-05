package cl.fatman.capital.fund;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Map;

public class ExtractDataTest {
	
	private ExtractData efData;
	private ReadProperties fProperties;
	private FundType type;
	
	@Before
	public void setUp() {
		fProperties = new ReadProperties("fund.properties");
		fProperties.getProperties();
		String userAgent = fProperties.getUserAgent();
		String fundLink = fProperties.getFundLink();
		String baseQuery = fProperties.getFundQuery();
		String ufLink = fProperties.getUfLink();
		efData = new ExtractData(fundLink, userAgent, baseQuery, ufLink);
		type = new FundType(1, "Deuda < 90 días");
	}
	
	@Test
	public void getFundDataTest() {
		LocalDate queryDate = LocalDate.of(2016, 1, 1);
		Map<Fund, Double>  fundMap = efData.getFundData(queryDate, type);
		assertThat("Fund map should not be greater than 10.", fundMap.size(), greaterThan(10));
	}
	
	@Test
	public void fundDataNotNullTest() {
		LocalDate queryDate = LocalDate.of(2016, 1, 1);
		assertNotNull("Fund map should not be null", efData.getFundData(queryDate, type));
	}
	
	@Test
	public void fundDataNotEmptyTest() {
		LocalDate queryDate = LocalDate.of(2016, 1, 1);
		Map<Fund, Double>  fundMap = efData.getFundData(queryDate, type);
		assertFalse("Fund map should not be empty.", fundMap.isEmpty()); 
	}
	
	@Test
	public void fundDataNullTest() {
		LocalDate queryDate = LocalDate.of(2016, 1, 1);
		efData.setFundLink("");
		efData.setUserAgent("");
		assertNull("Fund map should be null.", efData.getFundData(queryDate, type));
	}
	
	@Test
	public void fundDataEmptyTest() {
		LocalDate queryDate = LocalDate.of(1970, 1, 1);
		Map<Fund, Double>  fundMap = efData.getFundData(queryDate, type);
		assertTrue("Fund map should be empty.", fundMap.isEmpty()); 
	}
	
	@Test
	public void getFomentUnitDataTest() {
		LocalDate ufDate = LocalDate.now();
		Map<String, FomentUnit> ufMap = efData.getFomentUnitData(2016);
		assertThat("Foment unit map should not be greater than the days of the year.", ufMap.size(), 
				   greaterThan(ufDate.getDayOfYear()));
	}
	
	@Test
	public void fomentUnitDataNotNullTest() {
		assertNotNull("Foment unit map should not be null", efData.getFomentUnitData(2016)); 
	}
	
	@Test
	public void fomentUnitDataNullTest() {
		assertNull("Foment unit map should not be null", efData.getFomentUnitData(1910)); 
	}
}