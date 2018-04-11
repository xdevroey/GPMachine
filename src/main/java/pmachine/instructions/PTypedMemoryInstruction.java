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

package pmachine.instructions;

import pmachine.IPMemory;
import pmachine.Messages;
import pmachine.StackElem;
import pmachine.StackElemAddr;
import pmachine.exceptions.StackException;
import pmachine.exceptions.WrongTypeException;

/**
 * @author ybo
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class PTypedMemoryInstruction extends PTypedInstruction {

	public PTypedMemoryInstruction(int type) {
		super(type);
	}

	protected StackElemAddr base(IPMemory ps, int p, int a)
			throws WrongTypeException, StackException {
		if (p == 0) {
			return (new StackElemAddr(a));
		} else {
			StackElem s = ps.elemAt(new StackElemAddr(a + 1));
			if (s instanceof StackElemAddr) {
				return (base(ps, p - 1, ((StackElemAddr) s).intValue()));
			} else {
				throw new WrongTypeException(Messages
						.getString("PMachine.BASE_TypeError"));
			}
		}
	}
}
