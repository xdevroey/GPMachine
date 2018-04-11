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
 * StackElem are elements that can be stored on a PStack.
 */
public abstract class StackElem {

	/** The value of this StackElem. Virtually, anything */
	protected Object value;

	/**
	 * true iff this element is stored on the 5 first cells of a local state
	 * space
	 */
	private boolean isCallCell = false;

	public boolean equals(Object o) {
		if (o instanceof StackElem && o != null) {
			return ((StackElem) o).value.equals(this.value);
		}
		return false;
	}

	public boolean isCallCell() {
		return isCallCell;
	}

	public void setCallCell(boolean val) {
		isCallCell = val;
	}

	public String toString() {
		if (printType().equals(""))
			return this.value.toString();
		else
			return printType() + " " + value.toString();
	}

	/*
	 * Constants for representing comparison operators. See compare below
	 */
	/**
	 * Represents the "==" binary operator.
	 */
	protected final static int EQ = 1;
	/**
	 * Represents the ">=" binary operator.
	 */
	protected final static int GEQ = 2;
	/**
	 * Represents the "<=" binary operator.
	 */
	protected final static int LEQ = 3;
	/**
	 * Represents the ">" binary operator.
	 */
	protected final static int GRT = 4;
	/**
	 * Represents the "<" binary operator.
	 */
	protected final static int LST = 5;
	/**
	 * Represents the "!=" binary operator.
	 */
	protected final static int NEQ = 6;

	/**
	 * @param s1
	 *            a non-null StackElem
	 * @param s2
	 *            a non-null StackElem
	 * @param op
	 *            represents an operator one of
	 *            StackElem.EQ,StackElem.GEQ,StackElem.GRT,StackElem.LEQ,StackElem.LST,StackElem.NEQ.
	 * @throws WrongTypeException
	 *             if s1 and s2 are not of same type.
	 * @return a StackElemBool containing true iff s1 op s2, according to the
	 *         "natural ordering" of their type. This natural ordering is
	 *         specified by compareValues(), which is reimplemented in the
	 *         concrete StackElem types.
	 */
	protected static StackElemBool compare(StackElem s1, StackElem s2, int op)
			throws WrongTypeException {
		// call an abstract method for comparing s1 and s2.
		long res = s1.compareValues(s2);
		// System.out.println("Result of comparison is "+res);
		// System.out.println("0 > 0 is "+(0>0));
		// System.out.println("Op is GRT : "+(op==GRT));
		if (op == EQ)
			return new StackElemBool(res == 0);
		else if (op == NEQ)
			return new StackElemBool(res != 0);
		else if (op == GEQ)
			return new StackElemBool(res >= 0);
		else if (op == GRT)
			return new StackElemBool(res > 0);
		else if (op == LEQ)
			return new StackElemBool(res <= 0);
		else if (op == LST)
			return new StackElemBool(res < 0);
		else
			return new StackElemBool(false); /* should never be here */
	}

	/** @return the name of this type */
	public abstract String printType();

	/**
	 * Compares this.value with el.value
	 * 
	 * \pre : both objects have the same type
	 * 
	 * \post \li el.value "<" this.value ==> returns a value > 0 \li el.value
	 * ">" this.value ==> returns a value < 0 \li el.value "=" this.value ==>
	 * returns a value = 0
	 */
	protected abstract long compareValues(StackElem el)
			throws WrongTypeException;
}
