package cl.fatman.capital.fund;

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
		reader = new ReadProperties("fund.properties");
		reader.getProperties();
		extractor = new ExtractData(reader.getFundLink(), reader.getUserAgent(), reader.getFundQuery());
		persistence = PersistenceData.getInstance();
	}
}