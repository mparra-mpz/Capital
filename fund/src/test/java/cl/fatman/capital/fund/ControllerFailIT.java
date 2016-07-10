package cl.fatman.capital.fund;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControllerFailIT {
	
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
	public void storeFundDataByDateFailTest() {
		//Insert a initial fund type.
		List<FundType> ftList = new ArrayList<FundType>();
		FundType type = new FundType(1, "Deuda < 90 días");
		ftList.add(type);
		persistence.insertObjectList(ftList);
		//Process to found and store funds.
		LocalDate startDate = LocalDate.of(1970, 1, 1);
		LocalDate endDate = startDate.plusDays(4);
		control.storeFundData(startDate, endDate);
		List<?> fundRateList = persistence.selectAllObjects("from FundRate", FundRate.class);
		assertEquals("Fund rate list size shold be 0.", 0, fundRateList.size());
	}
	
	@Test
	public void storeFundDataFailTest() {
		control.storeFundData();
		control.storeFundData();
		List<?> fundRateList = persistence.selectAllObjects("from FundRate", FundRate.class);
		assertEquals("Fund rate list size shold be 0.", 0, fundRateList.size());
	}
	
	@Test
	public void storeFomentUnitDataByDateFailTest() {
		LocalDate startDate = LocalDate.of(1970, 1, 1);
		LocalDate endDate = LocalDate.of(1974, 1, 1);
		control.storeFomentUnitData(startDate, endDate);
		List<?> ufList = persistence.selectAllObjects("from FomentUnit", FomentUnit.class);
		assertEquals("Foment unit list size shold be 0.", 0, ufList.size());
	}
}