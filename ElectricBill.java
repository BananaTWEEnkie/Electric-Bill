/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electricbill;

// Importing the correct libraries to use
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.InputMismatchException;

/**
 *
 * @author Twee
 */
public class ElectricBill
{
    public static void main(String[] args)
    {
        // Declare string input for do while loop
        String input;
        Scanner scan;
        Scanner scan2;
        Scanner scan3;
        Scanner scan4;
        
        do
        {
            // Create variables
            Date printDate;
            String name, dateIn = "";
            int month = 0;
            int usage = 0;
            boolean valid = false;
            boolean valid2 = false;

            // Initialize variables
            scan = new Scanner(System.in);
            scan2 = new Scanner(System.in);
            scan3 = new Scanner(System.in);
            scan4 = new Scanner(System.in);
            printDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

            // Print out heading
            System.out.println("\n           Southwest Power & Light");
            System.out.println("              Billing Statement\n");
            
            // Set format for date from user's computer
            System.out.println("Date: " + sdf.format(printDate));
           /*
            // Where user inputs their name and then validate it
                System.out.print("Please enter your name (Last, First I.) > ");
                name = scan.nextLine();
             */
            
            // Where user inputs meter reading date and validate it
            while(!valid)
            {
                try
                {
                    // This is where user inputs their meter reading date
                    System.out.print("Meter reading date (mm/dd/yyyy) > ");
                    dateIn = scan.nextLine();
                    
            
                    // Create an allowable date format and evaluate the input
                    // SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
                    sdf.setLenient(false);
            
                    sdf.parse(dateIn);
            
                    // Date entered must be valid to continue
                    valid = true;
            
                    // Create a new scanner using the existing valid input
                    scan2 = new Scanner(dateIn);
            
                    scan2.useDelimiter("/");
            
                    month = scan2.nextInt();

                }
            
                catch (Exception e)
                {
                    System.out.println("Date entered is not valid. Please try again in mm/dd/yyyy format.");
                }
            }
            
            // Where user inputs Electricity used and validate it
            while (!valid2)
            {
                try
                {
                    System.out.print("Electricity used (KW) > ");
                    usage = scan3.nextInt();
                    
                    valid2 = true;
                }
                catch (InputMismatchException e)
                {
                    System.out.println("Please enter a number.");
                }
            }
            
            System.out.println("\nName: Bob");
            System.out.println("Meter reading date:  " + dateIn);
            System.out.println("Electricity Used (KW): " + usage);
            
            // Call the function
            calculateCharge(usage, month); 
            
            // Ask the user if they want to calculate another bill
            System.out.print("\nDo you want another bill to be calculated? (Y/N) > ");
            input = scan4.nextLine();
            
        }while(!input.equalsIgnoreCase ("N")); 
        
        scan.close();
        scan2.close();
        scan3.close();
        scan4.close();
        System.out.println("\nThank you for letting us serve you!");
        
    }
   
    // Created a function to calculate the charge
    public static void calculateCharge (int usage, int month)
    {
        // Initialize constants
        final double SECOND_ZONE_PERCENT = 0.1;
        final double THIRD_ZONE_PERCENT = 0.25;

        // Initialize variables
        Double baseCharge = 0.0, overCharge = 0.0, excessCharge = 0.0;
        Double firstRate = 0.1;
        Double secondRate = 0.12;
        Double thirdRate = 0.15;
    
        // Calculate charge and display through switch method
        if (usage <= 350)
        {            
            switch(month)
            {
                case 12: 
                case 1:
                case 2: baseCharge = usage * firstRate;
                        break;
                case 3:
                case 4:
                case 5: baseCharge = usage * secondRate;
                        break;
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11: baseCharge = usage * thirdRate;
                        break;
            }
        }
        else if (usage <= 500)
        {
            
            // Create variable for minimum range
            int minUsage = 350;
            
            switch(month)
            {
                case 12: 
                case 1:
                case 2: baseCharge = minUsage * firstRate;
                        overCharge = (usage - minUsage) * (firstRate + (firstRate * SECOND_ZONE_PERCENT));
                        break;
                case 3:
                case 4:
                case 5: baseCharge = minUsage * secondRate;
                        overCharge = (usage - minUsage) * (secondRate + (secondRate * SECOND_ZONE_PERCENT));
                        break;
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11: baseCharge = minUsage * thirdRate;
                         overCharge = (usage - minUsage) * (thirdRate + (thirdRate * SECOND_ZONE_PERCENT));
                         break;
            }
        }
        else
        {           
            // Create variables for range
            int minUsage = 350;
            int maxUsage = 500;
            
            switch(month)
            {
                case 12: 
                case 1:
                case 2: baseCharge = minUsage * firstRate;
                        overCharge = (maxUsage - minUsage) * (firstRate + (firstRate * SECOND_ZONE_PERCENT));
                        excessCharge = (usage - maxUsage) * (firstRate + (firstRate * THIRD_ZONE_PERCENT));
                        break;
                case 3:
                case 4:
                case 5: baseCharge = minUsage * secondRate;
                        overCharge = (maxUsage - minUsage) * (secondRate + (secondRate * SECOND_ZONE_PERCENT));
                        excessCharge = (usage - maxUsage) * (secondRate + (secondRate * THIRD_ZONE_PERCENT));
                        break;
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11: baseCharge = minUsage * thirdRate;
                         overCharge = (maxUsage - minUsage) * (thirdRate + (thirdRate * SECOND_ZONE_PERCENT));
                         excessCharge = (usage - maxUsage) * (thirdRate + (thirdRate * THIRD_ZONE_PERCENT));
                         break;   
            }
        }      
               
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        
        baseCharge = Math.round(baseCharge*100)/100.;
        overCharge = Math.round(overCharge*100)/100.;
        excessCharge = Math.round(excessCharge*100)/100.;
        
        double total = baseCharge + overCharge + excessCharge;
            
        System.out.printf("%15s %7s","\nBaseline Charge              ", fmt.format(baseCharge));
        System.out.printf("%15s %7s","\nOver-baseline Charge         ", fmt.format(overCharge));
        System.out.printf("%15s %7s","\nExcess Charge                ", fmt.format(excessCharge));
        System.out.printf("%15s %7s","\n\nTotal                        ", fmt.format(total));
        
    } 
    
}