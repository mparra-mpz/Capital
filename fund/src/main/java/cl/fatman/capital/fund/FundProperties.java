package cl.fatman.capital.fund;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FundProperties {
	
	private String propertyFile;
	private String userAgent;
	private String fundLink;
	private String fundQuery;
	private String ufLink;
	static final Logger logger = Logger.getLogger(FundProperties.class);
	
	
	public FundProperties(String propertyFile) {
		super();
		this.propertyFile = propertyFile;
	}
	
	
	public String getUserAgent() {
		return userAgent;
	}
	
	
	public String getFundLink() {
		return fundLink;
	}
	
	
	public String getFundQuery() {
		return fundQuery;
	}
	
	
	public String getUfLink() {
		return ufLink;
	}
	
	
	public String getPropertyFile() {
		return propertyFile;
	}
	
	
	public void setPropertyFile(String propertyFile) {
		this.propertyFile = propertyFile;
	}
	
	/*
	 * Method that read the property file and store the property values.
	 */
	public boolean readProperties() {
		logger.debug("readProperties()");
		boolean response = false;
		InputStream input = null;
		try {
			logger.debug("Start reading " + propertyFile + " file.");
			input = getClass().getClassLoader().getResourceAsStream(propertyFile);
			logger.debug("Loading properties.");
			Properties prop = new Properties();
			prop.load(input);
			userAgent = (String) prop.getProperty("connection.common.UserAgent");
			logger.debug("User agent: " + userAgent);
			fundLink = (String) prop.getProperty("connection.fund.Link");
			logger.debug("Fund link: " + fundLink);
			fundQuery = (String) prop.getProperty("connection.fund.Query");
			logger.debug("Raw fund query: " + fundQuery);
			ufLink = (String) prop.getProperty("connection.uf.Link");
			logger.debug("UF link: " + ufLink);
			logger.debug("Finish reading fund.properties file.");
			response = true;
		} catch (IOException e) {
			logger.error("Problem reading the property file.", e);
			response = false;
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error("Problem closing the property file.", e);
				}
			}
		}
		return response;
	}
}