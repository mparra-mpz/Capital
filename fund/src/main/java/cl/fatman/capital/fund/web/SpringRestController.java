package cl.fatman.capital.fund.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

import cl.fatman.capital.fund.logic.Controller;

@RestController
@RequestMapping("/fund")
public class SpringRestController {
	
	private Controller control = Controller.getInstance();
	
	@RequestMapping(value="/update_date", method=RequestMethod.GET)
	public LocalDate updateDate() {
		LocalDate updateDate = control.getFundRateUpdateDate();
		return updateDate;
	}

}