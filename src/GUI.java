import java.awt.*;

import javax.swing.*;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class GUI extends JFrame {
	
	private Spielfeld spielfeld;
	private Computergegner gegner;
	private GrafischesSpielfeld grafischesSpielfeld;
	private JLabel lblAusgabe;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
	    spielfeld = new Spielfeld();
	    gegner = new Computergegner(spielfeld);
	    
	    
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 589, 300);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormFactory.DEFAULT_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		grafischesSpielfeld = new GrafischesSpielfeld(spielfeld);
		grafischesSpielfeld.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int[] position = grafischesSpielfeld.bestimmeFeldUnterCursor(arg0.getX(), arg0.getY());
				if(arg0.getButton()==1){
					grafischesSpielfeld.setMarkiert1(position);
				} else if(arg0.getButton()==3){
					grafischesSpielfeld.setMarkiert2(position);
				}
				GUI.this.repaint();
			}
		});
		getContentPane().add(grafischesSpielfeld, "2, 2, fill, fill");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "3, 2, right, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,}));
		
		JButton btnSetzen = new JButton("Setzen");
		btnSetzen.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent arg0) {
				int[] markiert1 =grafischesSpielfeld.getMarkiert1();
				int ergebniss = spielfeld.setzeStein(markiert1[0], markiert1[1], markiert1[2]);
				GUI.this.repaint();
				if(ergebniss == 0 ){
					lblAusgabe.setText("AKTION VERBOTEN!");
				} else if(ergebniss == 1 ){
					lblAusgabe.setText("Aktion Erfolgreich!");
					computerzug();
				} else if(ergebniss == 2 ){
					lblAusgabe.setText("Mühle Geschlossen!");
				}
			}
		});
		
		lblAusgabe = new JLabel("New label");
		panel.add(lblAusgabe, "2, 2");
		panel.add(btnSetzen, "2, 4");
		
		JButton btnBewegen = new JButton("Bewegen");
		btnBewegen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] markiert1 =grafischesSpielfeld.getMarkiert1();
				int[] markiert2 =grafischesSpielfeld.getMarkiert2();
				int ergebniss = spielfeld.bewegeStein(markiert1[0], markiert1[1], markiert1[2],markiert2[0],markiert2[1],markiert2[2]);
				GUI.this.repaint();
				if(ergebniss == 0 ){
					lblAusgabe.setText("AKTION VERBOTEN!");
				} else if(ergebniss == 1 ){
					lblAusgabe.setText("Bewegung Erfolgreich!");
					computerzug();
				} else if(ergebniss == 2 ){
					lblAusgabe.setText("Mühle Geschlossen!");
				}
			}
		});
		panel.add(btnBewegen, "2, 6");
		
		JButton btnEntfernen = new JButton("Entfernen");
		btnEntfernen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] markiert1 =grafischesSpielfeld.getMarkiert1();
				int ergebniss = spielfeld.entferneStein(markiert1[0], markiert1[1], markiert1[2]);
				GUI.this.repaint();
				if(ergebniss == 0 ){
					lblAusgabe.setText("AKTION VERBOTEN!");
				} else if(ergebniss == 1 ){
					lblAusgabe.setText("Aktion Erfolgreich!");
					computerzug();
				}
			}
		});
		panel.add(btnEntfernen, "2, 8");
		
	}
	
	private void computerzug(){
		long startzeit = System.currentTimeMillis();
		gegner.Ziehen(spielfeld);
		lblAusgabe.setText("Computerzug in "+(System.currentTimeMillis()-startzeit)/1000+"s");
		GUI.this.repaint();
	}

}
