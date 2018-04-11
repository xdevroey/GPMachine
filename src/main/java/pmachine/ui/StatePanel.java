/*
This is "gpmachine" a PCode interpreter, by Yiti Group, rewritten by Yves Bontemps
Copyright (C) 2002-2004  Yves Bontemps
Copyright (C) 2006 Khvalenski Andrew
Copyright (C) 2004-2008 Hubert Toussaint
 
This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 
Contact:
 Hubert Toussaint
 CS Dept - University of Namur
 rue Grandgagnage, 21
 B5000 Namur
 Belgium
 gpm@info.fundp.ac.be
 */

/**
 * @author ybo
 *

 import javax.swing.JPanel;
 * TODO Comment this class
 */

package pmachine.ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import pmachine.ExecutionEnvironment;
import pmachine.Messages;

public class StatePanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public void setPCDisplayValue(int value) {
		getPcTextField().setText("" + value);
	}

	public void setSPDisplayValue(int value) {
		getSpTextField().setText("" + value);
	}

	public void setMPDisplayValue(int value) {
		getMpTextField().setText("" + value);
	}

	public void setEPDisplayValue(int value) {
		getEpTextField().setText("" + value);
	}

	/**
	 * Follow the index in the code viewport
	 * 
	 * @param list
	 * @param index
	 */
	public void ensureIndexIsVisible(JList list, int index) {
		list.ensureIndexIsVisible(index);
	}

	private JScrollPane jScrollPane = null;
	private JList stackList = null;
	private JScrollPane jScrollPane1 = null;
	private JList heapList = null;
	private JScrollPane jScrollPane2 = null;
	private JList codeList = null;

	private JPanel stackPanel = null;
	private JPanel codePanel = null;
	private JPanel heapPanel = null;
	private JPanel registersPanel = null;
	private JPanel jPanel = null;
	private JPanel jPanel1 = null;
	private JPanel jPanel2 = null;
	private JPanel jPanel3 = null;
	private JTextField pcTextField = null;
	private JPanel jPanel4 = null;
	private JTextField spTextField = null;
	private JTextField epTextField = null;
	private JTextField mpTextField = null;

	private JSplitPane leftJSP = null;
	private JSplitPane centerJSP = null;
	private JSplitPane rightJSP = null;

	/**
	 * This is the default constructor
	 */
	public StatePanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setLayout(new GridLayout(1, 1, 20, 10));
		leftJSP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false,
				getStackPanel(), getCodePanel());
		rightJSP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false,
				getHeapPanel(), getRegistersPanel());
		centerJSP = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, leftJSP,
				rightJSP);
		this.add(centerJSP);

		resize(800, 500);
		rightJSP.setResizeWeight(0.60);
	}

	/**
	 * This method ajuste grids dimentions
	 * 
	 */
	public void resize(int height, int width) {
		leftJSP.setDividerLocation(120);
		leftJSP.updateUI();
		centerJSP.setDividerLocation(height - 280);
	}

	/**
	 * This method initializes jScrollPane
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane() {
		if (jScrollPane == null) {
			jScrollPane = new JScrollPane();
			jScrollPane.setViewportView(getStackList());
		}
		return jScrollPane;
	}

	/**
	 * This method initializes stackList
	 * 
	 * @return javax.swing.JList
	 */
	public JList getStackList() {
		if (stackList == null) {
			stackList = new JList();
			MouseListener mouseListener = new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 1) {
						heapList.clearSelection();
					}
				}
			};
			stackList.addMouseListener(mouseListener);
		}
		return stackList;
	}

	/**
	 * This method initializes jScrollPane1
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane1() {
		if (jScrollPane1 == null) {
			jScrollPane1 = new JScrollPane();
			jScrollPane1.setViewportView(getHeapList());
		}
		return jScrollPane1;
	}

	/**
	 * This method initializes heapList
	 * 
	 * @return javax.swing.JList
	 */
	public JList getHeapList() {
		if (heapList == null) {
			heapList = new JList();
			MouseListener mouseListener = new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 1) {
						stackList.clearSelection();
					}
				}
			};
			heapList.addMouseListener(mouseListener);
		}
		return heapList;
	}

	/**
	 * This method initializes jScrollPane2
	 * 
	 * @return javax.swing.JScrollPane
	 */
	private JScrollPane getJScrollPane2() {
		if (jScrollPane2 == null) {
			jScrollPane2 = new JScrollPane();
			jScrollPane2.setViewportView(getCodeList());
		}
		return jScrollPane2;
	}

	/**
	 * This method initializes codeList
	 * 
	 * @return javax.swing.JList
	 */
	public JList getCodeList() {
		if (codeList == null) {
			codeList = new JList();
		}
		return codeList;
	}

	/**
	 * This method initializes stackPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getStackPanel() {
		if (stackPanel == null) {
			javax.swing.JLabel jLabel = new JLabel();
			stackPanel = new JPanel();
			stackPanel.setLayout(new BorderLayout());
			jLabel.setText(Messages.getString("PMachineJUI.StackMenuName"));
			jLabel.setFont(new java.awt.Font("Dialog", java.awt.Font.BOLD, 14));
			jLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
			stackPanel.add(jLabel, java.awt.BorderLayout.NORTH);
			stackPanel.add(getJScrollPane(), BorderLayout.CENTER);
		}
		return stackPanel;
	}

	/**
	 * This method initializes codePanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getCodePanel() {
		if (codePanel == null) {
			javax.swing.JLabel jLabel1 = new JLabel();
			codePanel = new JPanel();
			codePanel.setLayout(new BorderLayout());
			jLabel1.setText(Messages.getString("PMachineJUI.PCodeMenuName"));
			jLabel1.setFont(new Font("Dialog", Font.BOLD, 14));
			jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel1.setHorizontalTextPosition(SwingConstants.CENTER);
			codePanel.add(jLabel1, BorderLayout.NORTH);
			codePanel.add(getJScrollPane2(), BorderLayout.CENTER);
		}
		return codePanel;
	}

	/**
	 * This method initializes heapPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getHeapPanel() {
		if (heapPanel == null) {
			javax.swing.JLabel jLabel2 = new JLabel();
			heapPanel = new JPanel();
			jLabel2.setText(Messages.getString("PMachineJUI.HeapMenuName"));
			jLabel2.setFont(new Font("Dialog", Font.BOLD, 14));
			jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
			heapPanel.setLayout(new BorderLayout());
			heapPanel.add(jLabel2, BorderLayout.NORTH);
			heapPanel.add(getJScrollPane1(), BorderLayout.CENTER);
		}
		return heapPanel;
	}

	/**
	 * This method initializes registersPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getRegistersPanel() {
		if (registersPanel == null) {
			javax.swing.JLabel jLabel4 = new JLabel();
			registersPanel = new JPanel();
			jLabel4
					.setText(Messages
							.getString("PMachineJUI.RegistersMenuName"));
			jLabel4.setFont(new Font("Dialog", Font.BOLD, 14));
			jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
			jLabel4.setHorizontalTextPosition(SwingConstants.CENTER);
			registersPanel.setLayout(new BorderLayout());
			registersPanel.add(jLabel4, BorderLayout.NORTH);
			registersPanel.add(getJPanel4(), BorderLayout.CENTER);
		}
		return registersPanel;
	}

	/**
	 * This method initializes jPanel
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel() {
		if (jPanel == null) {
			javax.swing.JLabel jLabel3 = new JLabel();
			jPanel = new JPanel();
			jLabel3.setText(Messages.getString("PMachineJUI.PCMenuName"));
			jPanel.add(jLabel3, null);
			jPanel.add(getPcTextField(), null);
		}
		return jPanel;
	}

	/**
	 * This method initializes jPanel1
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel1() {
		if (jPanel1 == null) {
			javax.swing.JLabel jLabel5 = new JLabel();
			jPanel1 = new JPanel();
			jLabel5.setText(Messages.getString("PMachineJUI.SPMenuName"));
			jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
			jPanel1.add(jLabel5, null);
			jPanel1.add(getSpTextField(), null);
		}
		return jPanel1;
	}

	/**
	 * This method initializes jPanel2
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel2() {
		if (jPanel2 == null) {
			javax.swing.JLabel jLabel6 = new JLabel();
			jPanel2 = new JPanel();
			jLabel6.setText(Messages.getString("PMachineJUI.EPMenuName"));
			jPanel2.add(jLabel6, null);
			jPanel2.add(getEpTextField(), null);
		}
		return jPanel2;
	}

	/**
	 * This method initializes jPanel3
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel3() {
		if (jPanel3 == null) {
			javax.swing.JLabel jLabel7 = new JLabel();
			jPanel3 = new JPanel();
			jLabel7.setText(Messages.getString("PMachineJUI.MPMenuName"));
			// jLabel7.setForeground(ExecutionEnvironment.getMPColor());
			jPanel3.add(jLabel7, null);
			jPanel3.add(getMpTextField(), null);
		}
		return jPanel3;
	}

	/**
	 * This method initializes pcTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getPcTextField() {
		if (pcTextField == null) {
			pcTextField = new JTextField();
			pcTextField.setEditable(false);
			pcTextField.setColumns(4);
			pcTextField.setToolTipText(Messages
					.getString("PMachineJUI.PCTipText"));
			pcTextField.setBackground(ExecutionEnvironment.getPCColor());
		}
		return pcTextField;
	}

	/**
	 * This method initializes jPanel4
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel4() {
		if (jPanel4 == null) {
			jPanel4 = new JPanel();
			jPanel4.setLayout(new BoxLayout(jPanel4, BoxLayout.Y_AXIS));
			jPanel4.add(getJPanel(), null);
			jPanel4.add(getJPanel1(), null);
			jPanel4.add(getJPanel2(), null);
			jPanel4.add(getJPanel3(), null);
		}
		return jPanel4;
	}

	/**
	 * This method initializes spTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getSpTextField() {
		if (spTextField == null) {
			spTextField = new JTextField();
			spTextField.setEditable(false);
			spTextField.setColumns(4);
			spTextField.setToolTipText(Messages
					.getString("PMachineJUI.SPTipText"));
		}
		return spTextField;
	}

	/**
	 * This method initializes epTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getEpTextField() {
		if (epTextField == null) {
			epTextField = new JTextField();
			epTextField.setEditable(false);
			epTextField.setColumns(4);
			epTextField.setToolTipText(Messages
					.getString("PMachineJUI.EPTipText"));
		}
		return epTextField;
	}

	/**
	 * This method initializes mpTextField
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getMpTextField() {
		if (mpTextField == null) {
			mpTextField = new JTextField();
			mpTextField.setEditable(false);
			mpTextField.setColumns(4);
			mpTextField.setToolTipText(Messages
					.getString("PMachineJUI.MPTipText"));
			mpTextField.setBackground(ExecutionEnvironment.getMPColor());
		}
		return mpTextField;
	}

}
