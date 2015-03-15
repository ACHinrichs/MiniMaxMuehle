import java.util.*;
/**
 * Der Spielbaum hat als Inhaltsobjekt das Spielfeld gespeichert
 * Die Klasse Spielbaum stellt gleichzeitig auch die Koten dar,
 * das erste Kind ist im Knoten referenziert genau wie der Nächste Bruder.
 * 
 * 
 * @author A. C. Hinrichs
 * @version 2015-02-22
 */
public class Spielbaum
{
    private Spielfeld spielfeld;
    private Spielbaum bruder;
    private Spielbaum erstesKind;
    private int kosten;

    /**
     * Konstruktor für den Spielbaum ohne Parameter
     */
    public Spielbaum(){
        bruder=null;
        erstesKind=null;
    }

    /**
     * Konstruktor für den SPielbaum mit Parametern
     * 
     * @param aSpielfeld: das SPielfeld, das in dem Knoten gespeichert werden soll
     */
    public Spielbaum(Spielfeld aSpielfeld){
        bruder=null;
        erstesKind=null;
        spielfeld = aSpielfeld;
    }

    /**
     * Gibt den naechsten Bruder des Knotens zurück
     * @return Naechster bruder des Knotens
     */
    public Spielbaum getNeachstenBruder(){
        return bruder;
    }

    /**
     * Setzt den naechsten Bruder des Knotens
     * @param aBruder naechster Bruder des Knotens
     */
    public void setBruder(Spielbaum aBruder){
        bruder = aBruder;
    }

    /**
     * Gibt das erste Kind des aktuellen Knotens zurueck, die anderen kinder koennen ueber dessen methode {@link getNaechstenBruder()} verwendet werden 
     * @return Erstes Kind des Knotens
     */
    public Spielbaum getErstesKind(){
        return erstesKind;
    }

    /**
     * Gibt das SPielfeld das in dem Knoten gespeichert ist zur�ck
     * @return Spielfed
     */
    public Spielfeld getSpielfeld(){
        return spielfeld;
    }

    /**
     * Gibt die Berechneten kosten des Knotens zur�ck
     * @return Kosten
     */
    public int getKosten(){
        return kosten;
    }

    /**
     * Sucht nach einer Bestimmten Spielsituation
     * @param finden Die zu suchende SPielsituation
     * @return der Knoten, in welchem die Situation gefunden wurde
     */
    public Spielbaum finde(Spielfeld finden)
    {
        if(spielfeld.equals(finden)){
            return this;
        }

        Queue zubesuchen = new Queue();
        Spielbaum aktNode = erstesKind;

        while((aktNode!=null)&&(!finden.equals(aktNode.spielfeld))){
            Spielbaum aktKind = aktNode.erstesKind;
            while(aktKind != null){
                zubesuchen.enqueue(aktKind);
                aktKind = aktKind.getNeachstenBruder();
            }
            aktNode = (Spielbaum)zubesuchen.front();
            zubesuchen.dequeue();
        }

        return aktNode;
    }

    /**
     * Fuert rekursief den MiniMax-Algorythmus aus
     * @param tiefe die Maximale Rekursionstiefe des Algorythmusses
     * @return Die Kosten des Knotens
     */
    public int Minimax(int tiefe){
        if(spielfeld.pruefeAufEnde()){
            kosten = Integer.MAX_VALUE*(spielfeld.pruefeAufSieger());//Festlegen dew Wertes, entweder -Inf, +Inf oder 0
        }else if(tiefe == 0){
            kosten = spielfeld.bewerte();//Heuristik
        }else{
            if(erstesKind == null){//Sollten keine Kinder vorhanden sein, werden sie generiert
                generiereKinder();
            }
            Spielbaum aktKind = erstesKind;
            kosten = 0;
            while(aktKind != null){
                int kindKosten = aktKind.Minimax(tiefe-1);
                if(kindKosten*aktKind.getSpielfeld().getSpieler()>kosten){
                    kosten=kindKosten;
                }
                aktKind = aktKind.getNeachstenBruder();
            }
            //System.out.println("KNOTEN AUF EBENE "+tiefe+"BEWERTET");
        }
        return kosten;
    }

    /**
     * Generiert alle m�glichen nachfolgenden Zuege
     */
    public void generiereKinder(){
        int[][][] spielfeldArray = spielfeld.getSpielfeld();
        List kinder = new List();
        int obereGrenzeZ = spielfeldArray[0][0].length; //zur optimierung
        boolean feldGerollt = true;
        
        for(int x = 0; x<spielfeldArray.length;x++){
            for(int y = 0; y<spielfeldArray[x].length;y++){
                feldGerollt = feldGerollt && (spielfeldArray[x][y][0] == spielfeldArray[x][y][2]);
            }
        }
        if(feldGerollt){
        	obereGrenzeZ=1;
        }

        for(int x = 0; x<spielfeldArray.length; x++){
            for(int y = 0; y<spielfeldArray[x].length; y++){
                for(int z = 0; z<obereGrenzeZ; z++){
                    if(spielfeld.feldExistiert(x,y,z)){
                        if(spielfeld.getSpielphase()==0){
                            if(spielfeldArray[x][y][z]==0){

                                Spielbaum kind = new Spielbaum(spielfeld.kopieren());
                                byte ergebniss = kind.getSpielfeld().setzeStein(x,y,z);
                                if(ergebniss == 2){
                                    kinder.concat(generiereEntfernen(kind.getSpielfeld()));
                                }else if(ergebniss == 0){//FEHLER!
                                    System.out.println("FEHLER in Spielbaum.generiereKinder(), Spielfeld.setzeStein() gibt 0 zurueck");
                                }else{
                                    kinder.append(kind);
                                }
                            }
                        }else if(spielfeldArray[x][y][z]==spielfeld.getSpieler()){
                            if(spielfeld.zaehleSteine(spielfeld.getSpieler())>3){
                                int[][] zugrichtung = {{x-1,y,z},{x+1,y,z},{x,y-1,z},{x,y+1,z},{x,y,z-1},{x,y,z+1}};   //Definition aller potentiell möglichen zugrichtungen
                                for(int i = 0; i<zugrichtung.length;i++){
                                    if(Spielfeld.feldExistiert(zugrichtung[i][0], zugrichtung[i][1], zugrichtung[i][2])&&
                                    		spielfeldArray[zugrichtung[i][0]][zugrichtung[i][1]][zugrichtung[i][2]]==0&&
                                    		spielfeld.zugLegal(x, y, z, zugrichtung[i][0], zugrichtung[i][1], zugrichtung[i][2])){
                                        Spielbaum kind = new Spielbaum(spielfeld.kopieren());
                                        byte ergebniss = kind.getSpielfeld().bewegeStein(x,y,z,zugrichtung[i][0],zugrichtung[i][1],zugrichtung[i][2]);
                                        if(ergebniss == 2){
                                            kinder.concat(generiereEntfernen(kind.getSpielfeld()));
                                        }else if(ergebniss == 0){//FEHLER!
                                            System.out.println("FEHLER in Spielbaum.generiereKinder(), Spielfeld.bewegeStein() gibt 0 zurueck");
                                        }else{
                                            kinder.append(kind);
                                        }
                                        //kinder.append(kind);
                                    }
                                }
                            }else{
                                //Spieler kann überall hin springen
                                for(int zielX = 0; zielX<spielfeldArray.length;zielX++){
                                    for(int zielY = 0; zielY<spielfeldArray[x].length;zielY++){
                                        for(int zielZ = 0; zielZ<spielfeldArray[x][y].length;zielZ++){
                                            if(spielfeldArray[zielX][zielY][zielZ]==0&&spielfeldArray[x][y][z]==spielfeld.getSpieler()&&spielfeld.feldExistiert(zielX,zielY, zielZ)){
                                                Spielbaum kind = new Spielbaum(spielfeld.kopieren());
                                                byte ergebniss = kind.getSpielfeld().bewegeStein(x,y,z,zielX,zielY,zielZ);
                                                if(ergebniss == 2){
                                                    kinder.concat(generiereEntfernen(kind.getSpielfeld()));
                                                }else if(ergebniss == 0){//FEHLER!
                                                    System.out.println("FEHLER in Spielbaum.generiereKinder(), Spielfeld.bewegeStein() -spielphase springen- gibt 0 zurueck");
                                                }else{
                                                    kinder.append(kind);
                                                }
                                                //kinder.append(kind);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //Kinder aus der Liste in den Baum überführen
        kinder.toFirst();
        //erstesKind = aktKind;
        while(kinder.hasAccess()){
            Spielbaum aktKind = erstesKind;
            erstesKind = (Spielbaum)kinder.getObject();
            erstesKind.setBruder(aktKind);
            kinder.next();
        }
    }

    /**
     * generiert eine Liste aller moeglichkleiten einen Gegnerischen Stein zu entfernen
     * @param pSpielfeld die Situation, in welcher ein Stein entfernt werden soll
     * @return Liste der moeglichen Entfernungen
     */
    public static List generiereEntfernen(Spielfeld pSpielfeld){
        int[][][] spielfeldArray = pSpielfeld.getSpielfeld();
        List entfernt = new List();
        for(int x = 0; x<spielfeldArray.length; x++){
            for(int y = 0; y<spielfeldArray[x].length; y++){
                for(int z = 0; z<spielfeldArray[x][y].length; z++){
                    if(Spielfeld.feldExistiert(x,y,z)&&spielfeldArray[x][y][z] == -pSpielfeld.getSpieler()&&!pSpielfeld.pruefeMuehle(x, y, z)){//Wenn das feld existiert, und der gegner drauf steht,
                        Spielbaum kind = new Spielbaum(pSpielfeld.kopieren());
                        byte ergebniss = kind.getSpielfeld().entferneStein(x,y,z);
                        if(ergebniss ==0){//FEHLER!
                            System.out.println("FEHLER in Spielbaum.generiereEntfernen()");
                        }
                        entfernt.append(kind);
                    }
                }
            }
        }
        return entfernt;
    }
}
