package cl.fatman.capital.fund;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;

public class ExtractData {
	
	private String fundLink;
	private String userAgent;
	private String baseQuery;
	static final Logger logger = Logger.getLogger(ExtractData.class);
	
	public ExtractData(String fundLink, String userAgent, String baseQuery) {
		super();
		this.fundLink = fundLink;
		this.userAgent = userAgent;
		this.baseQuery = baseQuery;
	}
	
	public String getFundLink() {
		return fundLink;
	}
	
	public void setFundLink(String fundLink) {
		this.fundLink = fundLink;
	}
	
	public String getUserAgent() {
		return userAgent;
	}
	
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	public String getBaseQuery() {
		return baseQuery;
	}
	
	public void setBaseQuery(String baseQuery) {
		this.baseQuery = baseQuery;
	}
	
	/*
	 * Method to retrieve the fund information and fund rate from the AAFM web page using a date and fund type.
	 * We will store the fund data in Hash Map.
	 */
	public Map<Fund, Double> getFundData(LocalDate queryDate, FundType type) {
		logger.debug("getFundData(LocalDate queryDate, String typeName, int typeNumber)");
		String url = this.getURL(queryDate, type.getId());
		logger.debug("User agent: " + userAgent);
		logger.debug("URL: " + url);
		Map<Fund, Double> fundMap = new HashMap<Fund, Double>();
		try {
			logger.info("Start processing table data from AAFM web page.");
			Document document = Jsoup.connect(url).userAgent(userAgent).get();
			Elements rows = document.select("table").get(3).select("tr");
			for (Element row : rows) {
				Elements cols = row.select("td");
				String institution = cols.get(0).text().replace("\u00a0","");
				//Discard header row, summary row and empty rows.
				if (institution.isEmpty() || institution.contains("Administradora")) continue;
				if (institution.contains("TOTALES")) break;
				String run = cols.get(1).text().replace("\u00a0","");
				String name = cols.get(2).text().replace("\u00a0","");
				String series = cols.get(3).text().replace("\u00a0","");
				String tmpRate = cols.get(5).text().replace(".", "").replace(" %", "");
				//Discard rows without a fund rate.
				if (tmpRate.contains("\u00a0")) continue;
				Double rate = Double.parseDouble(tmpRate.replace(",", "."));
				logger.debug(institution + " " + name + " " + run + " " + series + " " + rate);
				Fund tmpFund = new Fund(name, run, series, institution, type);
				fundMap.put(tmpFund, rate);
				logger.debug("Fund data created and added to the HashMap.");
			}
			logger.info("Finish processing table data from AAFM web page. Fund HashMap successfully created.");
		} catch (IOException e) {
			fundMap = null;
			logger.error("Can not retrie the AAFM web page.", e);
		} catch (NumberFormatException e) {
			fundMap = null;
			logger.error("Can not casting rate string to rate double.", e);
		} catch (IllegalArgumentException e) {
			fundMap = null;
			logger.error("The URL is no longer valid.", e);
		}
		return fundMap;
	}
	
	private String getURL(LocalDate queryDate, int typeNumber) {
		String url = "";
		MessageFormat query = new MessageFormat(baseQuery);
		Object[] queryObjects = {typeNumber, queryDate.getDayOfMonth(), queryDate.getMonthValue(), 
				                 Integer.toString(queryDate.getYear())};
		url = fundLink + query.format(queryObjects);
		return url;
	}
}