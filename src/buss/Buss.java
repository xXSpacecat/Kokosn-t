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
    static int[] persfält = new int[21];
    static boolean fnstr = false;

    public static void main(String[] args) {
        //denna säger till så att vi kan stänga av hela grejen, den tas i funktion vid case tre
        boolean avstngd = false;
        
        //tänkte vara lite praktisk i mina namngivningar den här gången då det *kanske* kan ses som mer praktiskt
        Scanner input = new Scanner(System.in);
        try{
        while (!avstngd) {
            // Då det är ett val typ av system så va det bara logiskt att använda ett switch case här för att sedan leda de till sina respektive metoder
            
            System.out.print("Buss Boknings Meny:\n(1) Boka \n(2) Lista bokade \n(3) Avboka \n(4) Skriv ut total vinst\n(5) Lista obokade platser \n(6) Hitta din plats\n(7) Stäng av\nVad vill du göra?:");

            int val = input.nextInt();
            
            switch (val) {
                case 1:
                 //Jag tror jag kunde stoppat in det här i en metod men nu är det för sent
                    System.out.println("Vill du ha en fönsterplats? (y/n)");
                    String fnstrPlts = input.next();
                    
                    //Här så kollar jag om de ska ha fönsterpats eller inte, jag använder equals eftersom att jag vill använda ignorecase för jagg tror att det skulle vara jobbigare om jag använde den där == operatoren
                    if (fnstrPlts.equalsIgnoreCase("y")) {
                        fnstr = true;
                    } else if (fnstrPlts.equalsIgnoreCase("n")) {
                        fnstr = false;
                    } else {
                        continue;
                    }
                    //Med hjälp av denna metod kan jag se om det finns lediga platser, för fönsterplats respektive gångplats, det skulle vara onödigt att fortsätta om det inte fanns några platser kvar.
                    int ledigPlatsIndex = ledigPlats();

                    if (ledigPlatsIndex == -1) {
                        System.out.println("Det finns tyvärr inga lediga platser.");
                    } else {
                        //Här sker äntligen självaste boknningen
                        int personnummer = bokningV(persfält);
                        //Istället för en if statement använder jag dessa operatorer som kollar om något är sant eller falskt och använder av den 'value' som behövs beroende på om det ska vara en fönsterplats eller inte
                        String platsTyp = fnstr ? "fönsterplats" : "mittplats";
                        
                           //Genom att dela upp platserna för att ge dom en speceiell typ så kan jag säga till så att samma index alltid kommer en speciell plats, som t.ex fönsterplatserna  och så att resten blir mitt eller gångplats som kommer upp i raderna över.
                        if (ledigPlatsIndex % 2 == 0) {
                            platsTyp = fnstr ? "fönsterplats" : "gångplats";
                        }
                        persfält[ledigPlatsIndex] = personnummer;
                        
                        //Det här är en snabb lite konfirmation på bokningen så att jag kan se så att allt gick bra och blev rätt
                        System.out.println("Plats " + ledigPlatsIndex + " (" + platsTyp + ") bokad för person med personnummer " + personnummer + ".");
                        
                        
                    }

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
                    System.out.println("Skriv in erat personnummer tack:(ååååmmdd) ");
                    int pnummer = input.nextInt();
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
                    System.out.println("Ange ert personnummer tack: (ååååmmdd)");
                    int hittap= input.nextInt();
                    hittaplats(hittap);
                    break;                            
                    
                default:
                    System.out.println("Välj mellan 1, 2, 3, 4 och 5 tack.");
                    break;
                    
            }
            
        }}catch(Exception e){
                    System.out.println("Jag förstod inte riktigt, snälla försök igen");                           
                    }
    }

    static int bokningV(int[] fält) {
        //den här metoden kommer vara det som tar in personnumret som sedan  kommer användas i bokningen
        Scanner input = new Scanner(System.in);
        //med en forloop kan jag leta vart platserna inte har ett person nummer
        for (int i = 0; i < fält.length; i++) {
            if (fält[i] == 0) {
                System.out.println("Skriv in ditt personnummer (ÅÅÅÅMMDD):");
                int personnummer = input.nextInt();
                return personnummer;
            }
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
  //här kan jag med hjälp av fnstr sätta ut ett personnummer på rätt index beroende på om d vill ha en fönsterplats eller inte
    static int bokaPlts(boolean fnstr) {
        //Med indexen så kan jag säga till så att den hoppar till den typen av plats som söks, så att den bara kan boka innom just de indexerna som till hör t.ex fönsterplats
    int startIndex = fnstr ? 0 : 2;
    int endIndex = fnstr ? 1 : 3; 
    for (int i = startIndex; i <= endIndex; i++) {
        if (persfält[i] == 0) {
            return i;
        }
    }
    return bokaPlts(false);
}
    
    static void avBoka(int pnummer){
        //Med en forloop kollar jag om det som skrivits in matchar med något av personnummrerna i bokningarna.
        for(int i = 0; i< persfält.length;i++){
            if (pnummer == persfält[i]){
                //När en match hittas så nollställs den platsen och ett medelande skickas ut
                persfält[i] = 0;
                System.out.println("Du har nu avbokats från plats "+ i);
                break;
            }
        if(i>=20 && persfält[i]!= pnummer){
            //Om vi inte hittar en matchning så ska det medelas till användaren. För at medelandet inte ska skickas ut varje gång somloopen körks så är den i en ifsats som kollar hur många gånger splatser som kollats för en matchning
        System.out.println("Ingen bokning med personnummret "+pnummer+" har hittats");
        }
        }
    }
    
    
    static void mindre(){
        System.out.println("Passagerare under 18 år:");
        for(int i = 0; i < persfält.length;i++){  
            if (persfält[i]>20060000){
            System.out.println("Plats " + i + ": " + persfält[i]);   
            }
            else if(i==20 && persfält[i]==0){
                System.out.println("inga bokningar här än...");
            }
        }
    }
    
     static void större(){
        System.out.println("Passagerare över 18 år:");
        for(int i = 0; i < persfält.length;i++){  
            if (persfält[i]<20060000 && persfält[i]!=0){
    System.out.println("Plats " + i + ": " + persfält[i]);         
            }
            else if(i==20 && persfält[i]==0){
                System.out.println("Inga bokningar här än...");
            }
        }
    }
    
     
     
     static void hittaplats(int pnummer){
         for(int i = 0; i< persfält.length;i++){
            if (pnummer == persfält[i]){
                System.out.println("Du har plats "+ i);
                break;
            }
        if(i>=20 && persfält[i]!= pnummer){
            
                System.out.println("Ingen bokning med personnummret "+pnummer+" har hittats");
        }
        }
         
     }
     
     
     //Testar rekretion
      static double vinsten(int antal) {
    double nyVinst = 0;
    if (antal > 0) {
        if (persfält[antal - 1] != 0  && persfält[antal-1]<19540000) { 
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
            antal--;
            nyVinst = vinsten(antal); 
        }
    }
    return nyVinst;
}

    
}

    

