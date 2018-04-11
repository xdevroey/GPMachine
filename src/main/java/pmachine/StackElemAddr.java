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

import pmachine.exceptions.DivByZeroException;
import pmachine.exceptions.WrongTypeException;

/**
 * @author ybo
 * 
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class StackElemAddr extends StackElem {

	public String printType() {
		return Messages.getString("StackElemAddr.TypeName");
	}

	public StackElemAddr(int val) {
		value = new Integer(val);
	}

	public int intValue() {
		return ((Integer) value).intValue();
	}

	public Long getInteger() {
		return new Long(intValue());
	}

	/**
	 * @see pmachine.StackElem#compareValues(StackElem)
	 */
	protected long compareValues(StackElem el) throws WrongTypeException {
		if (!(el instanceof StackElemAddr)) {
			throw new WrongTypeException(Messages
					.getString("StackElemAddr.TypeComparisonError")); //$NON-NLS-1$
		}
		StackElemAddr ad = (StackElemAddr) el;
		return this.intValue() - ad.intValue();
	}

	private final static int ADD = 1;
	private final static int MINUS = 2;
	private final static int MULT = 3;
	private final static int DIV = 4;
	private final static int NEG = 5;

	private static StackElemAddr binOp(StackElem s1, StackElem s2, int op)
			throws WrongTypeException, DivByZeroException {
		if (s1 instanceof StackElemAddr && s2 instanceof StackElemAddr) {
			int val1 = ((StackElemAddr) s1).intValue();
			int val2 = ((StackElemAddr) s2).intValue();
			if (op == ADD)
				return (new StackElemAddr(val1 + val2));
			else if (op == MINUS)
				return (new StackElemAddr(val1 - val2));
			else if (op == MULT)
				return (new StackElemAddr(val1 * val2));
			else if (op == DIV) {
				if (val2 == 0)
					throw new DivByZeroException(Messages
							.getString("StackElemInt.DivByZero"));
				else
					return (new StackElemAddr(val1 / val2));
			} else if (op == NEG)
				return (new StackElemAddr(0 - val1));
			else
				return null; /* should never be here */
		} else {
			throw new WrongTypeException(Messages
					.getString("StackElemAddr.OperandsTypeError"));
		}
	}

	public static StackElem add(StackElem s1, StackElem s2)
			throws WrongTypeException {
		try {
			return ((StackElem) binOp(s1, s2, ADD));
		} catch (DivByZeroException e) {
			return null;
		}
	}

	public static StackElem sub(StackElem s1, StackElem s2)
			throws WrongTypeException {
		try {
			return ((StackElem) binOp(s1, s2, MINUS));
		} catch (DivByZeroException e) {
			return null;
		}
	}

	public static StackElem mult(StackElem s1, StackElem s2)
			throws WrongTypeException {
		try {
			return ((StackElem) binOp(s1, s2, MULT));
		} catch (DivByZeroException e) {
			return null;
		}
	}

	public static StackElem div(StackElem s1, StackElem s2)
			throws WrongTypeException, DivByZeroException {
		return ((StackElem) binOp(s1, s2, DIV));
	}

	public static StackElem uminus(StackElem s1) throws WrongTypeException {
		try {
			return ((StackElem) binOp(s1, new StackElemAddr(0), NEG));
		} catch (DivByZeroException e) {
			return null;
		}
	}
}
