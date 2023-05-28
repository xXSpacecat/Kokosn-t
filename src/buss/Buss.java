/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package buss;

import java.util.Scanner;

/**
 *
 * @author denise.leon
 */
public class Buss {

    /**
     * @param args the command line arguments
     */
  
    //Eftersom att jag vill använda dessa genom hela koden så skriver jag ut de som globala
    static String[] namnFält = new String[21];
    static int[] persfält = new int[21];
    static boolean fnstr = false;

    public static void main(String[] args) {
        //denna säger till så att vi kan stänga av hela grejen, den tas i funktion vid case tre
        boolean avstngd = false;
        
        //tänkte vara lite praktisk i mina namngivningar den här gången då det *kanske* kan ses som mer praktiskt
        Scanner input = new Scanner(System.in);
        
        while (!avstngd) {
            // Då det är ett val typ av system så va det bara logiskt att använda ett switch case här för att sedan leda de till sina respektive metoder
            try{
            System.out.print("Buss Boknings Meny:\n(1) Boka \n(2) Lista bokade \n(3) Avboka \n(4) Skriv ut total vinst\n(5) Lista obokade platser \n(6) Hitta din plats\n(7)Stäng av\nVad vill du göra?:");

            int val = input.nextInt();
            
            switch (val) {
                case 1:
                    //Fönsterplats frågas om först för att inte börja lägga in en bokning ifall det sker att inga platser kvar
                        System.out.println("Vill du ha en fönsterplats? (y/n)");
                        String fnstrPlts = input.next();

                        if (fnstrPlts.equalsIgnoreCase("y")) {
                            fnstr = true;
                        } else if (fnstrPlts.equalsIgnoreCase("n")) {
                            fnstr = false;
                        } else {
                            continue;
                        }
                        //Allt flyttat till en metod istället för att få en en massa parametrar
                        bokningV();

                    break;
                case 2:
                    try{
                    System.out.println("Vill ni lista ut bara de myndiga? (y/n)");
                    String list = input.next();
                    //Här så kommer alla personer att listas ut  med hjälp av den vanliga for loopen i dessa metoder
                    if(list.equalsIgnoreCase("y")){
                        större();
                    }
                    else if(list.equalsIgnoreCase("n")){
                        mindre();
                    }
                    else{
                        System.out.println("Jag förstod inte vad ni menade, snälla försök igen");
                    }}
                    catch (Exception e){
                        System.out.println("Jag förstod inte, snälla försök igen");
                    }
                    break;
                case 3:
                    System.out.println("Skriv in erat namn eller personnummer(ååååmmdd) tack: ");
                    String pnummer = input.next();
                    avBoka(pnummer);
                    break;
                case 7:
                    //jag ville bara ha en avstängningsfunktion så jag satte in allt i en while loop som det här tredje caset får hantera
                    avstngd = true;
                    break;
                    
                case 4:
                    System.out.println("Vinsten ligget på: "+vinsten(persfält.length)+" kr");
                    
                    break;
                    
                case 5:
                    for(int i = 0; i < persfält.length;i++){  
                    if (persfält[i]==0){
                    System.out.println("Plats " + i + " är ledig, ");         
            }      }
                    break;
                
                case 6:
                    System.out.println("Ange ertnamn eller personnummer(ååååmmdd) tack: ");
                    String hittap= input.next();
                    hittaplats(hittap);
                    break;   
                    
                default:
                    System.out.println("Välj mellan 1, 2, 3, 4 och 5 tack.");
                    break;
                    
            }
            
        }catch(Exception e){
                    System.out.println("Jag förstod inte riktigt, snälla försök igen");                           
                    }
            input.nextLine();
    }}

    static int bokningV() {
        Scanner input = new Scanner(System.in);
        
            int ledigPlatsIndex = ledigPlats();

            if (ledigPlatsIndex == -1) {
                System.out.println("Det finns tyvärr inga lediga platser.");
            } else {
                //Här sker äntligen självaste bokningen
                System.out.println("Skriv in ditt personnummer (ÅÅÅÅMMDD):");
                int personnummer = input.nextInt();
                input.nextLine();

                System.out.println("Skriv in ditt namn:");
                String namn = input.nextLine();
                //Personnummret och namnet som sparas i variablerna stoppas in i fälten som båda har samma index
                persfält[ledigPlatsIndex] = personnummer;
                namnFält[ledigPlatsIndex] = namn;
                 //Istället för en if statement använder jag dessa operatorer som kollar om något är sant eller falskt och använder av den 'value' som behövs beroende på om det ska vara en fönsterplats eller inte
                String platsTyp = fnstr ? "fönsterplats" : "mittplats";

                 //Genom att dela upp platserna för att ge dom en speceiell typ så kan jag säga till så att samma index alltid kommer en speciell plats, som t.ex fönsterplatserna  och så att resten blir mitt eller gångplats som kommer upp i raderna över.
                if (ledigPlatsIndex % 2 == 0) {
                    platsTyp = fnstr ? "fönsterplats" : "gångplats";
                }
                //Det här är en snabb lite konfirmation på bokningen så att jag kan se så att allt gick bra och blev rätt
                System.out.println("Plats " + ledigPlatsIndex + " (" + platsTyp + ") bokad för " + namn + " (personnummer: " + personnummer + ").");
            }


        return 0;
    }

  static int ledigPlats() {
    for (int i = 0; i < persfält.length; i++) {
        if (persfält[i] == 0) {
            if ((i % 2 == 0 && fnstr) || (i % 2 == 1 && !fnstr)) {
                // Här kan jag först kolla vilka platser som är lediga sedan också ta in om det ska vara en fönsterplats eller inte så att alla hamnar rätt där i kommer att vara indexen i fältet
                return i;
            }
        }
    }

    // Detta betyder att bussen är full
    return -1;
}
    
    static void avBoka(String avbokning) {
        boolean avbokad = false;
        //Med en forloop kollar jag om det som skrivits in matchar med något av personnummrerna i bokningarna.
        for (int i = 0; i < namnFält.length; i++) {
            if (namnFält[i] != null && (namnFält[i].equalsIgnoreCase(avbokning) || String.valueOf(persfält[i]).equals(avbokning))) {
            //När en match hittas så nollställs den platsen och ett medelande skickas ut
                namnFält[i] = null;
                persfält[i] = 0;
                avbokad = true;
                System.out.println("Bokningen för " + avbokning + " på plats " + i + " har avbokats.");
                break;
            }
        }
        if (!avbokad) {
            //Om vi inte hittar en matchning så ska det medelas till användaren. 
            System.out.println("Ingen bokning hittades för " + avbokning + ".");
        }
    }

    
    
    static void mindre(){
        System.out.println("Passagerare under 18 år:");
        //som på resterande platser så sker här en jämförelse där om personnumret vid en plats är under myndighetsgränsen så skrivs den ut
        for(int i = 0; i < persfält.length;i++){  
            if (persfält[i]>20060000){
            System.out.println("Plats " + i + ": " + persfält[i] +" "+namnFält[i]);   
            }
            else if(i==20 && persfält[i]==0){
                System.out.println("inga bokningar här än...");
            }
        }
    }
    
     static void större(){
        System.out.println("Passagerare över 18 år:");
        //Det här är samma sak som övre metoden bara att den skriver ut de som är över 18 år
        for(int i = 0; i < persfält.length;i++){  
            if (persfält[i]<20060000 && persfält[i]!=0){
    System.out.println("Plats " + i + ": " + persfält[i] +" "+namnFält[i]);         
            }
            else if(i==20 && persfält[i]==0){
                System.out.println("Inga bokningar här än...");
            }
        }
    }
    
     
     
     static void hittaplats(String pnummer){
         
        boolean hitta = false;
         //Hittar platsen genom att jämföra i en ifsats med hjälp av en forloop
         for(int i = 0; i< persfält.length;i++){
            if (namnFält[i] != null && (namnFält[i].equalsIgnoreCase(pnummer) || String.valueOf(persfält[i]).equals(pnummer))){
                System.out.println("Du har plats "+ i);
                hitta =true;
                break;
            }
            //För att skicka ut medelandet om plats som inte finns bara ska ske en gång och inte för varje plats den checkar.
        }
         if(!hitta){
            
                System.out.println("Ingen bokning med personnummret "+pnummer+" har hittats");
        }
         
     }
     
     
     //Testar rekretion
      static double vinsten(int antal) {
    double nyVinst = 0;
    if (antal > 0) {
        //Här så kollas åldern på passageraren, antalet är hur många platser det finns i bussen
        if (persfält[antal - 1] != 0  && persfält[antal-1]<19540000) { 
            //När rätt ålder hittats så kommer vinsten öka med respektive pris och sedan lägga till det som den får ut av att köra metoden igen tills inte platserna i bussen tar slut.
            antal--;
            nyVinst = 199.90 + vinsten(antal);         
        } 
        else if (persfält[antal - 1] != 0 && persfält[antal-1]>20060000) { 
            antal--;
            nyVinst = 149.90 + vinsten(antal);  
        }
        else if (persfält[antal - 1] != 0) {
            antal--;
            nyVinst = 299.90 + vinsten(antal);  
        }
        else {
            //Om platsen är tom ska den fortfarande kolla igenom nästa plats genom att köra om metoden med nytt antal
            antal--;
            nyVinst = vinsten(antal); 
        }
    }
    return nyVinst;
}
}

    

