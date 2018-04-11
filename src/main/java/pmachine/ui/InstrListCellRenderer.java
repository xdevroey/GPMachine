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

package pmachine.ui;

import java.awt.Component;
import java.util.Set;

import javax.swing.JList;

import pmachine.ExecutionEnvironment;
import pmachine.PMachine;

/**
 * 
 * @author ybo
 */
public class InstrListCellRenderer extends javax.swing.DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;
	private java.util.Set breakpoints;
	private PMachine pm;

	/** Creates a new instance of BreakpointListCellRenderer */
	public InstrListCellRenderer(Set breakpoints, PMachine pm) {
		this.breakpoints = breakpoints;
		this.pm = pm;
	}

	private String getIndexRepresentation(int index) {
		int codeLength = pm.getProgramMemory().getSize();
		// maxIndexLength = 3, si codeLength 562
		int maxIndexLength = ("" + codeLength).length();

		String indexAsString = "" + index;
		for (int i = 0; i < maxIndexLength - ("" + index).length(); i++) {
			indexAsString = "0" + indexAsString;
		}
		return indexAsString;
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		// Example of representation 75(index=PC): P-Code, index begins with 1
		// (index+1)
		String representation = getIndexRepresentation(index + 1) + ":   "
				+ value.toString();
		// String representation = index+": "+value.toString();
		Component c = super.getListCellRendererComponent(list, representation,
				index, isSelected, cellHasFocus);
		int PC = pm.getPC();
		if (breakpoints.contains(new Integer(index))) {
			if (index == PC)
				c
						.setBackground(ExecutionEnvironment
								.getBreakPointCursorColor());
			else
				c.setBackground(ExecutionEnvironment.getBreakPointColor());
		} else if (index == PC) {
			c.setBackground(ExecutionEnvironment.getPCColor());
		}
		return c;
	}
}
