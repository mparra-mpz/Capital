package cl.fatman.capital.fund;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ControllerTest {
	
	private static Controller control;
	private static PersistenceData persistence;
	
	@BeforeClass
	public static void setUp() {
		control = Controller.getInstance();
		control.setUp();
		List<FundType> ftList = new ArrayList<FundType>();
		FundType type = new FundType(1, "Deuda < 90 días");
		ftList.add(type);
		persistence = PersistenceData.getInstance();
		persistence.insertObjectList(ftList);
	}
	
	@AfterClass
	public static void tearDown() {
		control.tearDown();
	}
	
	@Test
	public void storeFundDataTest() {
		LocalDate endDate = LocalDate.now();
		LocalDate startDate = endDate.minusDays(3);
		control.storeFundData(startDate, endDate);
		List<?> rList = persistence.selectAllObjects("from FundRate", FundRate.class);
		assertThat("Fund rate list should be greater than 0.", rList.size(), greaterThan(0));
	}
}