package cl.fatman.capital.fund;

import java.time.LocalDate;

public class App 
{
    public static void main( String[] args )
    {	
    	Controller control = Controller.getInstance();
    	control.setUp();
        System.out.println( "Starting application that load the funds data in the database.");
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusDays(4);
        control.storeFundData(startDate, endDate);
        control.tearDown();
        System.out.println( "Finishing application that load the funds data in the database.");
    }
}