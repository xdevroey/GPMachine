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

package pmachine;

import java.util.List;
import java.util.Vector;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class HeapDisplayServer extends DisplayServer {

	List heap;
	IPMemory memoryOwner;

	public HeapDisplayServer(List h, IPMemory owner) {
		heap = h;
		memoryOwner = owner;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.IListDisplayServer#asDisplayableVector()
	 */
	public Vector asDisplayableVector() {
		Vector v = new Vector();
		int maxSize = heap.size();
		for (int i = 0; i < maxSize; i++) {
			StackElem elem = (StackElem) heap.get(i);
			v.add(Messages.getString("PStack.TextOutput10") + (maxSize - i)
					+ Messages.getString("PStack.TextOutput11") + elem);
		}
		return v;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.DisplayServer#notifyRemoval(int, int, int,
	 *      javax.swing.event.ListDataListener)
	 */
	public void notifyRemoval(int x, int y, int z, ListDataListener listener) {
		listener.intervalRemoved(new ListDataEvent(this,
				ListDataEvent.INTERVAL_REMOVED, y, z));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.DisplayServer#notifyAdd(int, int,
	 *      javax.swing.event.ListDataListener)
	 */
	public void notifyAdd(int i, int j, ListDataListener listener) {
		listener.intervalAdded(new ListDataEvent(this,
				ListDataEvent.INTERVAL_ADDED, i, j));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.DisplayServer#notifyChange(int, int,
	 *      javax.swing.event.ListDataListener)
	 */
	public void notifyChange(int beginIndx, int endIndx,
			ListDataListener listener) {
		listener.contentsChanged(new ListDataEvent(this,
				ListDataEvent.CONTENTS_CHANGED, beginIndx, endIndx));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.DisplayServer#getSize()
	 */
	public int getSize() {
		return heap.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.DisplayServer#getElementAt(int)
	 */
	public Object getElementAt(int index) {
		return heap.get(index);
	}

}
