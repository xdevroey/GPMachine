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

/**
 * @author ybo
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class PTypedInstruction extends PInstructionAbstract {

	public final static int NO_TYPE = 0;
	public final static int TYPE_INT = 1;
	public final static int TYPE_BOOL = 2;
	public final static int TYPE_ADDR = 3;
	public final static int TYPE_PCADDR = 4;

	private int typeModifier = NO_TYPE;

	public boolean isIntegerOperation() {
		return getTypeModifier() == TYPE_INT;
	}

	public boolean isBooleanOperation() {
		return getTypeModifier() == TYPE_BOOL;
	}

	public boolean isAddrOperation() {
		return getTypeModifier() == TYPE_ADDR;
	}

	public boolean isPCAddrOperation() {
		return getTypeModifier() == TYPE_PCADDR;
	}

	public boolean isUntypedOperation() {
		return getTypeModifier() == NO_TYPE;
	}

	/**
	 * @return Returns the typeModifier.
	 */
	public int getTypeModifier() {
		return typeModifier;
	}

	/**
	 * @param typeModifier
	 *            The typeModifier to set.
	 */
	public void setTypeModifier(int typeModifier) {
		// No assertion for compatibility with Java < 1.4
		// assert (typeModifier >= NO_TYPE && typeModifier <= TYPE_PCADDR);
		this.typeModifier = typeModifier;
	}

	public PTypedInstruction(int typeModifier) {
		setTypeModifier(typeModifier);
	}

	public String getTypeModifierAsString() {
		if (isIntegerOperation())
			return "i";
		else if (isAddrOperation())
			return "a";
		else if (isBooleanOperation())
			return "b";
		else if (isPCAddrOperation())
			return "p";
		else
			return "";
	}

	public abstract String getName();

	public abstract String getArgs();

	public String toString() {
		return getName() + " " + getTypeModifierAsString() + " " + getArgs();
	}
}
