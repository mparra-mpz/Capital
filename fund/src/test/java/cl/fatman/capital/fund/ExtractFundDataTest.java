package cl.fatman.capital.fund;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

public class ExtractFundDataTest {
	
	private static ExtractFundData efData;
	
	
	@BeforeClass
	public static void setUp() {
		String link = "http://www.aafm.cl/tecnoera/index.php?clase=informe&metodo=rentabilidad_html&inversion=%&administradora=%&tipo=";
		String userAgent = "Mozilla/5.0 (X11; Fedora; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.108 Safari/537.36";
		efData = new ExtractFundData(link, userAgent);
	}
	
	
	@Test
	public void getFundDataTest() {
		assertNotNull("List with Fund data should not be null", efData.getFundData("Deuda < 90 días", 1));
	}
}