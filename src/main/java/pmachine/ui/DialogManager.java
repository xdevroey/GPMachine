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

import java.awt.Component;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pmachine.ExecutionEnvironment;
import pmachine.Messages;

public class DialogManager {

	private static JFileChooser fileChooser = new JFileChooser();

	public static void displayGPL() {
		JOptionPane.showMessageDialog(null, Messages
				.getString("PMachineJUI.PMachineName")
				+ " "
				+ ExecutionEnvironment.getVersion()
				+ Messages.getString("PMachineJUI.CopyrightStatement"));
	}

	public static int askNumberOfSteps(Component parent) throws CancelException {
		boolean correctInput = false;
		int result = 1;
		while (!correctInput) {
			String value = JOptionPane.showInputDialog(parent, Messages
					.getString("PMachineJUI.AskNumberOfStepsDialog"),
					new Integer(1));
			if (value == null) {
				throw new CancelException(Messages
						.getString("PMachineJUI.AskNumberOfStepsCanceled"));
			} else {
				try {
					result = Integer.parseInt(value);
					if (result <= 0) {
						JOptionPane
								.showMessageDialog(
										parent,
										result
												+ Messages
														.getString("PMachineJUI.AskNumberOfStepsNotPositiveInt"),
										Messages
												.getString("PMachineJUI.InputError"),
										JOptionPane.ERROR_MESSAGE);
					} else {
						correctInput = true;
					}
				} catch (NumberFormatException e) {
					JOptionPane
							.showMessageDialog(
									parent,
									value
											+ Messages
													.getString("PMachineJUI.AskNumberOfStepsNotValideFormattedInt"),
									Messages
											.getString("PMachineJUI.InputError"),
									JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		return result;
	}

	public static String askChooseFile(Component parent) throws CancelException {
		int returnVal = fileChooser.showOpenDialog(parent);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				return fileChooser.getSelectedFile().getCanonicalPath();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(parent, e.getMessage(), Messages
						.getString("PMachineJUI.AskChooseFileError"),
						JOptionPane.ERROR_MESSAGE);
				throw new CancelException(
						Messages
								.getString("PMachineJUI.AskChooseFileErrorFileSelection"));
			}
		} else {
			throw new CancelException(Messages
					.getString("PMachineJUI.AskChooseFileCanceled"));
		}
	}

}
