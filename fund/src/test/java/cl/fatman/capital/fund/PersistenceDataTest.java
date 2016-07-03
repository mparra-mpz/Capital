package cl.fatman.capital.fund;

import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.ArrayList;

public class PersistenceDataTest {
	
	private PersistenceData persistence;
	private List<FundType> ftList;
	private List<Fund> fList;
	private List<FundRate> frList;
	
	@Before
	public void setUp() {
		ftList = new ArrayList<FundType>();
		fList = new ArrayList<Fund>();
		frList = new ArrayList<FundRate>();
		LocalDate fDate = LocalDate.now();
		persistence = PersistenceData.getInstance();
		persistence.setUp();
		FundType type = new FundType(1, "Deuda < 90 días");
		ftList.add(type);
		for (int i = 1; i <= 10; i++) {
			Fund temp = new Fund("Fake " + i, "0-k", "s-"+i, "VTR", type);
			fList.add(temp);
		}
		for (Fund fund : fList) {
			FundRate ft = new FundRate(0.003, fDate, fund);
			frList.add(ft);
		}
	}
	
	@After
	public void tearDown() {
		persistence.tearDown();
	}
	
	@Test
	public void createFundTypeTest() {
		persistence.insertObjectList(ftList);
		List<?> rList = persistence.selectAllObjects("from FundType", FundType.class);
		assertEquals("Stored list size should be equal to received list size", ftList.size(), rList.size());
	}
	
	@Test
	public void createFundTest() {
		persistence.insertObjectList(ftList);
		persistence.insertObjectList(fList);
		List<?> rList = persistence.selectAllObjects("from Fund", Fund.class);
		assertEquals("Stored list size should be equal to received list size", fList.size(), rList.size());
	}
	
	@Test
	public void createFundRateTest() {
		persistence.insertObjectList(ftList);
		persistence.insertObjectList(fList);
		persistence.insertObjectList(frList);
		List<?> rList = persistence.selectAllObjects("from FundRate", FundRate.class);
		assertEquals("Stored list size should be equal to received list size", frList.size(), rList.size());
	}
	
	@Test
	public void duplicationFailTest() {
		persistence.insertObjectList(ftList);
		List<Fund> tempList = new ArrayList<Fund>();
		for (int i = 0; i < 9; i++) {
			Fund temp = new Fund("Arcano", "0-k", "UNICO", "VTR", ftList.get(0));
			tempList.add(temp);
		}
		persistence.insertObjectList(tempList);
		List<?> rList = persistence.selectAllObjects("from Fund", Fund.class);
		assertEquals("Received list should be empty.", 0, rList.size());
	}
	
	@Test
	public void getFundByTypeTest() {
		persistence.insertObjectList(ftList);
		persistence.insertObjectList(fList);
		persistence.insertObjectList(frList);
		FundType type = ftList.get(0);
		List<?> rList = persistence.getFundByType(type);
		assertThat("Fund list should be greater than 9.", rList.size(), greaterThan(9));
	}
	
	@Test
	public void getUpdateDateTest() {
		List<FundRate> tmpList = new ArrayList<FundRate>();
		persistence.insertObjectList(ftList);
		persistence.insertObjectList(fList);
		Fund fund = fList.get(0);
		LocalDate fDate = LocalDate.of(LocalDate.now().getYear(), Month.DECEMBER, 31);
		for (int i = 1; i <= 10; i++) {
			fDate = fDate.plusDays(1);
			FundRate fr = new FundRate(0.003, fDate, fund);
			tmpList.add(fr);
		}
		persistence.insertObjectList(tmpList);
		List<?> rList = persistence.getUpdateDate();
		LocalDate tmpDate = (LocalDate) rList.get(0);
		assertTrue("The last store date should be equal to the received date.", tmpDate.isEqual(fDate));
	}
}