package cl.fatman.capital.fund;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class PersistenceDataTest {
	
	private PersistenceData connection;
	
	
	@Before
	public void setUp() {
		connection = PersistenceData.getInstance();
		connection.connect();
	}
	
	
	@After
	public void tearDown() {
		connection.close();
	}
	
	
	@Test
	public void fomentUnitTest() {
		LocalDate fuDate = LocalDate.now();
		List<FomentUnit> fuList = new ArrayList<FomentUnit>();
		fuList.add(new FomentUnit(0.003, fuDate));
		connection.insertObjectList(fuList);
		List<?> tmpList = connection.selectAllObjects("from FomentUnit", FomentUnit.class);
		assertEquals("Foment Unit lists should be equals.", fuList.size(), tmpList.size());
	}
	
	
	@Test
	public void createFundEntryTest() {
		List<Fund> fundList = new ArrayList<Fund>();
		fundList.add(new Fund("Fake", "0-K", "A", "REM",  "Accionario", 1.0));
		fundList.add(new Fund("Fake", "0-K", "B", "REM",  "Accionario", 2.0));
		fundList.add(new Fund("Fake", "0-K", "C", "REM",  "Accionario", 3.0));
		connection.insertObjectList(fundList);
		List<?> tmpList = connection.selectAllObjects("from Fund", Fund.class);
		assertEquals("Fund lists should be equals", fundList.size(), tmpList.size());
	}
	
	@Test
	public void createFundRateEntryTest() {
		List<FundRate> frList = new ArrayList<FundRate>();
		List<Fund> fundList = new ArrayList<Fund>();
		LocalDate frDate = LocalDate.now();
		FundRate frTmp = null;
		Fund fundTmp = new Fund("Fake", "0-K", "A", "REM",  "Accionario", 1.0);
		fundList.add(fundTmp);
		connection.insertObjectList(fundList);
		for (int i = 0; i < 10; i++) {
			frTmp = new FundRate(i/10000, frDate);
			frTmp.setFund(fundTmp);
			frList.add(frTmp);
		}
		connection.insertObjectList(frList);
		List<?> tmpList = connection.selectAllObjects("from FundRate", FundRate.class);
		assertEquals("Fund Rate lists should be equals", frList.size(), tmpList.size());
	}
}