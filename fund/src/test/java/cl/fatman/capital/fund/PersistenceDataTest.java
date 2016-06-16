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
	public void selectEmptyFomentUnitTest() {
		List<?> tmpList = connection.selectAllObjects("from FomentUnit", FomentUnit.class);
		assertEquals("Foment Unit lists should be empty.", 0, tmpList.size());
	}
	
	@Test
	public void createFomentUnitTest() {
		LocalDate fuDate = LocalDate.now();
		List<FomentUnit> fuList = new ArrayList<FomentUnit>();
		fuList.add(new FomentUnit(0.003, fuDate));
		connection.insertObjectList(fuList);
		List<?> tmpList = connection.selectAllObjects("from FomentUnit", FomentUnit.class);
		assertEquals("Foment Unit lists should be equals.", fuList.size(), tmpList.size());
	}
	
	@Test
	public void selectEmptyFundTest() {
		List<?> tmpList = connection.selectAllObjects("from Fund", Fund.class);
		assertEquals("Fund lists should be empty.", 0, tmpList.size());
	}
	
	@Test
	public void createFundTest() {
		List<Fund> fundList = new ArrayList<Fund>();
		List<FundType> ftList = new ArrayList<FundType>();
		FundType type = new FundType(1, "Deuda < 90 días");
		ftList.add(type);
		connection.insertObjectList(ftList);
		fundList.add(new Fund("Fake", "0-K", "A", "REM", type));
		fundList.add(new Fund("Fake", "0-K", "B", "REM", type));
		fundList.add(new Fund("Fake", "0-K", "C", "REM", type));
		connection.insertObjectList(fundList);
		List<?> tmpList = connection.selectAllObjects("from Fund", Fund.class);
		assertEquals("Fund lists should be equals.", fundList.size(), tmpList.size());
	}
	
	@Test
	public void selectEmptyFundRateTest() {
		List<?> tmpList = connection.selectAllObjects("from FundRate", FundRate.class);
		assertEquals("Fund Rate lists should be empty.", 0, tmpList.size());
	}
	
	@Test
	public void createFundRateTest() {
		List<FundRate> frList = new ArrayList<FundRate>();
		List<Fund> fundList = new ArrayList<Fund>();
		List<FundType> ftList = new ArrayList<FundType>();
		LocalDate frDate = LocalDate.now();
		FundRate frTmp = null;
		FundType type = new FundType(1, "Deuda < 90 días");
		ftList.add(type);
		connection.insertObjectList(ftList);
		Fund fundTmp = new Fund("Fake", "0-K", "A", "REM", type);
		fundList.add(fundTmp);
		connection.insertObjectList(fundList);
		for (int i = 0; i < 10; i++) {
			frTmp = new FundRate(i/10000, frDate);
			frTmp.setFund(fundTmp);
			frList.add(frTmp);
		}
		connection.insertObjectList(frList);
		List<?> tmpList = connection.selectAllObjects("from FundRate", FundRate.class);
		assertEquals("Fund Rate lists should be equals.", frList.size(), tmpList.size());
	}
	
	@Test
	public void selectEmptyFundTypeTest() {
		List<?> tmpList = connection.selectAllObjects("from FundType", FundType.class);
		assertEquals("Fund Type lists should be empty.", 0, tmpList.size());
	}
	
	@Test
	public void createFundTypeTest() {
		List<FundType> ftList = new ArrayList<FundType>();
		FundType type1 = new FundType(1, "Deuda < 90 días");
		ftList.add(type1);
		FundType type2 = new FundType(2, "Deuda < 365 días");
		ftList.add(type2);
		connection.insertObjectList(ftList);
		List<?> tmpList = connection.selectAllObjects("from FundType", FundType.class);
		assertEquals("Fund Rate lists should be equals", ftList.size(), tmpList.size());
	}
}