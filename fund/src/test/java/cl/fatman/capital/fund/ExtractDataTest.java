package cl.fatman.capital.fund;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Map;

public class ExtractDataTest {
	
	private ExtractData efData;
	private FundProperties fProperties;
	
	@Before
	public void setUp() {
		fProperties = new FundProperties("fund.properties");
		fProperties.readProperties();
		String userAgent = fProperties.getUserAgent();
		String link = fProperties.getFundLink();
		String baseQuery = fProperties.getFundQuery();
		efData = new ExtractData(link, userAgent, baseQuery);
	}
	
	@Test
	public void fundDataNotNullTest() {
		LocalDate queryDate = LocalDate.of(2016, 1, 1);
		assertNotNull("Fund map should not be null", efData.getFundData(queryDate, "Deuda < 90 días", 1));
	}
	
	@Test
	public void fundDataNotEmptyTest() {
		LocalDate queryDate = LocalDate.of(2016, 1, 1);
		Map<Fund, Double>  tmpMap = efData.getFundData(queryDate, "Deuda < 90 días", 1);
		assertFalse("Fund map should not be empty.", tmpMap.isEmpty()); 
	}
	
	@Test
	public void fundDataNullTest() {
		LocalDate queryDate = LocalDate.of(2016, 1, 1);
		efData.setLink("");
		efData.setUserAgent("");
		assertNull("Fund map should be null.", efData.getFundData(queryDate, "Deuda < 90 días", 1));
	}
	
	@Test
	public void fundDataEmptyTest() {
		LocalDate queryDate = LocalDate.of(1970, 1, 1);
		Map<Fund, Double>  tmpMap = efData.getFundData(queryDate, "Deuda < 90 días", 1);
		assertTrue("Fund map should be empty.", tmpMap.isEmpty()); 
	}
}