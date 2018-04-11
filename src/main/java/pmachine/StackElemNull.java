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

/*
 * StackElemNull.java
 *
 * Created on 3 d�cembre 2002, 10:20
 */

package pmachine;

import pmachine.exceptions.WrongTypeException;

/**
 * 
 * @author ybo
 */
public class StackElemNull extends pmachine.StackElem {

	/** Creates a new instance of StackElemNull */
	public StackElemNull() {
		value = new String(Messages.getString("StackElemNull.UndefineElemName"));
	}

	public StackElemNull(String s) {
		value = Messages.getString("StackElemNull.UndefineElemName") + " "
				+ Messages.getString("PStack.TextOutput13") + s
				+ Messages.getString("PStack.TextOutput14");
	}

	protected long compareValues(StackElem el) throws WrongTypeException {
		throw new WrongTypeException(Messages
				.getString("StackElemNull.UndefineElemComparing"));
	}

	public String printType() {
		return new String("");
	}
}
