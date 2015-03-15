import java.lang.Math;
import java.util.*;
/**
 * Beschreiben Sie hier die Klasse Spielfeld.
 * 
 * @author A.C.Hinrihs 
 * @version 2015-02-22
 */
public class Spielfeld
{
    private int[][][] spielfeld= new int[3][3][3];
    private byte spielphase = 0;                    //Spielphase 0 = Setzen, Spielphase 1 = Ziehen,
    private byte spieler = 1;
    private int runde=1;
    private boolean steinEntfernen = false;

    /**
     * Konstruktor für die Klasse Spielfeld
     */
    public Spielfeld()
    {

    }

    /**
     * Generiert ein neues Spielfeld mit angegebenen Spezifikationen 
     * @param pSpielfeld aktuelle Spielsituation
     * @param pSpielphase Spielphase
     * @param pSpieler der Spieler der am Zug ist
     * @param pSteinEntfernen Ob ein Stein entfernt werden muss
     * @param pRunde die Runde
     */
    public Spielfeld(int[][][] pSpielfeld,byte pSpielphase,byte pSpieler,boolean pSteinEntfernen,int pRunde)
    {
        spielfeld = pSpielfeld;
        spielphase = pSpielphase;
        spieler = pSpieler;
        steinEntfernen = pSteinEntfernen;
        runde = pRunde;
    }

    /**
     * Gibt die Aktuelle Spielphase zurück
     * @return Spielphase
     */
    public byte getSpielphase()
    {
        return spielphase;
    }

    /**
     * Gibt den aktuellen Spieler zurueck
     * @return Spieler
     */
    public byte getSpieler()
    {
        return spieler;
    }

    /**
     * Gibt das Aktuelle Spielfeld als dreidimensionales Int-Array zurueck
     * @return Spielfeld
     */
    public int[][][] getSpielfeld()
    {
        return spielfeld;
    }

    /**
     * Gibt zurueck ob ein Stein entfernt werden darf
     * @return Boolean
     */
    public boolean getSteinEntfernen()
    {
        return steinEntfernen;
    }

    /**
     * Gibt die nummer der aktuelle Runde zurueck
     * @return Runde
     */
    public int getRunde(){
        return runde;
    }

    /**
     * Kopiert das Spielfeld
     * @return Kopie des Spielfeldes
     */
    public Spielfeld kopieren()
    {
        //Kopie des Spielfeld.Arrays erzeugen
        int[][][] neuesSpielfeld = new int[3][3][3];
        for(int x=0; x< neuesSpielfeld.length; x++){
            for(int y=0; y< neuesSpielfeld.length; y++){
                for(int z=0; z< neuesSpielfeld.length; z++){
                    neuesSpielfeld[x][y][z]=spielfeld[x][y][z];
                }
            }
        }

        Spielfeld result = new Spielfeld(neuesSpielfeld,spielphase,spieler,steinEntfernen,runde);
        return result;
    }

    /**
     * Zaehlt die Steine eines SPielers
     * @param aSpieler Spieler dessen Steine zu zaehlen sind
     * @return anzahl der Steine
     */
    public int zaehleSteine(byte aSpieler)
    {
        int result = 0;
        for(int x=0;x<spielfeld.length;x++){
            for(int y=0;y<spielfeld[x].length;y++){
                for(int z=0;z<spielfeld[x][y].length;z++){
                    if(spielfeld[x][y][z]==aSpieler){
                        result++;
                    }
                }   
            }
        }
        return result;
    }

    /**
     * Prueft ob das Angegebene Feld existiert
     * @param x
     * @param y
     * @param z
     * @return Warheitswert ob das Feld existiert
     */
    public static boolean feldExistiert(int x, int y, int z)
    {
        return (x<3)&&(x>=0)&&(y<3)&&(y>=0)&&(z<3)&&(z>=0)&&!((x==1)&&(y==1));
    }

    /**
     * Prueft ob der Angegebene Stei  in einer Muehle steht
     * @param x
     * @param y
     * @param z
     * @return Wahrheitswert, ob der Stein in einer Muehle steht
     */
    public boolean pruefeMuehle(int x, int y, int z)
    {
        if(y!=1){
            if((spielfeld[0][y][z]==spielfeld[1][y][z])&&(spielfeld[0][y][z]==spielfeld[2][y][z])&&(spielfeld[0][y][z]!=0))
                return true;
        }

        if(x!=1){
            if((spielfeld[x][0][z]==spielfeld[x][1][z])&&(spielfeld[x][0][z]==spielfeld[x][2][z])&&(spielfeld[x][0][z]!=0))
                return true;
        }

        if((x==1)||(y==1)){
            if((spielfeld[x][y][0]==spielfeld[x][y][1])&&(spielfeld[x][y][0]==spielfeld[x][y][2])&&(spielfeld[x][y][0]!=0))
                return true;
        }

        return false;
    }

    /**
     * Prueft ob der angegebene Stein sioch bewegen kann
     * @param x
     * @param y
     * @param z
     * @return Wahrheitswert, ob der Stein sich bewegen kann
     */
    public boolean pruefeZugMoeglichkeit(int x, int y, int z){
        if((feldExistiert(x+1,y,z)&&spielfeld[x+1][y][z]==0)||
        (feldExistiert(x-1,y,z)&&spielfeld[x-1][y][z]==0)||
        (feldExistiert(x,y+1,z)&&spielfeld[x][y+1][z]==0)||
        (feldExistiert(x,y-1,z)&&spielfeld[x][y-1][z]==0)||
        (feldExistiert(x,y,z+1)&&spielfeld[x][y][z+1]==0)||
        (feldExistiert(x,y,z-1)&&spielfeld[x][y][z-1]==0))
        {
            return true;
        }
        return false;
    }

    /**
     * Prueft welcher Spieler gewonnen hat, wenn einer gewonnen hat
     * @return Spieler der Gewonnen hat
     */
    public byte pruefeAufSieger()
    {
        if(zaehleSteine((byte)-1)<3){//SPieler -1 hat verloren, wenn er weniger als drei Steine hat
            return 1;
        }

        if(zaehleSteine((byte)1)<3){//Spieler 1 hat verloren, wenn er weniger als drei Steine hat
            return -1;
        }

        //wenn ein Spieler sich nicht mehr bewegen kann, hat er verloren
        boolean bewSPA = false; //Spieler1
        boolean bewSPB = false; //Spieler-1
        for(int x=0;x<spielfeld.length;x++){
            for(int y=0;y<spielfeld[x].length;y++){
                for(int z=0;z<spielfeld[x][y].length;z++){
                    switch(spielfeld[x][y][z]){
                        case 1 :bewSPA = bewSPA || pruefeZugMoeglichkeit(x,y,z); break;
                        case -1:bewSPB = bewSPB || pruefeZugMoeglichkeit(x,y,z); break;
                        default:break;
                    }
                }
            }
        }
        if(!bewSPA){
            return -1;  //Spieler 1 kann sich nicht mehr bewegen, SPieler -1 hat gewonnen
        }
        if(!bewSPB){
            return 1;   //Spieler -1 kann sich nicht mehr Bewegen, SPieler 1 hat gewonnen
        }
        return 0;
    }

    /**
     * prueft ob das Spiel zu ende ist
     * @return Wahrheitswert, ob das Spiel zu ende ist
     */
    public boolean pruefeAufEnde(){
        if(spielphase == 0){
            return false;
        }
        boolean ende = false;
        if(zaehleSteine((byte)-1)<3){//SPieler -1 hat verloren, wenn er weniger als drei Steine hat
            ende = true;
        }

        if(zaehleSteine((byte)1)<3){//Spieler 1 hat verloren, wenn er weniger als drei Steine hat
            ende = true;
        }

        //wenn ein Spieler sich nicht mehr bewegen kann, hat er verloren
        boolean bewSPA = false; //Spieler1
        boolean bewSPB = false; //Spieler-1
        for(int x=0;x<spielfeld.length;x++){
            for(int y=0;y<spielfeld[x].length;y++){
                for(int z=0;z<spielfeld[x][y].length;z++){
                    switch(spielfeld[x][y][z]){
                        case 1 :bewSPA = bewSPA || pruefeZugMoeglichkeit(x,y,z); break;
                        case -1:bewSPB = bewSPB || pruefeZugMoeglichkeit(x,y,z); break;
                        default:break;
                    }
                }
            }
        }
        ende = ende||(!bewSPA)||(!bewSPB);   //Wenn mindestens einer der Spieler sich nicht mehr bewegen kann, ist das Spiel vorbei

        if(ende){
            spieler = 0;//Ende erreicht
        }
        return ende;
    }
    
    /**
     * Prueft ob der Angegebene Zug legal ist
     * @param x
     * @param y
     * @param z
     * @param zielX
     * @param zielY
     * @param zielZ
     * @return Wahrheitswert, ob der Angegeben Zug Legal ist
     */
    public boolean zugLegal(int x,int y,int z,int zielX,int zielY,int zielZ){
    	return (zaehleSteine(spieler)==3)||//Der Spieler muss entweder süringen dürfen, oder
    		   (Math.abs(x-zielX)+Math.abs(y-zielY)<=1 && 
    		   		(Math.abs(z-zielZ)==0 || 
    		   			(Math.abs(x-zielX)+Math.abs(y-zielY)+Math.abs(z-zielZ)==1 &&
    		   			((x==1 && y!=1) || 
    		   			(x!=1 && y==1)))));
    }

    /**
     * Setzt eine  Stein auf die angegebene Koordinaten, sofern dies erlaubt ist
     * @param x
     * @param y
     * @param z
     * @result 0 bedeutet illegaler Zug, 1 bedutet zug erfolgreich, 2 bedeutet zug erfolgreich, muele gebildet
     */
    public byte setzeStein(int x, int y, int z)
    {
        if((spielphase==0)&&//wenn wir im moment in der Setz-Phase sind, ...
        (feldExistiert(x,y,z))&&//... das Feld existiert,...
        (spielfeld[x][y][z]==0)&&//...das Feld frei ist...
        (!steinEntfernen)){//... und falls eine MÃ¼hle fertig gestellt wurde, der Stein schon entfernt wurde
        	System.out.println("Spieler "+spieler+" setzt Stein auf "+x+","+y+","+z);
            spielfeld[x][y][z]=spieler;
            if(pruefeMuehle(x,y,z)){
                steinEntfernen=true;
                return 2; //Muehle erlangt
            }
            naechsterSpieler();
            return 1;
        }
        return 0; // Spiel ungÃ¼ltig
    }

    /**
     * Entfernt den Angegeben zug, sofern das erlaubt ist
     * @param x
     * @param y
     * @param z
     * @result 0 bedeutet illegaler Zug, 1 bedutet zug erfolgreich
     
     */
    public byte entferneStein(int x, int y, int z)
    {
        if((steinEntfernen)&&   //wenn im moment ein Stein entfernt werden darf,...
        (feldExistiert(x,y,z))&&//...das feld gÃ¼ltig ist,...
        (spielfeld[x][y][z]==-spieler)&&//...auf dem Feld ein Frmder Sten steht...
        (!pruefeMuehle(x, y, z))){//und dr Stein in keiner MÃ¼hle steht
        	System.out.println("Spieler "+spieler+" entfernt Stein auf "+x+","+y+","+z);
            spielfeld[x][y][z]=0;
            steinEntfernen = false;
            naechsterSpieler();
            return 1;
        }/*
        System.out.println("steinEntfernen: "+steinEntfernen);
        System.out.println("feldExistiert(x,y,z): "+feldExistiert(x,y,z));
        System.out.println("spielfeld[x][y][z]: "+spielfeld[x][y][z]);
        System.out.println("spieler: "+spieler);
        System.out.println("pruefeMuehle(x, y, z): "+pruefeMuehle(x, y, z));*/
        return 0; // Spiel ungÃ¼ltig
    }

    /**
     * Bewegt eine Stein germäß den uebergebenen Koordinaten, sofern dies erlaubt ist
     * @param x
     * @param y
     * @param z
     * @param zielX
     * @param zielY
     * @param zielZ
     * @result 0 bedeutet illegaler Zug, 1 bedutet zug erfolgreich, 2 bedeutet zug erfolgreich, muele gebildet
     */
    public byte bewegeStein(int x, int y, int z, int zielX, int zielY, int zielZ)
    {
        if((spieler != 0)&&//Das Spiel muss Laufen (es muss ein Spieler an der Reihe sein)
        !((x==zielX)&&(y==zielY)&&(z==zielZ))&&//...es muss sich um zwei unterschiedliche Felder Handeln ...
        (feldExistiert(x,y,z))&&
        (feldExistiert(zielX,zielY,zielZ))&&//... beide mÃ¼ssen Existieren...
        (spielphase==1)&&//...wir dÃ¼rfen uns nichtmehr in der setz-phase befinden...
        (zugLegal(x,y,z,zielX,zielY,zielZ))&&//... der Zug muss legal sein,...
        (spielfeld[x][y][z]==spieler)&&//... der Stein der bewegt wird muss dem Spieler der am Zug ist gehÃ¶ren...
        (spielfeld[zielX][zielY][zielZ]==0)&&//... das Zielfeld muss frei sein...
        (!steinEntfernen)){//... und falls eine MÃ¼hle fertig geworden ist wurde der Stein schon entfernt
        	System.out.println("Spieler "+spieler+" bewegt Stein von "+x+","+y+","+z+" nach "+zielX+","+zielY+","+zielZ);
            spielfeld[x][y][z]=0;
            spielfeld[zielX][zielY][zielZ]=spieler;
            if(pruefeMuehle(zielX,zielY,zielZ)){
                steinEntfernen=true;
                return 2; //Muehle erlangt
            }
            naechsterSpieler();
            return 1;
        }
        //System.out.println(feldExistiert(x,y,z)+" "+feldExistiert(zielX,zielY,zielZ));
        return 0; // Spielzug ungÃ¼ltig
    }


    public boolean equals(Object obj)
    {
    	//if( (Arrays.equals(spielfeld,((Spielfeld)obj).getSpielfeld())))
    	//	System.out.println("GLEICH!");
        return (Arrays.deepEquals(spielfeld,((Spielfeld)obj).getSpielfeld()))&&
        (spielphase==((Spielfeld)obj).getSpielphase())&&
        (spieler==((Spielfeld)obj).getSpieler())&&
        (steinEntfernen==((Spielfeld)obj).getSteinEntfernen());
    }

    /**
     * Fuert die heuristik fuer die aktuelle Situation durch
     * @return bewertung
     */
    public int bewerte(){
        //HEURISTIK
        double ergebniss = 0;

        for(int z = 0; z< spielfeld[0][0].length; z++)
        {
            //Bewertung der Steine auf den "Ringen"
            ergebniss = ergebniss + Math.pow((spielfeld[0][0][z]+spielfeld[1][0][z]+spielfeld[2][0][z]), 3);
            ergebniss = ergebniss + Math.pow((spielfeld[0][2][z]+spielfeld[1][2][z]+spielfeld[2][2][z]), 3);
            ergebniss = ergebniss + Math.pow((spielfeld[0][0][z]+spielfeld[0][1][z]+spielfeld[0][2][z]), 3);
            ergebniss = ergebniss + Math.pow((spielfeld[0][0][z]+spielfeld[2][1][z]+spielfeld[2][2][z]), 3);
        }
        //Bewertung der Steine auf den "Geraden"
        ergebniss = ergebniss + Math.pow((spielfeld[1][0][0]+spielfeld[1][0][1]+spielfeld[1][0][2]), 3);
        ergebniss = ergebniss + Math.pow((spielfeld[1][2][0]+spielfeld[1][2][1]+spielfeld[1][2][2]), 3);
        ergebniss = ergebniss + Math.pow((spielfeld[0][1][0]+spielfeld[0][1][1]+spielfeld[0][1][2]), 3);
        ergebniss = ergebniss + Math.pow((spielfeld[2][1][0]+spielfeld[2][1][1]+spielfeld[2][1][2]), 3);
        return (int)Math.round(ergebniss);
    }
    
    /**
     * Aktuallisiert den Spieler, und ggf. die Rundenzahl
     */
    private void naechsterSpieler(){
    	spieler = (byte)-spieler;
        if(spieler==1){
            runde++;    //Ist der erste Spieler wieder am Zug, wurde eine neue Runde begonnen
            if(runde > 9){
            	spielphase = 1;
            }
        }
    	pruefeAufEnde();
    	
    }

}
