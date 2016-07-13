package cl.fatman.capital.fund;

public class App 
{
    public static void main(String[] args)
    {
    	System.out.println( "Starting application that load economic information in the database.");
    	Controller control = Controller.getInstance();
    	control.setUp();
        control.storeFundData();
        control.storeFomentUnitData();
        control.tearDown();
        System.out.println( "Finishing application that load economic infromation in the database.");
    }
}