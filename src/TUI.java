
/**
 * Einfaches TexUI
 * 
 * @author A.C.Hinrichs
 * @version 2015-02-22
 */
public class TUI
{
    public Spielfeld spielfeld;
    //public Spielbaum spielbaum;
    public Computergegner gegner;

    public TUI()
    {
        spielfeld = new Spielfeld();
        gegner = new Computergegner(spielfeld);
        //spielbaum = new Spielbaum();
    }

    public void SpielfeldAusgeben(){
        System.out.println("MUEHLE=====RUNDE: "+spielfeld.getRunde()+"=====Computer: X==Spieler: #"+"=====Spieler: "+spielfeld.getSpieler());
        /*
        0-----0-----0
        |
        | 0---0---0
        | |   |
        | | 0-0-0
        | | |
        0-0-0
        |
        |
        |
        |
        |
        |
        0-----0-----0
         */
        switch(spielfeld.getSpielfeld()[0][0][0]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("-----");
        switch(spielfeld.getSpielfeld()[1][0][0]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("-----");
        switch(spielfeld.getSpielfeld()[2][0][0]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.println();
        System.out.println("|     |     |");
        System.out.print("| ");
        
        switch(spielfeld.getSpielfeld()[0][0][1]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("---");
        switch(spielfeld.getSpielfeld()[1][0][1]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("---");
        switch(spielfeld.getSpielfeld()[2][0][1]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.println(" |");
        System.out.println("| |   |   | |");
        
        System.out.print("| | ");
        switch(spielfeld.getSpielfeld()[0][0][2]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("-");
        switch(spielfeld.getSpielfeld()[1][0][2]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("-");
        switch(spielfeld.getSpielfeld()[1][0][2]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.println(" | |");
        
        
        System.out.println("| | |   | | |");
        switch(spielfeld.getSpielfeld()[0][1][0]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("-");
        switch(spielfeld.getSpielfeld()[0][1][1]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("-");
        switch(spielfeld.getSpielfeld()[0][1][2]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("   ");
        switch(spielfeld.getSpielfeld()[2][1][2]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("-");
        switch(spielfeld.getSpielfeld()[2][1][1]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("-");
        switch(spielfeld.getSpielfeld()[2][1][0]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.println();
        
        
        System.out.println("| | |   | | |");
        
        System.out.print("| | ");
        switch(spielfeld.getSpielfeld()[0][2][2]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("-");
        switch(spielfeld.getSpielfeld()[1][2][2]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("-");
        switch(spielfeld.getSpielfeld()[1][2][2]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.println(" | |");
        
        
        System.out.println("| |   |   | |");
        System.out.print("| ");
        switch(spielfeld.getSpielfeld()[0][2][1]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("---");
        switch(spielfeld.getSpielfeld()[1][2][1]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("---");
        switch(spielfeld.getSpielfeld()[2][2][1]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        //System.out.print("--");
        System.out.println(" |");
        
        System.out.println("|     |     |");
        switch(spielfeld.getSpielfeld()[0][2][0]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("-----");
        switch(spielfeld.getSpielfeld()[1][2][0]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.print("-----");
        switch(spielfeld.getSpielfeld()[2][2][0]){
            case -1: System.out.print("X"); break;
            case 1: System.out.print("#"); break;
            default: System.out.print("0");break;
        }
        System.out.println();
    }
    
    public void computerzug(){
gegner.Ziehen(spielfeld);
    }
}
