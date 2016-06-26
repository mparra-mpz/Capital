package cl.fatman.capital.fund;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
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
	public void selectFundTypeTest() {
		persistence.insertObjectList(ftList);
		FundType rFundType = persistence.selectFundType(1);
		assertNotNull("The fund type received should not be null", rFundType);
	}
	
	@Test
	public void selectFundTest() {
		persistence.insertObjectList(ftList);
		persistence.insertObjectList(fList);
		List<?> rList = persistence.selectAllObjects("from Fund", Fund.class);
		Fund temp = (Fund) rList.get(0);
		Fund rFund = persistence.selectFund(temp.getId());
		assertNotNull("The fund received should not be null", rFund);
	}
	
	@Test
	public void duplicationFundTest() {
		persistence.insertObjectList(ftList);
		List<Fund> tempList = new ArrayList<Fund>();
		for (int i = 0; i < 9; i++) {
			Fund temp = new Fund("Arcano", "0-k", "UNICO", "VTR", ftList.get(0));
			tempList.add(temp);
		}
		Fund temp = new Fund("Arcano", "1-k", "A", "VTR", ftList.get(0));
		tempList.add(temp);
		persistence.insertObjectList(tempList);
		List<?> rList = persistence.selectAllObjects("from Fund", Fund.class);
		assertEquals("The list size should be 2, duplicate object are not store.", 2, rList.size());
	}
	
	@Test
	public void tooManyConnectionsTest() {
		persistence.insertObjectList(ftList);
		persistence.insertObjectList(fList);
		persistence.insertObjectList(frList);
		for (int i = 1; i <= 500; i++) {
			Fund temp = new Fund("Fake " + i, "0-k", "s-"+i, "VTR", ftList.get(0));
			persistence.selectFund(temp.getId());
		}
		List<?> rList = persistence.selectAllObjects("from Fund", Fund.class);
		assertEquals("Stored list size should be equal to received list size", fList.size(), rList.size());
	}
}