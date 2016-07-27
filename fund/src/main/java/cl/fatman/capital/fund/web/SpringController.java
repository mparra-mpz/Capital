package cl.fatman.capital.fund.web;

import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class SpringController {
	
	@RequestMapping("/update_date")
	public String updateDate() {
		LocalDate updateDate = LocalDate.now();
		return updateDate.toString();
	}

}