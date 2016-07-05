package cl.fatman.capital.fund;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class Controller {
	
	private static final Controller INSTANCE = new Controller();
	private ReadProperties reader;
	private ExtractData extractor;
	private PersistenceData persistence;
	static final Logger logger = Logger.getLogger(Controller.class);
	
	private Controller() {
		
	}
	
	public static Controller getInstance() {
		return INSTANCE;
	}
	
	public void setUp() {
		logger.info("setUp()");
		reader = new ReadProperties("fund.properties");
		if (reader.getProperties()) {
			logger.info("Configuration file loaded successfully.");
			extractor = new ExtractData(reader.getFundLink(), reader.getUserAgent(), 
					                    reader.getFundQuery(), reader.getUfLink());
			persistence = PersistenceData.getInstance();
			persistence.setUp();
			logger.info("The Controller setup finished successfully.");
		} else {
			logger.error("Problem loading the configuration file.");
		}
	}
	
	public void tearDown() {
		logger.info("tearDown()");
		persistence.tearDown();
	}
	
	private Map<String, Fund> fundsByType(FundType type) {
		logger.debug("Map<String, Fund> fundsByType()");
		logger.debug("Get funds for fund type: " + type.getName());
		Map<String, Fund> fundMap = new HashMap<String, Fund>();
		List<?> fundList = persistence.getFundByType(type);
		for (Object object : fundList) {
			Fund fund = (Fund) object;
			fundMap.put(fund.getId(), fund);
		}
		logger.debug("Funds select by type was saved in a map.");
		return fundMap;
	}
	
	private LocalDate getUpdateDate() {
		logger.debug("getUpdateDate()");
		LocalDate uDate = LocalDate.of(LocalDate.now().getYear() - 1, Month.DECEMBER, 31);
		List<?> rList = persistence.getUpdateDate();
		if (rList != null && rList.size() > 0) uDate= (LocalDate) rList.get(0);
		logger.debug("Last update date was: " + uDate.toString());
		return uDate;
	}
	
	private void storeFundData(LocalDate startDate, LocalDate endDate) {
		logger.info("storeFundData(LocalDate startDate, LocalDate endDate)");
		logger.info("Start the fund data recollection/store.");
		List<?> ftList = persistence.selectAllObjects("from FundType", FundType.class);
		while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
			logger.info("Processing data for date: " + startDate.toString());
			for (Object object : ftList) {
				FundType ft = (FundType) object;
				logger.info("Retrieve fund type: " + ft.getName());
				Map<Fund, Double> fundMap = extractor.getFundData(startDate, ft);
				if (fundMap == null) {
					logger.error("Problem retrieving the map with fund rate data, review the logs.");
					continue;
				}
				logger.info("Retrieve " + fundMap.size() + " funds.");
				Map<String, Fund> storeFundMap = this.fundsByType(ft);
				logger.info("Retrieve " + storeFundMap.size() + " stored funds.");
				List<Fund> fList = new ArrayList<Fund>();
				List<FundRate> frList = new ArrayList<FundRate>();
				for (Map.Entry<Fund, Double> entry : fundMap.entrySet()) {
					Fund fund = entry.getKey();
					double rate = entry.getValue();
					Fund rFund = storeFundMap.get(fund.getId());
					if (rFund == null) {
						logger.debug(fund.getName() + " doesn't exist, adding to the list.");
						fList.add(fund);
						FundRate fr = new FundRate(rate, startDate, fund);
						frList.add(fr);
					} else {
						logger.debug(rFund.getName() + " found in the database.");
						FundRate fr = new FundRate(rate, startDate, rFund);
						frList.add(fr);
					}
				}
				logger.info("Finished the funds data retrieving.");
				persistence.insertObjectList(fList);
				logger.info(fList.size() + " fund stored in the database.");
				persistence.insertObjectList(frList);
				logger.info(frList.size() + " fund rate stored in the database.");
			}
			logger.info("Finished processing data for date: " + startDate.toString());
			startDate = startDate.plusDays(1);
		}
		logger.info("Finished fund data recollection/store."); 
	}
	
	public void storeFundData() {
		logger.info("storeFundData()");
		LocalDate startDate = this.getUpdateDate().plusDays(1);
		LocalDate endDate = LocalDate.now();
		long difference = ChronoUnit.DAYS.between(startDate, endDate);
		logger.info("Database outdated in " + difference + " days.");
		if (difference > 10) {
			endDate = startDate.plusDays(9);
		}
		this.storeFundData(startDate, endDate);
		String msg = "Database update from " + startDate.toString() + " until " + endDate.toString();
		logger.info(msg);
	}
	
	public void storeFomentUnitData(LocalDate startDate, LocalDate endDate) {
		logger.info("storeFomentUnitData(LocalDate startDate, LocalDate endDate)");
		logger.info("Start the foment unit data recollection/store.");
		List<FomentUnit> ufList = new ArrayList<FomentUnit>();
		Map<String, FomentUnit> ufMap = new HashMap<String, FomentUnit>();
		for (int i = startDate.getYear(); i <= endDate.getYear(); i++) {
			ufMap.putAll(extractor.getFomentUnitData(i));
		}
		logger.info("Retrieve " + ufMap.size() + " foment unit values.");
		while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
			FomentUnit uf = ufMap.get(startDate.toString());
			if (uf != null) {
				logger.debug("Found foment unit value for: " + uf.getDate().toString());
				ufList.add(uf);
			} else {
				logger.debug("Not found foment unit value for: " + startDate.toString());
			}
			startDate = startDate.plusDays(1);
		}
		logger.info("Finished the foment unit data retrieving.");
		persistence.insertObjectList(ufList);
		logger.info(ufList.size() + "  foment unit stored in the database.");
		logger.info("Finished the foment unit data recollection/store.");
	}
}