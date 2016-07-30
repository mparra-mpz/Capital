package cl.fatman.capital.fund.web;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

import cl.fatman.capital.fund.logic.Controller;

@Component
public class ContextStoppedListener implements ApplicationListener<ContextStoppedEvent> {
	
	private Controller control = Controller.getInstance();

    @Override
    public void onApplicationEvent(ContextStoppedEvent contextStoppedEvent) {
    	control.tearDown();
    }
}