package cl.fatman.capital.fund;

import static org.junit.Assert.*;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Map;

public class ExtractFundDataTest {	
	
	
	@Test
	public void fundDataNotNullTest() {
		String link = "http://www.aafm.cl/tecnoera/index.php";
		String userAgent = "Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.108 Safari/537.36";
		LocalDate queryDate = LocalDate.of(2016, 1, 1);
		ExtractFundData efData = new ExtractFundData(link, userAgent);
		assertNotNull("Fund map should not be null", efData.getFundData(queryDate, "Deuda < 90 días", 1));
	}
	
	
	@Test
	public void fundDataNotEmptyTest() {
		String link = "http://www.aafm.cl/tecnoera/index.php";
		String userAgent = "Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.108 Safari/537.36";
		LocalDate queryDate = LocalDate.of(2016, 1, 1);
		ExtractFundData efData = new ExtractFundData(link, userAgent);
		Map<Fund, Double>  tmpMap = efData.getFundData(queryDate, "Deuda < 90 días", 1);
		assertFalse("Fund map should not be empty.", tmpMap.isEmpty()); 
	}
	
	
	@Test
	public void fundDataNullTest() {
		String link = "";
		String userAgent = "";
		LocalDate queryDate = LocalDate.of(2016, 1, 1);
		ExtractFundData efData = new ExtractFundData(link, userAgent);
		assertNull("Fund map should be null.", efData.getFundData(queryDate, "Deuda < 90 días", 1));
	}
	
	
	@Test
	public void fundDataEmptyTest() {
		String link = "http://www.aafm.cl/tecnoera/index.php";
		String userAgent = "Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.108 Safari/537.36";
		LocalDate queryDate = LocalDate.of(1970, 1, 1);
		ExtractFundData efData = new ExtractFundData(link, userAgent);
		Map<Fund, Double>  tmpMap = efData.getFundData(queryDate, "Deuda < 90 días", 1);
		assertTrue("Fund map should be empty.", tmpMap.isEmpty()); 
	}
}