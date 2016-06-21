package cl.fatman.capital.fund;

import org.apache.log4j.Logger;

import java.time.LocalDate;
import java.util.ArrayList;
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
		logger.debug("setUp()");
		reader = new ReadProperties("fund.properties");
		if (reader.getProperties()) {
			logger.debug("Configuration file loaded successfully.");
			extractor = new ExtractData(reader.getFundLink(), reader.getUserAgent(), reader.getFundQuery());
			persistence = PersistenceData.getInstance();
			persistence.setUp();
			logger.debug("The Controller setup finished successfully.");
		} else {
			logger.error("Problem loading the configuration file, finishing the application.");
			this.tearDown();
		}
	}
	
	public void tearDown() {
		logger.debug("tearDown()");
		persistence.tearDown();
	}
	
	public void storeFundData(LocalDate startDate, LocalDate endDate) {
		logger.debug("storeFundData(LocalDate startDate, LocalDate endDate)");
		List<?> ftList = persistence.selectAllObjects("from FundType", FundType.class);
		while (startDate.isBefore(endDate) || startDate.isEqual(endDate)) {
			logger.debug("Processing data for date: " + startDate.toString());
			for (Object object : ftList) {
				FundType ft = (FundType) object;
				logger.debug("Retrieve fund type: " + ft.getName());
				Map<Fund, Double> fundMap = extractor.getFundData(startDate, ft);
				if (fundMap == null) {
					logger.error("Problem retrieving the map with fund rate data, review the logs.");
					continue;
				}
				logger.debug("Retrieve " + fundMap.size() + " funds.");
				List<Fund> fList = new ArrayList<Fund>();
				List<FundRate> frList = new ArrayList<FundRate>();
				for (Map.Entry<Fund, Double> entry : fundMap.entrySet()) {
					Fund fund = entry.getKey();
					double rate = entry.getValue();
					Fund rFund = persistence.selectFund(fund.getRun(), fund.getSeries());
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
				logger.debug("Finish the funds data retrieving.");
				persistence.insertObjectList(fList);
				logger.debug(fList.size() + " fund stored in the database.");
				persistence.insertObjectList(frList);
				logger.debug(frList.size() + " fund rate stored in the database.");
			}
			logger.debug("Finish processing data for date: " + startDate.toString());
			startDate = startDate.plusDays(1);
		}
	}
}