package cl.fatman.capital.fund.logic;

import static org.junit.Assert.*;
import org.junit.Test;

public class ReadPropertiesTest {
	
	@Test
	public void readPropertiesTest() {
		ReadProperties fundProperties = new ReadProperties("fund.properties");
		assertTrue("The method response should be true.", fundProperties.getProperties());
	}
}