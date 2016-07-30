package cl.fatman.capital.fund.web;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import cl.fatman.capital.fund.logic.Controller;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {
	
	private Controller control = Controller.getInstance();

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    	control.setUp();
    	System.out.println("=== Context Refreshed Event Received ===");
    }
}