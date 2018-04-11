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

package pmachine;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.event.ListDataListener;

/**
 * @author ybo
 * 
 * \brief Functionalities for being
 * 
 */
public abstract class DisplayServer implements IListDisplayServer {

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.IListDisplayServer#asDisplayableVector()
	 */
	public abstract Vector asDisplayableVector();

	public abstract void notifyRemoval(int x, int y, int z,
			ListDataListener listener);

	private List dataListeners = new Vector();

	/**
	 * Sends a notification to all dataListeners that all elements between 0 and
	 * j (inclusive) have been removed.
	 * 
	 * @param i
	 * @param j
	 */
	public void notifyRemoval(int x, int y, int z) {
		Iterator iter = dataListeners.iterator();
		while (iter.hasNext()) {
			ListDataListener listener = (ListDataListener) iter.next();
			notifyRemoval(x, y, z, listener);
		}
	}

	public abstract void notifyAdd(int i, int j, ListDataListener listener);

	public void notifyAdd(int i, int j) {
		Iterator iter = dataListeners.iterator();
		while (iter.hasNext()) {
			ListDataListener listener = (ListDataListener) iter.next();
			notifyAdd(i, j, listener);
		}
	}

	public abstract void notifyChange(int beginIndx, int endIndx,
			ListDataListener listener);

	public void notifyChange(int beginIdx, int endIdx) {
		Iterator iter = dataListeners.iterator();
		while (iter.hasNext()) {
			ListDataListener l = (ListDataListener) iter.next();
			notifyChange(beginIdx, endIdx, l);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
	 */
	public void removeListDataListener(ListDataListener l) {
		dataListeners.remove(l);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	public abstract int getSize();

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public abstract Object getElementAt(int index);

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
	 */
	public void addListDataListener(ListDataListener l) {
		dataListeners.add(l);
	}

	public void removeAllListDataListener() {
		dataListeners.clear();
	}

}
