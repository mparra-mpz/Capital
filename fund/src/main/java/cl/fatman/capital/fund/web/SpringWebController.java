package cl.fatman.capital.fund.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SpringWebController {
	
	@RequestMapping("/")
    public String index(){
        return "index";
    }

}
