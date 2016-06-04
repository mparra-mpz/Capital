package cl.fatman.capital.fund;

import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class PersistenceDataTest {
	
	private static PersistenceData connection;
	
	
	@BeforeClass
	public static void setUp() {
		connection = PersistenceData.getInstance();
		connection.connect();
	}
	
	
	@AfterClass
	public static void tearDown() {
		connection.close();
	}
	
	
	@Test
	public void fomentUnitTest() {
		LocalDate ufDate = LocalDate.now();
		List<FomentUnit> ufList = new ArrayList<FomentUnit>();
		ufList.add(new FomentUnit(0.003, ufDate));
		connection.insertObjectList(ufList);
		List<FomentUnit> tmpList = (List<FomentUnit>)(List<?>)connection.selectAllObjects("from FomentUnit", FomentUnit.class);
		assertEquals("UF lists should be equals.", ufList.size(), tmpList.size());
	}
	
	
	@Test
	public void createFundEntryTest() {
		List<Fund> fundList = new ArrayList<Fund>();
		fundList.add(new Fund("Fake", "0-K", "A", "REM",  "Accionario", "Nacional", 1.0));
		fundList.add(new Fund("Fake", "0-K", "B", "REM",  "Accionario", "Nacional", 2.0));
		fundList.add(new Fund("Fake", "0-K", "C", "REM",  "Accionario", "Nacional", 3.0));
		connection.insertObjectList(fundList);
		List<Fund> tmpList = (List<Fund>)(List<?>)connection.selectAllObjects("from Fund", Fund.class);
		assertEquals("Fund lists should be equals", fundList.size(), tmpList.size());
	}

}