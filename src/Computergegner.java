
/**
 * Beschreiben Sie hier die Klasse Computergegner.
 * 
 * @author A.C.Hinrichs 
 * @version 2015-02-22
 */
public class Computergegner
{
    public int rekursionstiefe = 4;
    
    private Spielbaum spielbaum;
    
    /**
     * Konstruktor für die Klasse Computergegner
     * @param aSpielfeld das Spielfeld mit welchem der Spielbaum initialisiert wird
     */
    public Computergegner(Spielfeld aSpielfeld)
    {
        spielbaum = new Spielbaum(aSpielfeld.kopieren());
    }

    /**
     * Diese Methode veranlasst den Computer seinen zug zu berechnen und auf die Situation anzuwenden
     * Ausserdem Variert der Computer die Rekursionstiefe für den nächsten Zug, je nach Zeit die zur Berechnung benötigt wurde
     * @param aktuelleSituation Das Spielfeld wie es im moment ist
     * @return					Die Zeit, welche der Computer zum ziehen gebraucht hat
     */
    public long Ziehen(Spielfeld aktuelleSituation){
        spielbaum = spielbaum.finde(aktuelleSituation);//spielbaum auf aktuelle situation setzen
        
        if(spielbaum == null){
            spielbaum = new Spielbaum(aktuelleSituation.kopieren());
            System.out.println("Spielfeld musste neu angelegt werden");
        }

		long startzeit = System.currentTimeMillis();
        //ZÃ¼ge berechnen;
        spielbaum.Minimax(rekursionstiefe);
        long dauer = System.currentTimeMillis()-startzeit;
        
        //Besten zug suchen;
        Spielbaum besterZug = spielbaum.getErstesKind();
        
        Spielbaum aktZug = spielbaum.getErstesKind();
        while(!(aktZug == null)){
            if(aktZug.getKosten()>besterZug.getKosten()){
                besterZug = aktZug;
            }
            aktZug = aktZug.getNeachstenBruder();
        }
        
        //Zug herausfinden;
        int startX = -1;
        int startY = -1;
        int startZ = -1;
        int zielX = -1;
        int zielY = -1;
        int zielZ = -1;
        int entferneX = -1;
        int entferneY = -1;
        int entferneZ = -1;
        
        int[][][] spielfeldArrayBesterZug = besterZug.getSpielfeld().getSpielfeld();
        int[][][] spielfeldArrayAktuell = aktuelleSituation.getSpielfeld();
        List kinder = new List();
        for(int x = 0; x<spielfeldArrayBesterZug.length; x++){
            for(int y = 0; y<spielfeldArrayBesterZug[x].length; y++){
                for(int z = 0; z<spielfeldArrayBesterZug[x][y].length; z++){
                    if(spielfeldArrayAktuell[x][y][z]==aktuelleSituation.getSpieler()&&spielfeldArrayBesterZug[x][y][z]==0){
                        startX = x;
                        startY = y;
                        startZ = z;
                    }else if(spielfeldArrayBesterZug[x][y][z]==aktuelleSituation.getSpieler()&&spielfeldArrayAktuell[x][y][z]==0){
                        zielX = x;
                        zielY = y;
                        zielZ = z;
                    }else if(spielfeldArrayBesterZug[x][y][z]==0&&spielfeldArrayAktuell[x][y][z]==-aktuelleSituation.getSpieler()){
                        entferneX = x;
                        entferneY = y;
                        entferneZ = z;
                    }
                }
            }
        }
        //aktuelleSituation;
        int ergebniss = -1;
        if(aktuelleSituation.getSpielphase()==0){
           ergebniss = aktuelleSituation.setzeStein(zielX, zielY, zielZ);
        }else{
            ergebniss = aktuelleSituation.bewegeStein(startX, startY, startZ, zielX, zielY, zielZ);
        }
        if(ergebniss == 0){
            System.out.println("FEHLER! im zug des Computers, das sollte nicht passieren!");
        }else
        if(ergebniss == -1){
            System.out.println("FEHLER! computerzug");
        }else if(ergebniss == 2){
            System.out.println("COMPUTER ENTFERNT STEIN");
            ergebniss = aktuelleSituation.entferneStein(entferneX, entferneY, entferneZ);
            if(ergebniss ==0){
                System.out.println("FEHLER! Der computer hat versucht einen Stein zu entfernen!");
            }
        }
        
        System.out.println("Computer ist gezogen");
        
        //Schließlich noch die Rekursionstiefe für den Nächsten zug anpassen
        if(dauer < 5000 && aktuelleSituation.getSpielphase()>0 && rekursionstiefe <5){
        	rekursionstiefe++;
        	System.out.println(rekursionstiefe);
        }else if(dauer > 30000 && rekursionstiefe > 3){
        	rekursionstiefe--;
        	System.out.println(rekursionstiefe);
        }
        
        return dauer;
    }
}
