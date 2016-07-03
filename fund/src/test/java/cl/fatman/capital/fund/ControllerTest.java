package cl.fatman.capital.fund;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

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
	public void storeFundDataTest() {
		control.storeFundData();
		control.storeFundData();
		List<?> fundList = persistence.selectAllObjects("from Fund", Fund.class);
		List<?> fundRateList = persistence.selectAllObjects("from FundRate", FundRate.class);
		assertThat("Fund rate list should be greater than fund list.", fundRateList.size(), greaterThan(fundList.size()));
	}
}