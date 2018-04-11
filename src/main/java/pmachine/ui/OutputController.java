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
 * TODO Comment this class
 */

package pmachine.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import pmachine.Messages;
import pmachine.StackElemInt;

public class OutputController implements IOutputController {

	private JPanel itsVisualPart;
	private JTextArea outputArea;
	private IStateController parent;

	public OutputController(IStateController superior) {
		outputArea = new JTextArea();
		outputArea.setEditable(false);
		outputArea.setFont(new Font("Roman", Font.BOLD, 12));
		itsVisualPart = new JPanel();
		itsVisualPart.setLayout(new BorderLayout());
		JScrollPane scrollPane = new JScrollPane(outputArea);
		JLabel titleLabel = new JLabel(Messages
				.getString("PMachineJUI.OutputMenuName"));
		titleLabel.setFont(new Font("Dialog", Font.BOLD, 14));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		itsVisualPart.add(scrollPane, BorderLayout.CENTER);
		itsVisualPart.add(titleLabel, BorderLayout.NORTH);
		parent = superior;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IController#getComponent()
	 */
	public Component getComponent() {
		return itsVisualPart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.PWriter#write(pmachine.StackElemInt)
	 */
	public void write(StackElemInt data) {
		outputArea.setText(outputArea.getText() + data.toString() + "\n");
	}

	/**
	 * This method write a message in output(GUI) : errors and p-code outputs
	 */
	public void write(String data) {
		outputArea.setText(outputArea.getText() + data + "\n");
	}

}
