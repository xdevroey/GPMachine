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

public class PInstructionDPL extends PTypedInstruction {

	/**
	 * @param typeModifier
	 */
	public PInstructionDPL(int typeModifier) {
		super(typeModifier);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.instructions.PInstructionAbstract#getName()
	 */
	public String getName() {
		return "dpl";
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
		if (isAddrOperation()) {
			dplA(pm);
		} else if (isIntegerOperation()) {
			dplI(pm);
		} else if (isBooleanOperation()) {
			dplB(pm);
		} else {
			throw new InvalidPInstructionException(Messages
					.getString("PInstruction.DLPTypeIntAddrBool"));
		}
	}

	protected void dplA(PMachine pm) throws WrongTypeException, StackException {
		StackElem s = pm.getPMemory().pop();
		if (s instanceof StackElemAddr) {
			pm.getPMemory().push(s);
			pm.getPMemory().push(s);
		} else {
			throw new WrongTypeException(Messages
					.getString("PMachine.DPL_a_TypeError"));
		}
	}

	protected void dplI(PMachine pm) throws WrongTypeException, StackException {
		StackElem s = pm.getPMemory().pop();
		if (s instanceof StackElemInt) {
			pm.getPMemory().push(s);
			pm.getPMemory().push(s);
		} else {
			throw new WrongTypeException(Messages
					.getString("PMachine.DPL_i_TypeError"));
		}
	}

	protected void dplB(PMachine pm) throws WrongTypeException, StackException {
		StackElem s = pm.getPMemory().pop();
		if (s instanceof StackElemBool) {
			pm.getPMemory().push(s);
			pm.getPMemory().push(s);
		} else {
			throw new WrongTypeException(Messages
					.getString("PMachine.DPL_b_TypeError"));
		}
	}

}
