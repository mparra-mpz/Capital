package cl.fatman.capital.fund;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class ControllerFailTest {
	
	private Controller control;
	private PersistenceData persistence;
	
	@Before
	public void setUp() {
		control = Controller.getInstance();
		control.setUp();
		persistence = PersistenceData.getInstance();
	}
	
	@After
	public void tearDown() {
		control.tearDown();
	}
	
	@Test
	public void storeFundDataFailTest() {
		control.storeFundData();
		control.storeFundData();
		List<?> fundRateList = persistence.selectAllObjects("from FundRate", FundRate.class);
		assertEquals("Fund rate list size shold be 0.", 0, fundRateList.size());
	}
}