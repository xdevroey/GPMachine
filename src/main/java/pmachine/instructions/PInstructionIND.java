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

package pmachine.instructions;

import pmachine.Messages;
import pmachine.PMachine;
import pmachine.StackElem;
import pmachine.StackElemAddr;
import pmachine.StackElemBool;
import pmachine.StackElemInt;
import pmachine.exceptions.InvalidPInstructionException;
import pmachine.exceptions.RuntimeException;
import pmachine.exceptions.StackException;
import pmachine.exceptions.WrongTypeException;

public class PInstructionIND extends PTypedInstruction {

	/**
	 * @param typeModifier
	 */
	public PInstructionIND(int typeModifier) {
		super(typeModifier);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.instructions.PInstructionAbstract#getName()
	 */
	public String getName() {
		return "ind";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.instructions.PInstructionAbstract#getArgs()
	 */
	public String getArgs() {
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.instructions.PInstruction#execute(pmachine.PMachine)
	 */
	public void execute(PMachine pm) throws RuntimeException {
		if (isIntegerOperation()) {
			indI(pm);
		} else if (isBooleanOperation()) {
			indB(pm);
		} else if (isAddrOperation()) {
			indA(pm);
		} else {
			throw new InvalidPInstructionException(Messages
					.getString("PInstruction.INDTypeIntAddrBool"));
		}
	}

	protected void indA(PMachine pm) throws StackException, WrongTypeException {
		StackElem addr = pm.getPMemory().pop();
		if (addr instanceof StackElemAddr) {
			StackElem ind = pm.getPMemory().elemAt((StackElemAddr) addr);
			if (ind instanceof StackElemAddr) {
				pm.getPMemory().push(ind);
			} else {
				throw new WrongTypeException(Messages
						.getString("PMachineJUI.StackMenuName")
						+ Messages.getString("PStack.TextOutput10")
						+ addr
						+ Messages.getString("PStack.TextOutput11")
						+ Messages.getString("PInstruction.INDTypeAddr"));
			}
		} else {
			throw new WrongTypeException(Messages
					.getString("PMachine.IND_a_RefTypeError"));
		}
	}

	protected void indI(PMachine pm) throws StackException, WrongTypeException {
		StackElem addr = pm.getPMemory().pop();
		if (addr instanceof StackElemAddr) {
			StackElem ind = pm.getPMemory().elemAt((StackElemAddr) addr);
			if (ind instanceof StackElemInt) {
				pm.getPMemory().push(ind);
			} else {
				throw new WrongTypeException(Messages
						.getString("PMachineJUI.StackMenuName")
						+ Messages.getString("PStack.TextOutput10")
						+ addr
						+ Messages.getString("PStack.TextOutput11")
						+ Messages.getString("PInstruction.INDTypeInt"));
			}
		} else {
			throw new WrongTypeException(Messages
					.getString("PMachine.IND_i_RefTypeError"));
		}
	}

	protected void indB(PMachine pm) throws StackException, WrongTypeException {
		StackElem addr = pm.getPMemory().pop();
		if (addr instanceof StackElemAddr) {
			StackElem ind = pm.getPMemory().elemAt((StackElemAddr) addr);
			if (ind instanceof StackElemBool) {
				pm.getPMemory().push(ind);
			} else {
				throw new WrongTypeException(Messages
						.getString("PMachineJUI.StackMenuName")
						+ Messages.getString("PStack.TextOutput10")
						+ addr
						+ Messages.getString("PStack.TextOutput11")
						+ Messages.getString("PInstruction.INDTypeBool"));
			}
		} else {
			throw new WrongTypeException(Messages
					.getString("PMachine.IND_a_RefTypeError"));
		}
	}
}
