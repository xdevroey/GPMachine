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

import javax.swing.JOptionPane;

import pmachine.Messages;
import pmachine.PReader;
import pmachine.StackElemInt;
import pmachine.exceptions.WrongTypeException;

public class JReader implements PReader {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.PReader#read()
	 */
	public StackElemInt read() throws WrongTypeException {
		String res;
		try {
			int result = 0;
			boolean correctInput = false;
			while (!correctInput) {
				try {
					res = JOptionPane.showInputDialog(Messages
							.getString("PMachineJUI.PromptIntegerInput"));
					result = Integer.parseInt(res);
					correctInput = true;
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, Messages
							.getString("PMachineJUI.BadIntFormat"), Messages
							.getString("PMachineJUI.InputError"),
							JOptionPane.ERROR_MESSAGE);
				}
			}
			return new StackElemInt(result);
		}

		catch (NumberFormatException e) {
			throw new WrongTypeException(Messages
					.getString("PMachineJUI.InputNotIntegerError"));
		} catch (NullPointerException e) {
			throw new WrongTypeException(Messages
					.getString("PMachineJUI.EmptyInputError"));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1); /* What's this error, headless ?? */
			throw new WrongTypeException(Messages
					.getString("PMachineJUI.HeadlessExceptionError"));
		}
	}
}
