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

import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class StackDisplayServer extends DisplayServer {

	private Stack stack;
	private IPMemory memoryOwner;

	public StackDisplayServer(Stack stack, IPMemory memory) {
		this.stack = stack;
		this.memoryOwner = memory;
	}

	/**
	 * Returns a Vector representation of the stack's content. All StackElem are
	 * converted to their String representation. This Vector is upside-down: its
	 * first element is the top of the stack.
	 */
	public Vector asDisplayableVector() {
		Vector res = new Vector();
		Iterator it = this.stack.iterator();
		int i = 0;

		while (it.hasNext()) {
			res.add(0, Messages.getString("PStack.TextOutput10") + i
					+ Messages.getString("PStack.TextOutput11")
					+ it.next().toString());
			i++;
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Object getElementAt(int index) {
		try {
			return stack.get(indexUpsideDown(index));
		} catch (ArrayIndexOutOfBoundsException e) {
			// Return Null Stack Element, because of unsynchronization between 2
			// threads (main and PMachine) the stack can be changed be PMachine
			// thread
			return new StackElemNull();
		}
	}

	// TODO delete it
	/*
	 * returns the content of the stack, as a String.
	 */
	/*
	 * public String contents() { int i; String
	 * outp=Messages.getString("PStack.TextOutput1")+ (new
	 * Integer(memoryOwner.getMP())).toString() +
	 * Messages.getString("PStack.TextOutput2") + (new
	 * Integer(memoryOwner.getSP())).toString() +
	 * Messages.getString("PStack.TextOutput3")+memoryOwner.getEP()+Messages.getString("PStack.TextOutput4");
	 * //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ try{ for(i=0; i<=memoryOwner.getSP();
	 * ++i) { outp = outp + Messages.getString("PStack.TextOutput5")+ (new
	 * Integer(i)).toString()+Messages.getString("PStack.TextOutput6")+
	 * stack.get(i).toString() + Messages.getString("PStack.TextOutput7");
	 * //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ } outp = outp +
	 * Messages.getString("PStack.TextOutput8"); //$NON-NLS-1$ } catch
	 * (Exception e){ /** Should never occur
	 */
	/*
	 * System.err.println(Messages.getString("PStack.StackExceptionOutput"));
	 * //$NON-NLS-1$ System.err.println(e.getMessage()+"\n"); //$NON-NLS-1$
	 * System.exit(1); } return(outp); }
	 */
	/*
	 * returns the content of the stack, as a String, in a short form.
	 */
	/*
	 * public String shortContents() { int i; String outp
	 * =Messages.getString("PStack.TextOutput9"); //$NON-NLS-1$ try{
	 * for(i=memoryOwner.getSP();i>=0;--i) { outp = outp +
	 * Messages.getString("PStack.TextOutput10") + String.valueOf(i) +
	 * Messages.getString("PStack.TextOutput11") + stack.get(i).toString() +
	 * "\n"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ } outp +=
	 * Messages.getString("PStack.TextOutput12"); //$NON-NLS-1$ } catch
	 * (Exception e){ /** Should never occur
	 */
	/*
	 * System.err.println(Messages.getString("PStack.StackExceptionOutputShort"));
	 * //$NON-NLS-1$ System.err.println(e.getMessage()+"\n"); //$NON-NLS-1$
	 * System.exit(1); } return(outp); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return stack.size();
	}

	private int indexUpsideDown(int index, int size) {
		return (size - 1) - index;
	}

	private int indexUpsideDown(int index) {
		return indexUpsideDown(index, getSize());
	}

	public void notifyRemoval(int oldSize, int i, int j,
			ListDataListener listener) {
		listener.intervalRemoved(new ListDataEvent(this,
				ListDataEvent.INTERVAL_REMOVED, indexUpsideDown(j, oldSize),
				indexUpsideDown(i, oldSize)));
	}

	public void notifyAdd(int i, int j, ListDataListener listener) {
		listener.intervalAdded(new ListDataEvent(this,
				ListDataEvent.INTERVAL_ADDED, indexUpsideDown(j),
				indexUpsideDown(i)));
	}

	public void notifyChange(int beginIdx, int endIdx, ListDataListener listener) {
		listener.contentsChanged(new ListDataEvent(this,
				ListDataEvent.CONTENTS_CHANGED, indexUpsideDown(endIdx),
				indexUpsideDown(beginIdx)));
	}

}
