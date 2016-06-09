package cl.fatman.capital.fund;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class ExtractFundDataTest {

	@Test
	public void getFundDataTest() {
		ExtractFundData efData = new ExtractFundData("blabla");
		assertNotNull("List with Fund data should not be null", efData.getFundData());
	}
	
	
	@Test
	public void getFundRateDataTest() {
		ExtractFundData efData = new ExtractFundData("blabla");
		LocalDate frDate = LocalDate.now();
		assertNotNull("List with Fund Rate data should not be null", efData.getFundRateData(frDate));
	}
}
