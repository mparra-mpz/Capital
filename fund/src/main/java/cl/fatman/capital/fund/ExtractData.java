package cl.fatman.capital.fund;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Map;
import java.util.HashMap;

public class ExtractData {
	
	private String fundLink;
	private String userAgent;
	private String baseQuery;
	private String ufLink;
	private static final Logger logger = LogManager.getLogger(ExtractData.class);
	
	public ExtractData(String fundLink, String userAgent, String baseQuery, String ufLink) {
		super();
		this.fundLink = fundLink;
		this.userAgent = userAgent;
		this.baseQuery = baseQuery;
		this.ufLink = ufLink;
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
	
	public String getUfLink() {
		return ufLink;
	}
	
	public void setUfLink(String ufLink) {
		this.ufLink = ufLink;
	}
	
	private String getURL(LocalDate queryDate, int typeNumber) {
		String url = "";
		MessageFormat query = new MessageFormat(baseQuery);
		Object[] queryObjects = {typeNumber, queryDate.getDayOfMonth(), queryDate.getMonthValue(), 
				                 Integer.toString(queryDate.getYear())};
		url = fundLink + query.format(queryObjects);
		return url;
	}
	
	private String getURL(int year) {
		String url = "";
		MessageFormat query = new MessageFormat(ufLink);
		Object[] queryObjects = {Integer.toString(year)};
		url = query.format(queryObjects);
		return url;
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
			logger.debug("Start processing table data from AAFM web page.");
			Document document = Jsoup.connect(url).userAgent(userAgent).timeout(10000).get();
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
			}
			logger.debug("Finish processing table data from AAFM web page, fund HashMap successfully created.");
		} catch (IOException e) {
			fundMap = null;
			logger.error("Can not retrive the AAFM web page.", e);
		} catch (NumberFormatException e) {
			fundMap = null;
			logger.error("Can not casting rate string to rate double.", e);
		} catch (IllegalArgumentException e) {
			fundMap = null;
			logger.error("The URL is no longer valid.", e);
		}
		return fundMap;
	}
	
	/*
	 * Method to retrieve the foment unit data from SII web page. We will store the foment unit data in Hash Map.
	 */
	public Map<String, FomentUnit> getFomentUnitData(int year) {
		logger.debug("getFomentUnitData(int year)");
		String url = this.getURL(year);
		logger.debug("User agent: " + userAgent);
		logger.debug("URL: " + url);
		Map<String, FomentUnit> ufMap = new HashMap<String, FomentUnit>();
		try {
			logger.debug("Start processing table data from SII web page.");
			Document document = Jsoup.connect(url).userAgent(userAgent).timeout(10000).get();
			Elements table = document.select("table.tabla tr");
			//First row for headers and first column for January values.
			//i will be use for months.
			for (int i = 1; i <= 12; i++) {
				YearMonth tmpYearMonth = YearMonth.of(year, i);
				int maxDay = tmpYearMonth.lengthOfMonth();
				//j will be use for days.
				for (int j = 1; j <= maxDay; j++) {
					LocalDate ufDate = LocalDate.of(year, i, j);
					//First element position is 1.
					Elements dayRow = table.get(j).select("td");
					//First element position is 0.
					String dayValue = dayRow.get(i-1).text();
					if (dayValue.contains("\u00a0")) continue;
					dayValue = dayValue.replace(".", "");
					double ufValue = Double.parseDouble(dayValue.replace(",", "."));
					logger.debug("Creating UF with date: " + ufDate + " and value: " + ufValue);
					FomentUnit uf = new FomentUnit(ufValue, ufDate);
					ufMap.put(ufDate.toString(), uf);
				}
			}
			logger.debug("Finish processing table data from SII web page, foment unit HashMap successfully created.");
		} catch (IOException e) {
			ufMap = null;
			logger.error("Can not retrive the SII web page.", e);
		} catch (NumberFormatException e) {
			ufMap = null;
			logger.error("Can not casting uf value string to uf value double.", e);
		} catch (IllegalArgumentException e) {
			ufMap = null;
			logger.error("The URL is no longer valid.", e);
		}
		return ufMap;
	}
}