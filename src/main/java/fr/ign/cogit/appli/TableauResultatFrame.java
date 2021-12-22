/**
 * 
 * This software is released under the licence CeCILL
 * 
 * see LICENSE.TXT
 * 
 * see <http://www.cecill.info/ http://www.cecill.info/
 * 
 * 
 * @copyright IGN
 * 
 * 
 */
package fr.ign.cogit.appli;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import fr.ign.cogit.appariement.LigneResultat;

/**
 * 
 * 
 * @author M-D Van Damme
 */
public class TableauResultatFrame implements ActionListener {

	JFrame frame = null;

	/** Button : close. */
	private JButton closeButton = null;

	/** Tab Panels. */
	JPanel buttonPanel = null;
	JPanel resultatPanel = null;

	List<LigneResultat> listeResultat;
	private JTable tableau;

	private static final String COL_NAME_CLE_REF = "ID ref";
	private static final String COL_NAME_CLE_COMP = "ID candidat";
	private static final String COL_NAME_NOM_REF = "Nom ref";
	private static final String COL_NAME_NOM_COMP = "Nom candidat";
	private static final String COL_NAME_URI_REF = "URI ref";
	private static final String COL_NAME_URI_COMP = "URI candidat";
	private static final String COL_NAME_PIGN_C1 = "Proba pign premier";
	private static final String COL_NAME_PIGN_C2 = "Proba pign second";
	private static final String COL_NAME_DECISION = "Decision";

	private Vector<Vector<?>> rowData = new Vector<Vector<?>>();
	private Vector<String> columnNames = new Vector<String>();
	
	public void displayEnsFrame(String title, List<LigneResultat> listeResultat) {
		this.listeResultat = listeResultat;

		frame = new JFrame(title);

		initResultat();
		initResultatPanel();
		initButtonPanel();

		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(resultatPanel, BorderLayout.CENTER);
		frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		frame.setVisible(true);
		frame.setLocation(250, 250);
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public void computeRes(List<LigneResultat> listeResultat) {
		this.listeResultat = listeResultat;
		initResultat();
		initResultatPanel();
		initButtonPanel();
	}
	
	public void setListeResultat(List<LigneResultat> listeResultat) {
		this.listeResultat = listeResultat;
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		Object source = evt.getSource();
		if (source == closeButton) {
			// close
			frame.dispose();
		}
	}

	private void initButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		buttonPanel.add(closeButton);
	}
	
	
	public void initResultat() {
		
		// Les données
		for (int i = 0; i < this.listeResultat.size(); i++) {

			Vector<Object> rowOne = new Vector<Object>();
			LigneResultat res = this.listeResultat.get(i);

			rowOne.addElement(res.getIdTopoRef());
			rowOne.addElement(res.getCompteurC());
			rowOne.addElement(res.getIdTopoComp());

			rowOne.addElement(res.getNomTopoRef());
			rowOne.addElement(res.getNomTopoComp());

			// Autres attributs
			rowOne.addElement(res.getUriTopoRef());
			rowOne.addElement(res.getUriTopoComp());

			for (int c = 0; c < this.listeResultat.get(i).getDistances().length; c++) {
				double d = this.listeResultat.get(i).getDistance(c);
				if (d < 0) {
					rowOne.addElement("");
				} else {
					rowOne.addElement(d);
				}
			}

			rowOne.addElement(res.getProbaPignistiquePremier());
			rowOne.addElement(res.getProbaPignistiqueSecond());
			rowOne.addElement(res.isDecision());

			rowData.add(rowOne);
		}
		
		// Les colonnes
		columnNames.addElement(COL_NAME_CLE_REF);
		columnNames.addElement("N°");
		columnNames.addElement(COL_NAME_CLE_COMP);

		columnNames.addElement(COL_NAME_NOM_REF);
		columnNames.addElement(COL_NAME_NOM_COMP);

		columnNames.addElement(COL_NAME_URI_REF);
		columnNames.addElement(COL_NAME_URI_COMP);

		for (int c = 0; c < this.listeResultat.get(0).getDistances().length; c++) {
			columnNames.addElement(this.listeResultat.get(0).getNomDistance(c));
		}

		columnNames.addElement(COL_NAME_PIGN_C1);
		columnNames.addElement(COL_NAME_PIGN_C2);
		columnNames.addElement(COL_NAME_DECISION);
		
	}

	
	private void initResultatPanel() {

		resultatPanel = new JPanel();
		resultatPanel.setLayout(new BorderLayout());

		tableau = new JTable(rowData, columnNames);
		tableau.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		if (rowData != null && rowData.size() > 0) {
			for (int l = 1; l < rowData.get(0).size(); l++) {
				tableau.getColumnModel().getColumn(l).setCellRenderer(new ColorRedRenderer());
			}
		}
		tableau.getColumnModel().getColumn(0).setCellRenderer(new TitreRenderer());

		resultatPanel.add(tableau.getTableHeader(), BorderLayout.NORTH);
		resultatPanel.add(new JScrollPane(tableau), BorderLayout.CENTER);
	}

	/**
	 * 
	 */
	public int[] analyse() {

		int nbApp = 0;
		int nbNA = 0;
		int nbIndecis = 0;

		// Toutes les lignes
		for (int i = 0; i < this.rowData.size(); i++) {
			boolean isNA = false;
			for (int j = 0; j < this.columnNames.size(); j++) {
			
				String colname = this.columnNames.get(j);
				
				if (rowData.elementAt(i).get(j) != null) {
					String val = rowData.elementAt(i).get(j).toString();
					// System.out.println(colname + " : " + val);

					if (colname.equals(COL_NAME_NOM_COMP)) {
						if (val.equals("NA")) {
							isNA = true;
						} else {
							isNA = false;
						}
					}

					if (colname.equals(COL_NAME_DECISION)) {
						if (isNA && val.equals("true")) {
							nbNA++;
						}
						if (!isNA && val.equals("true")) {
							nbApp++;
						}
						if (isNA && val.equals("indécis")) {
							nbIndecis++;
						}
					}

				}
			}
		}

		int[] tab = new int[3];
		tab[0] = nbNA;
		tab[1] = nbApp;
		tab[2] = nbIndecis;
		return tab;

	}

}

/**
 * 
 *
 */
class ColorRedRenderer extends DefaultTableCellRenderer {

	/** Default serial id. */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (value != null) {
			if (value.equals("")) {
				// setBackground(Color.RED);
			} else if (value.equals("true")) {
				for (int c = 0; c < table.getColumnCount(); c++) {
					super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, c)
							.setBackground(Color.GREEN);
					// System.out.println("Nb de colonnes = " + table.getColumnCount() + " - " + c);
				}
				// setBackground(Color.GREEN);
			} else if (value.equals("indécis")) {
				setBackground(Color.ORANGE);
			} else {
				setBackground(Color.WHITE);
			}
		} else {
			setBackground(Color.WHITE);
		}

		return this;
	}
}

class TitreRenderer extends DefaultTableCellRenderer {

	/** Default serial id. */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		setBackground(UIManager.getColor("Panel.background"));
		setForeground(Color.BLACK);

		return this;
	}

}
