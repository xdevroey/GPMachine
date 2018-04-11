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
import pmachine.StackElem;
import pmachine.exceptions.StackException;

/**
 * 
 * @author ybo
 */
public class StackListCellRenderer extends javax.swing.DefaultListCellRenderer {

	private static final long serialVersionUID = 1L;
	private StateController parent;
	private java.util.Set watchs;
	private PMachine pm;

	/** Creates a new instance of BreakpointListCellRenderer */
	public StackListCellRenderer(StateController parent, Set watchs, PMachine pm) {
		this.parent = parent;
		this.watchs = watchs;
		this.pm = pm;
	}

	private String getIndexRepresentation(int index) {
		String maxAddress = "" + pm.getSP();
		String indexAsString = "" + (pm.getSP() - index);
		if (index > pm.getSP()) {
			// you should never come here. => treads unsynchronization
			return indexAsString;
		}
		boolean aligned = maxAddress.length() == indexAsString.length();
		while (!aligned) {
			indexAsString = "0" + indexAsString;
			aligned = maxAddress.length() == indexAsString.length();
		}
		return indexAsString;
	}

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		String representation = getIndexRepresentation(index) + ": "
				+ value.toString();
		Component c = super.getListCellRendererComponent(list, representation,
				index, isSelected, cellHasFocus);
		// coloring watch's lines (watchs and last active watch)
		int MPidx = pm.getSP() - pm.getMP();
		if (index == MPidx) {
			c.setBackground(ExecutionEnvironment.getMPColor());
		}
		try {
			StackElem el = (StackElem) pm.getPMemory().elemAt(
					pm.getSP() - index);
			if (el.isCallCell()) {
				c.setBackground(ExecutionEnvironment.getCallFrameBackground());
			}
		} catch (StackException e) {
		} catch (ArrayIndexOutOfBoundsException e) {
		}
		if (watchs.contains(new Integer(pm.getSP() - index))) {
			c.setBackground(ExecutionEnvironment.getWatchColor());
			if (parent.isStackWatchTriggered()) {
				if (pm.getSP() - index == parent.getStackWatch())
					c.setBackground(ExecutionEnvironment.getWatchCursorColor());
			}
		}
		return c;
	}
}
