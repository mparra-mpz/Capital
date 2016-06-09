package cl.fatman.capital.fund;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
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
	
	
	public List<Fund> getFundData(int fundType) {
		logger.debug("getFundData(int fundType)");
		this.getURL(fundType);
		return null;
	}
	
	
	private String getURL(int fundType) {
		logger.debug("getURL(int fundType)");
		LocalDate tmpDate = LocalDate.now();
		String url = null;
		Document document = null;
		Elements rows = null;
		logger.debug("Search the URL with the lastest Fund information.");
		//Search a valid URL with fund information.
		for (int i = 0; i < 10; i++) {
			url = link + fundType + "&dia=" + (tmpDate.getDayOfMonth() - i) + 
                  "&mes=" + tmpDate.getMonthValue() + "&anio=" + tmpDate.getYear();
			logger.debug("Testing url: " + url);
			try {
				document = Jsoup.connect(url).userAgent(userAgent).get();
				rows = document.select("table").get(3).select("tr");
				if (rows.size() > 7) break; else url = "";
			} catch (IOException e) {
				url = "";
				logger.error("Problem validating url: ", e);
			}
		}
		logger.debug("Valid URL: " + url);
		return url;
	}
	
	public List<FundRate> getFundRateData(LocalDate frDate) {
		return null;
	}
}