package cl.fatman.capital.fund;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Map;
import java.util.HashMap;

public class ReadProperties {
	
	private String propertyFile;
	private String userAgent;
	private String fundLink;
	private String fundQuery;
	private String ufLink;
	private Map<String, String> typeMap;
	static final Logger logger = Logger.getLogger(ReadProperties.class);
	
	public ReadProperties(String propertyFile) {
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
	
	public Map<String, String> getTypeMap() {
		return typeMap;
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
	public boolean getProperties() {
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
			typeMap = new HashMap<String, String>();
			String[] idList = prop.getProperty("connection.fund.type.id").split(",");
			String[] nameList = prop.getProperty("connection.fund.type.name").split(",");
			if (idList.length == nameList.length) {
				int top = idList.length;
				for (int i = 0; i < top; i++) {
					logger.debug("Fund Type: " + idList[i] + " " + nameList[i]);
					typeMap.put(idList[i], nameList[i]);
				}
			} else {
				logger.error("The properties for fund types could not be loaded, review the configuration file.");
			}
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