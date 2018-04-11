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

import pmachine.exceptions.WrongTypeException;

/**
 * @author ybo
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class StackElemPCAddr extends StackElem {

	/**
	 * @see pmachine.StackElem#printType()
	 */
	public String printType() {
		return new String(Messages.getString("StackElemPCAddr.TypeName"));
	}

	public StackElemPCAddr(int val) {
		value = new Integer(val);
	}

	public int intValue() {
		return ((Integer) value).intValue();
	}

	/**
	 * @see pmachine.StackElem#compareValues(StackElem)
	 */
	protected long compareValues(StackElem el) throws WrongTypeException {
		if (!(el instanceof StackElemPCAddr)) {
			throw new WrongTypeException(Messages
					.getString("StackElemPCAddr.TypeComparisonError"));
		}
		return intValue() - ((StackElemPCAddr) el).intValue();
	}

}
