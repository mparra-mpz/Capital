package cl.fatman.capital.fund;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class ControllerTest {
	
	private static Controller control;
	private static PersistenceData persistence;
	
	@BeforeClass
	public static void setUp() {
		control = Controller.getInstance();
		control.setUp();
		persistence = PersistenceData.getInstance();
		//Insert a initial fund type.
		List<FundType> ftList = new ArrayList<FundType>();
		FundType type = new FundType(1, "Deuda < 90 días");
		ftList.add(type);
		persistence.insertObjectList(ftList);
	}
	
	@AfterClass
	public static void tearDown() {
		control.tearDown();
	}
	
	@Test
	public void storeFundDataByDateTest() {
		LocalDate endDate = LocalDate.now();
		LocalDate startDate = endDate.minusDays(4);
		control.storeFundData(startDate, endDate);
		List<?> fundList = persistence.selectAllObjects("from Fund", Fund.class);
		List<?> fundRateList = persistence.selectAllObjects("from FundRate", FundRate.class);
		assertThat("Fund rate list should be greater than fund list.", fundRateList.size(), 
				   greaterThan(fundList.size()));
	}
	
	@Test
	public void storeFundDataTest() {
		control.storeFundData();
		control.storeFundData();
		List<?> fundList = persistence.selectAllObjects("from Fund", Fund.class);
		List<?> fundRateList = persistence.selectAllObjects("from FundRate", FundRate.class);
		assertThat("Fund rate list should be greater than fund list.", fundRateList.size(), 
				   greaterThan(fundList.size()));
	}
	
	@Test
	public void storeFomentUnitDataByDateTest() {
		LocalDate startDate = LocalDate.of(2013, 7, 7);
		LocalDate endDate = LocalDate.now();
		control.storeFomentUnitData(startDate, endDate);
		List<?> ufList = persistence.selectAllObjects("from FomentUnit", FomentUnit.class);
		int difference = (int) ChronoUnit.DAYS.between(startDate, endDate);
		assertThat("Foment unit list size should be greater than to the days difference", ufList.size(), 
				   greaterThan(difference));
	}
}