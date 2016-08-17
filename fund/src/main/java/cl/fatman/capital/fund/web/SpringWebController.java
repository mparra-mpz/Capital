package cl.fatman.capital.fund.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/fund")
public class SpringWebController {
	
	@RequestMapping(value="", method=RequestMethod.GET)
    public String index(){
        return "index";
    }
	
	@RequestMapping(value="/update", method=RequestMethod.GET)
    public String update(){
        return "update";
    }

}
