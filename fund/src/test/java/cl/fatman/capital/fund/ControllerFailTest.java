package cl.fatman.capital.fund;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
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
	
	@Test
	public void storeFomentUnitDataFailTest() {
		LocalDate startDate = LocalDate.of(1910, 1, 1);
		LocalDate endDate = LocalDate.of(1912, 1, 1);
		control.storeFomentUnitData(startDate, endDate);
		List<?> ufList = persistence.selectAllObjects("from FomentUnit", FomentUnit.class);
		assertEquals("Foment unit list size shold be 0.", 0, ufList.size());
	}
}