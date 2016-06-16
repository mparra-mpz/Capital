package cl.fatman.capital.fund;

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
		String link = fProperties.getFundLink();
		String baseQuery = fProperties.getFundQuery();
		efData = new ExtractData(link, userAgent, baseQuery);
		type = new FundType(1, "Deuda < 90 días");
	}
	
	@Test
	public void fundDataNotNullTest() {
		LocalDate queryDate = LocalDate.of(2016, 1, 1);
		assertNotNull("Fund map should not be null", efData.getFundData(queryDate, type));
	}
	
	@Test
	public void fundDataNotEmptyTest() {
		LocalDate queryDate = LocalDate.of(2016, 1, 1);
		Map<Fund, Double>  tmpMap = efData.getFundData(queryDate, type);
		assertFalse("Fund map should not be empty.", tmpMap.isEmpty()); 
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
		Map<Fund, Double>  tmpMap = efData.getFundData(queryDate, type);
		assertTrue("Fund map should be empty.", tmpMap.isEmpty()); 
	}
}