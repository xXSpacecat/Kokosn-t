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
    static int[] persfält = new int[20];
    static boolean fnstr = false;

    public static void main(String[] args) {
        //denna säger till så att vi kan stänga av hela grejen, den tas i funktion vid case tre
        boolean avstngd = false;
        
        //tänkte vara lite praktisk i mina namngivningar den här gången då det *kanske* kan ses som mer praktiskt
        Scanner input = new Scanner(System.in);

        while (!avstngd) {
            // Då det är ett val typ av system så va det bara logiskt att använda ett switch case här för att sedan leda de till sina respektive metoder
            System.out.println("Vad vill du göra?\n(1) Boka \n(2) Lista bokade \n(3) Avboka \n(4) Stäng av");

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
                        
                        //Eftersom att jag är lat å bestämde jag mig att alla jämna index är fönsterplatser och ojämna index är mittplatser vilket jag kan räkna uy med hjälp av modulo eller vad det nu hette
                        if (ledigPlatsIndex % 2 == 0) {
                            platsTyp = fnstr ? "fönsterplats" : "gångplats";
                        }
                        persfält[ledigPlatsIndex] = personnummer;
                        
                        //Det här är en snabb lite konfirmation på bokningen så att jag kan se så att allt gick bra och blev rätt
                        System.out.println("Plats " + ledigPlatsIndex + " (" + platsTyp + ") bokad för person med personnummer " + personnummer + ".");
                    }

                    break;
                case 2:
                    //Här så kommer alla personer att listas ut  med hjälp av den vanliga for loopen
                    for (int i = 0; i < persfält.length; i++) {
                        if (persfält[i] != 0) {
                            System.out.println("Plats " + i + ": " + persfält[i]);
                        }
                    }

                    break;
                case 3:
                    System.out.println("Skriv in erat personnummer tack: ");
                    int pnummer = input.nextInt();
                    avBoka(pnummer);
                    break;
                case 4:
                    //jag ville bara ha en avstängningsfunktion så jag satte in allt i en while loop som det här tredje caset får hantera
                    avstngd = true;
                    break;
                default:
                    System.out.println("Välj mellan 1, 2 och 3 tack.");
                    break;
            }
        }
    }

    static int bokningV(int[] fält) {
        //den här metoden kommer vara det som tar in personnumret som sedan  kommer användas i bokningen
        Scanner input = new Scanner(System.in);

        for (int i = 0; i < fält.length; i++) {
            if (fält[i] == 0) {
                System.out.println("Skriv in ditt personnummer (ÅÅMMDD):");
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
                // Här kan jag placera först kolla vilka platser som är lediga sedan också ta in om det ska vara en fönsterplats eller inte så att alla hamnar rätt där i kommer att vara indexen i fältet
                return i;
            }
        }
    }

    // Detta menas att bussen är full
    return -1;
}
  //här kan jag med hjälp av fnstr sätta ut ett personnummer på rätt index beroende på om d vill ha en fönsterplats eller inte
    static int bokaPlts(boolean fnstr) {
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
        for(int i = 0; i< persfält.length;i++){
            if (pnummer == persfält[i]){
                persfält[i] = 0;
                System.out.println("Du har nu avbokats från plats "+ i);
                break;
            }
        if(i>=19 && persfält[i]!= 0){
        System.out.println("Ingen bokning med personnummret "+pnummer+" har hittats");
        }
        }
    }
}

    

