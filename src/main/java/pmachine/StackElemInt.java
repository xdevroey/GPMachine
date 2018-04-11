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
public class StackElemInt extends StackElem {

	public String printType() {
		return Messages.getString("StackElemInt.TypeName");
	}

	protected long compareValues(StackElem el) throws WrongTypeException {
		if (!(el instanceof StackElemInt)) {
			throw new WrongTypeException(Messages
					.getString("StackElemInt.CompareIntegers"));
		}

		StackElemInt i = (StackElemInt) el;

		return (this.longValue() - i.longValue());
	}

	public StackElemInt(int i) {
		value = new Integer(i);
	}

	private final static int ADD = 1;
	private final static int MINUS = 2;
	private final static int MULT = 3;
	private final static int UMINUS = 4;
	private final static int DIV = 5;

	private final static StackElemInt dummyStackElemInt = new StackElemInt(0);

	Integer getValue() {
		return new Integer(((Integer) this.value).intValue());
	}

	public int longValue() {
		return ((Integer) this.value).intValue();
	}

	private static StackElemInt binOp(StackElem s1, StackElem s2, int op)
			throws WrongTypeException, DivByZeroException {
		if (s1 instanceof StackElemInt && s2 instanceof StackElemInt) {
			int val1 = ((Integer) ((StackElemInt) s1).value).intValue();
			int val2 = ((Integer) ((StackElemInt) s2).value).intValue();
			if (op == ADD)
				return (new StackElemInt(val1 + val2));
			else if (op == MINUS)
				return (new StackElemInt(val1 - val2));
			else if (op == MULT)
				return (new StackElemInt(val1 * val2));
			else if (op == UMINUS)
				return (new StackElemInt(0 - val1));
			else if (op == DIV) {
				if (val2 == 0)
					throw new DivByZeroException(Messages
							.getString("StackElemInt.DivByZero"));
				else
					return (new StackElemInt(val1 / val2));
			} else
				return null; /* should never be here */
		} else {
			throw new WrongTypeException(Messages
					.getString("StackElemInt.OperandsAreNotInteger"));
		}
	}

	public static StackElem add(StackElem s1, StackElem s2)
			throws WrongTypeException {
		try {
			return ((StackElem) binOp(s1, s2, ADD));
		} catch (DivByZeroException e) {
			System.err.println(Messages
					.getString("StackElemInt.DivByZeroAddition"));
			System.exit(1);
		}
		return null;
	}

	public static StackElem sub(StackElem s1, StackElem s2)
			throws WrongTypeException {
		try {
			return ((StackElem) binOp(s1, s2, MINUS));
		} catch (DivByZeroException e) {
			System.err.println(Messages
					.getString("StackElemInt.DivByZeroSubstraction"));
			System.exit(1);
		}
		return null;
	}

	public static StackElem mult(StackElem s1, StackElem s2)
			throws WrongTypeException {
		try {
			return ((StackElem) binOp(s1, s2, MULT));
		} catch (DivByZeroException e) {
			System.err.println(Messages
					.getString("StackElemInt.DivByZeroMultiplication"));
			System.exit(1);
		}
		return null;
	}

	public static StackElem uminus(StackElem s1) throws WrongTypeException {
		try {
			return binOp(s1, dummyStackElemInt, UMINUS);
		} catch (DivByZeroException e) {
			System.err.println(Messages
					.getString("StackElemInt.DivByZeroUMinus"));
			System.exit(1);
		}
		return null;
	}

	public static StackElem div(StackElem s1, StackElem s2)
			throws WrongTypeException, DivByZeroException {
		return binOp(s1, s2, DIV);
	}

	protected static StackElemBool compare(StackElem s1, StackElem s2, int op)
			throws WrongTypeException {
		return StackElem.compare(s1, s2, op);
	}

	public static StackElemBool eq(StackElem s1, StackElem s2)
			throws WrongTypeException {
		return compare(s1, s2, EQ);
	}

	public static StackElemBool neq(StackElem s1, StackElem s2)
			throws WrongTypeException {
		return compare(s1, s2, NEQ);
	}

	public static StackElemBool leq(StackElem s1, StackElem s2)
			throws WrongTypeException {
		return compare(s1, s2, LEQ);
	}

	public static StackElemBool geq(StackElem s1, StackElem s2)
			throws WrongTypeException {
		return compare(s1, s2, GEQ);
	}

	public static StackElemBool grt(StackElem s1, StackElem s2)
			throws WrongTypeException {
		return compare(s1, s2, GRT);
	}

	public static StackElemBool lst(StackElem s1, StackElem s2)
			throws WrongTypeException {
		return compare(s1, s2, LST);
	}

}
