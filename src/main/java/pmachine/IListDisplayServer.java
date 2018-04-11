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

import java.util.Vector;

import javax.swing.ListModel;

/**
 * @author ybo
 * 
 * This interface specifies the various services that are needed by ListDisplay.
 * Elements wanting to be displayed as a List must implement this interface.
 * 
 * This interface models a List.
 */
public interface IListDisplayServer extends ListModel {
	// Its value is the first address of the current local memory space.
	/**
	 * \return a Vector view of this.
	 */
	public abstract Vector asDisplayableVector();

	/**
	 * The List that this is modeling has had elements removed. Assume that the
	 * list, before removal, was l[0..n]. This method is to be used to specify
	 * that the interval [beginIndex..endIndex[ (i.e. endIndex exclusive) has
	 * been removed. oldSize is n+1, the size of the list before removal <br>
	 * It is necessary to specify the size of the list before removal, since the
	 * List might have been changed by other operations.
	 * 
	 * @param oldSize
	 *            the size of the List before removal.
	 * @param beginIndex
	 *            the start of the interval removed.
	 * @param endIndex
	 *            the removed interval ends at endIndex - 1.
	 */
	public abstract void notifyRemoval(int oldSize, int beginIndex, int endIndex);

	/**
	 * Elements from beginIndex to endIndex are new in the List. They have been
	 * added.
	 * 
	 * @param beginIndex
	 *            the index of the first newly added element
	 * @param endIndex
	 *            the end index (exclusive) of the added interval.
	 */
	public abstract void notifyAdd(int beginIndex, int endIndex);

	/**
	 * Elements in interval (beginIndex..endIndex - 1) have had their value
	 * changed. This method can also be called if elements are believed to have
	 * changed.
	 * 
	 * @param beginIndex
	 *            the index of the first element that has changed.
	 * @param endIndex
	 *            the end index (exclusive) of the added interval.
	 */
	public abstract void notifyChange(int beginIndex, int endIndex);

	public abstract void removeAllListDataListener();
}
