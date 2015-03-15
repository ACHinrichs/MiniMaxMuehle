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
import java.util.Arrays;
/**
 * GUI f�r das M�hle-Spiel
 * @author A. C. Hinrichs
 * @version 2015-03-15
 */
public class GUI extends JFrame {

	private Spielfeld spielfeld;
	private Computergegner gegner;
	private GrafischesSpielfeld grafischesSpielfeld;
	private JLabel lblAusgabe;
	private JLabel lblSpielinfo;
	private JPanel panel;
	private JButton btnComputerBeginnt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					/*
					System.out.println((new Spielfeld(new int[][][] {	{	{0, 0, 0},
						{0, 0, 0}, 
						{0, 0, 0}}, 
					{	{0, 0, 0},
						{0, 0, 0},
						{0, 0, 0}},
					{	{0, 0, 1},
						{0, 0, 1},
						{0, 0, 1}}},(byte)0,(byte)0,false,0)).bewerte());
					System.out.println((new Spielfeld(new int[][][] {	{	{0, 0, 0},
						{0, 0, 0}, 
						{0, 0, 0}}, 
					{	{0, 0, 0},
						{0, 0, 0},
						{0, 0, 0}},
					{	{0, 0, 0},
						{0, 0, 0},
						{1, 1, 1}}},(byte)0,(byte)0,false,0)).bewerte());
						*/
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
		getContentPane().setLayout(
				new FormLayout(new ColumnSpec[] {
						FormFactory.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),
						FormFactory.DEFAULT_COLSPEC, }, new RowSpec[] {
						FormFactory.RELATED_GAP_ROWSPEC,
						FormFactory.DEFAULT_ROWSPEC,
						FormFactory.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"), }));

		grafischesSpielfeld = new GrafischesSpielfeld(spielfeld);
		grafischesSpielfeld.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent arg0) {
				int[] position = grafischesSpielfeld.bestimmeFeldUnterCursor(
						arg0.getX(), arg0.getY());
				if (arg0.getButton() == 1) {
					grafischesSpielfeld.setMarkiert1(position);
				} else if (arg0.getButton() == 3) {
					grafischesSpielfeld.setMarkiert2(position);
				}
				GUI.this.repaint();
			}
		});

		lblSpielinfo = new JLabel("Runde 1 - Weiss ist am Zug");
		getContentPane().add(lblSpielinfo, "2, 2");

		lblAusgabe = new JLabel("");
		getContentPane().add(lblAusgabe, "3, 2");
		getContentPane().add(grafischesSpielfeld, "2, 4, fill, fill");

		panel = new JPanel();
		getContentPane().add(panel, "3, 4, right, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC, }, new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC, FormFactory.DEFAULT_ROWSPEC, }));
		
				JButton btnSetzen = new JButton("Setzen");
				btnSetzen.setToolTipText("Das Feld mit der linken Maustaste w\u00E4hlen");
				btnSetzen.addActionListener(new ActionListener() {
					public void actionPerformed(final ActionEvent arg0) {
						int[] markiert1 = grafischesSpielfeld.getMarkiert1();
						int ergebniss = spielfeld.setzeStein(markiert1[0],
								markiert1[1], markiert1[2]);
						updateGUI();
						if (ergebniss == 0) {
							lblAusgabe.setText("AKTION VERBOTEN!");
						} else if (ergebniss == 1) {
							lblAusgabe.setText("Aktion Erfolgreich!");
							computerzug();
						} else if (ergebniss == 2) {
							lblAusgabe.setText("M�hle Geschlossen!");
						}
					}
				});
				panel.add(btnSetzen, "2, 2");
		
				JButton btnBewegen = new JButton("Bewegen");
				btnBewegen.setToolTipText("Den Stein der Bewegt werden soll mit der linken Maustaste ausw\u00E4hlen, das Zielfeld mit der Rechten");
				btnBewegen.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int[] markiert1 = grafischesSpielfeld.getMarkiert1();
						int[] markiert2 = grafischesSpielfeld.getMarkiert2();
						int ergebniss = spielfeld.bewegeStein(markiert1[0],
								markiert1[1], markiert1[2], markiert2[0], markiert2[1],
								markiert2[2]);
						updateGUI();
						if (ergebniss == 0) {
							lblAusgabe.setText("AKTION VERBOTEN!");
						} else if (ergebniss == 1) {
							lblAusgabe.setText("Bewegung Erfolgreich!");
							computerzug();
						} else if (ergebniss == 2) {
							lblAusgabe.setText("M�hle Geschlossen!");
						}
					}
				});
				panel.add(btnBewegen, "2, 4");
						
								JButton btnEntfernen = new JButton("Entfernen");
								btnEntfernen.setToolTipText("Den Stein mit der Linken Maustaste w\u00E4hlen");
								btnEntfernen.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										int[] markiert1 = grafischesSpielfeld.getMarkiert1();
										int ergebniss = spielfeld.entferneStein(markiert1[0],
												markiert1[1], markiert1[2]);
										updateGUI();
										if (ergebniss == 0) {
											lblAusgabe.setText("AKTION VERBOTEN!");
										} else if (ergebniss == 1) {
											lblAusgabe.setText("Aktion Erfolgreich!");
											computerzug();
										}
									}
								});
								panel.add(btnEntfernen, "2, 6");
								
								btnComputerBeginnt = new JButton("Computer Beginnt");
								btnComputerBeginnt.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {
										btnComputerBeginnt.setEnabled(false);
										computerzug();
									}
								});
								panel.add(btnComputerBeginnt, "2, 8");

	}

	/**
	 * Die Methode sorgt daf�r, das der Computer zieht
	 */
	private void computerzug() {
		if(spielfeld.pruefeAufEnde()){
			panel.setEnabled(false);
			return;
		}
		long dauer = gegner.Ziehen(spielfeld);
		lblAusgabe.setText("Computerzug in "
				+ dauer / 1000 + "s");
		if(spielfeld.pruefeAufEnde()){
			panel.setEnabled(false);
		}
		updateGUI();
	}

	/**
	 * Aktuallisiert die texte in der GUI
	 */
	private void updateGUI() {
		String text = "Runde " + spielfeld.getRunde() + " - ";
		switch (spielfeld.getSpieler()) {
		case 1:
			text = text + "Weiss ist am Zug";
			break;
		case -1:
			text = text + "Schwarz ist am Zug";
			break;
		default:
			text = text + "Spiel beendet - Sieger ist ";
			switch (spielfeld.pruefeAufSieger()) {
			case 1:
				text = text + "WEISS";
				break;
			case -1:
				text = text + "SCHWARZ";
				break;
			default:
				text = text + "UNBEKANNT";
				break;
			}
			break;

		}
		lblSpielinfo.setText(text);
		repaint();
		revalidate();
	}
}
