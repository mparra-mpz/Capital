package cl.fatman.capital.fund;

import static org.junit.Assert.*;
import org.junit.Test;

public class FundPropertiesTest {

	@Test
	public void readPropertiesTest() {
		FundProperties fundProperties = new FundProperties("fund.properties");
		assertTrue("The method response should be true.", fundProperties.readProperties());
	}

}
