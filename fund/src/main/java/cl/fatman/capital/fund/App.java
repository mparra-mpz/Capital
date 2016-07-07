package cl.fatman.capital.fund;

public class App 
{
    public static void main( String[] args )
    {	
    	Controller control = Controller.getInstance();
    	control.setUp();
        System.out.println( "Starting application that load the funds data in the database.");
        control.storeFundData();
        control.storeFomentUnitData();
        control.tearDown();
        System.out.println( "Finishing application that load the funds data in the database.");
    }
}