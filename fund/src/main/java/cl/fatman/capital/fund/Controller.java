package cl.fatman.capital.fund;

import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			extractor = new ExtractData(reader.getFundLink(), reader.getUserAgent(), reader.getFundQuery());
			persistence = PersistenceData.getInstance();
			persistence.setUp();
			logger.info("The Controller setup finished successfully.");
		} else {
			logger.error("Problem loading the configuration file, finishing the application.");
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
		logger.debug("Finish retrieve/store funds by type.");
		return fundMap;
	}
	
	public void storeFundData(LocalDate startDate, LocalDate endDate) {
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
					//Fund rFund = storeFundMap.remove(fund.getId());
					Fund rFund = storeFundMap.get(fund.getId());
					if (rFund == null) {
						logger.info(fund.getName() + " doesn't exist, adding to the list.");
						fList.add(fund);
						FundRate fr = new FundRate(rate, startDate, fund);
						frList.add(fr);
					} else {
						logger.info(rFund.getName() + " found in the database.");
						FundRate fr = new FundRate(rate, startDate, rFund);
						frList.add(fr);
					}
				}
				logger.info("Finish the funds data retrieving.");
				persistence.insertObjectList(fList);
				logger.info(fList.size() + " fund stored in the database.");
				persistence.insertObjectList(frList);
				logger.info(frList.size() + " fund rate stored in the database.");
			}
			logger.info("Finish processing data for date: " + startDate.toString());
			startDate = startDate.plusDays(1);
		}
		logger.info("Finish the fund data recollection/store."); 
	}
}