import java.awt.*;
import javax.swing.*;
/**
 * Beschreiben Sie hier die Klasse GrafischesSpiefeld.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class GrafischesSpielfeld extends JPanel
{
    private Spielfeld spielfeld;
    private int[] markiert1 = {-1,-1,-1};
    private int[] markiert2 = {-1,-1,-1};

    public GrafischesSpielfeld(Spielfeld s)
    {
        spielfeld = s;
    }

    public void paintComponent(Graphics g)
    {
    	super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        g.clearRect(0,0,width,height);
        int[][][] spielfeldArray = spielfeld.getSpielfeld();
        g.setColor(Color.black);
        g.drawRect(width/2-width/3,height/2-height/3,2*width/3,2*height/3);
        g.drawRect(width/2-width/9*2,height/2-height/9*2,2*width/9*2,2*height/9*2);
        g.drawRect(width/2-width/9,height/2-height/9,2*width/9,2*height/9);

        g.drawLine(width/2,height/2+height/3,width/2,height/2+height/9);
        g.drawLine(width/2,height/2-height/3,width/2,height/2-height/9);
        g.drawLine(width/2+width/3,height/2,width/2+width/9,height/2);
        g.drawLine(width/2-width/3,height/2,width/2-width/9,height/2);
        for(int x=0;x<spielfeldArray.length;x++){
            for(int y=0;y<spielfeldArray[x].length;y++){
                if(!(x==1&&y==1)){
                    for(int z=0;z<spielfeldArray[x][y].length;z++){
                        if(spielfeldArray[x][y][z]==0){
                            g.setColor(Color.black);
                            g.fillRect(width/2+(x-1)*width/9*(3-z)-5, height/2+(y-1)*height/9*(3-z)-5,10, 10);
                        }else 
                        {
                            if(spielfeldArray[x][y][z]==1){
                                g.setColor(Color.white);
                            }else {
                                g.setColor(Color.black);
                            }
                            g.fillOval(width/2+(x-1)*width/9*(3-z)-10, height/2+(y-1)*height/9*(3-z)-10,20, 20);
                            g.setColor(Color.black);
                            g.drawOval(width/2+(x-1)*width/9*(3-z)-10, height/2+(y-1)*height/9*(3-z)-10,20, 20);
                        }
                    }
                }
            }
        }
        if(markiert1[0]!=-1&&markiert1[1]!=-1&&markiert1[2]!=-1){
            g.setColor(Color.blue);
            g.drawOval(width/2+(markiert1[0]-1)*width/9*(3-markiert1[2])-10, height/2+(markiert1[1]-1)*height/9*(3-markiert1[2])-10,20, 20);
        }
        
        if(markiert2[0]!=-1&&markiert2[1]!=-1&&markiert2[2]!=-1){
            g.setColor(Color.green);
            g.drawOval(width/2+(markiert2[0]-1)*width/9*(3-markiert2[2])-10, height/2+(markiert2[1]-1)*height/9*(3-markiert2[2])-10,20, 20);
        }
    }

    public int[] bestimmeFeldUnterCursor(int xPos,int yPos){
        int[] ergebniss = {-1,-1,-1};      
        int width = getWidth();
        int height = getHeight();
        int[][][] spielfeldArray = spielfeld.getSpielfeld();

        for(int x=0;x<spielfeldArray.length;x++){
            for(int y=0;y<spielfeldArray[x].length;y++){
                if(!(x==1&&y==1)){
                    for(int z=0;z<spielfeldArray[x][y].length;z++){
                        //System.out.println((width/2+(x-1)*width/9*(3-z))+"_"+(height/2+(y-1)*height/9*(3-z)));
                        if(xPos>width/2+(x-1)*width/9*(3-z)-10&&
                        xPos<width/2+(x-1)*width/9*(3-z)+10&&
                        yPos>height/2+(y-1)*height/9*(3-z)-10&&
                        yPos<height/2+(y-1)*height/9*(3-z)+10){
                            ergebniss[0]=x;
                            ergebniss[1]=y;
                            ergebniss[2]=z;
                        }
                    }
                }
            }
        }
        return ergebniss;
    }

    public void setMarkiert1(int[] a){
    	markiert1 = a;
    }
    
    public int[] getMarkiert1(){
    	return markiert1;
    }
    
    public void setMarkiert2(int[] a){
    	markiert2 = a;
    }

    public int[] getMarkiert2(){
    	return markiert2;
    }
}
