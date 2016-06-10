package cl.fatman.capital.fund;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class ExtractFundData {
	
	private String link;
	private String userAgent;
	static final Logger logger = Logger.getLogger(ExtractFundData.class);
	
	
	public ExtractFundData(String link, String userAgent) {
		super();
		this.link = link;
		this.userAgent = userAgent;
	}
	
	
	public String getLink() {
		return link;
	}
	
	
	public void setLink(String link) {
		this.link = link;
	}
	
	
	public String getUserAgent() {
		return userAgent;
	}
	
	
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	
	public List<Fund> getFundData(String typeName, int typeNumber) {
		logger.debug("getFundData(int fundType)");
		String url = this.getValidURL(typeNumber);
		List<Fund> fundList = new ArrayList<Fund>();
		try {
			logger.debug("Start processing table data from AAFM web page.");
			Document document = Jsoup.connect(url).userAgent(userAgent).get();
			Elements rows = document.select("table").get(3).select("tr");
			for (Element row : rows) {
				Elements cols = row.select("td");
				String institution = cols.get(0).text().replace("\u00a0","");
				if (institution.isEmpty() || institution.contains("Administradora")) continue;
				if (institution.contains("TOTALES")) break;
				String run = cols.get(1).text().replace("\u00a0","");
				String name = cols.get(2).text().replace("\u00a0","");
				String series = cols.get(3).text().replace("\u00a0","");
				Fund tmpFund = new Fund(name, run, series, institution, typeName);
				fundList.add(tmpFund);
			}
		} catch (IOException e) {
			logger.error("Problem creating Fund list.", e);
			fundList = null;
		}
		return fundList;
	}
	
	
	/*
	 * Method to get a valid URL with the last information for the mutual funds,
	 * we will review the data from the last 3 days in the AAFM,to get the most
	 * recent valid url, if no valid url is found, return an empty string.
	 */
	private String getValidURL(int typeNumber) {
		logger.debug("getURL(int fundType)");
		LocalDate tmpDate = LocalDate.now();
		String url = "";
		logger.debug("Search the URL with the lastest Fund information.");
		logger.debug("User Agent: " + userAgent);
		for (int i = 0; i < 3; i++) {
			url = link + typeNumber + "&dia=" + (tmpDate.getDayOfMonth() - i) + 
                  "&mes=" + tmpDate.getMonthValue() + "&anio=" + tmpDate.getYear();
			logger.debug("Testing url: " + url);
			try {
				Document document = Jsoup.connect(url).userAgent(userAgent).get();
				Elements rows = document.select("table").get(3).select("tr");
				if (rows.size() > 7) break; else url = "";
			} catch (IOException e) {
				url = "";
				logger.error("Problem validating url: ", e);
			}
		}
		logger.debug("Valid URL: " + url);
		return url;
	}
}