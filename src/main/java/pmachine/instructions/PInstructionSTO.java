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

import pmachine.Messages;
import pmachine.PMachine;
import pmachine.StackElem;
import pmachine.StackElemAddr;
import pmachine.exceptions.InvalidPInstructionException;
import pmachine.exceptions.RuntimeException;
import pmachine.exceptions.WrongTypeException;

public class PInstructionSTO extends PTypedMemoryInstruction {

	/**
	 * @param type
	 */
	public PInstructionSTO(int type) {
		super(type);
	}

	public void execute(PMachine pm) throws RuntimeException {
		if (isAddrOperation()) {
			stoA(pm);
		} else if (isBooleanOperation()) {
			stoB(pm);
		} else if (isIntegerOperation()) {
			stoI(pm);
		} else
			throw new InvalidPInstructionException(Messages
					.getString("PInstruction.STOTypeIntAddrBool"));
	}

	protected void stoA(PMachine pm) throws RuntimeException {
		StackElem sp = pm.getPMemory().pop();
		StackElem sp1 = pm.getPMemory().pop();
		// System.out.println("Valeur de SP/SP1:" + String.valueOf(sp) + "/"+
		// String.valueOf(sp1) );
		pm.getPMemory().push(sp);
		if (sp1 instanceof StackElemAddr) {
			PInstruction instr = new PInstructionSRO(
					PTypedInstruction.TYPE_ADDR, (int) ((StackElemAddr) sp1)
							.intValue());
			instr.execute(pm);
		} else {
			throw new WrongTypeException(Messages
					.getString("PMachine.STO_i_RefTypeError"));
		}
	}

	protected void stoI(PMachine pm) throws RuntimeException {
		StackElem sp = pm.getPMemory().pop();
		StackElem sp1 = pm.getPMemory().pop();
		// System.out.println("Valeur de SP/SP1:" + String.valueOf(sp) + "/"+
		// String.valueOf(sp1) );
		pm.getPMemory().push(sp);
		if (sp1 instanceof StackElemAddr) {
			PInstruction instr = new PInstructionSRO(
					PTypedInstruction.TYPE_INT, (int) ((StackElemAddr) sp1)
							.intValue());
			instr.execute(pm);
		} else {
			throw new WrongTypeException(Messages
					.getString("PMachine.STO_i_RefTypeError"));
		}
	}

	protected void stoB(PMachine pm) throws RuntimeException {
		StackElem sp = pm.getPMemory().pop();
		StackElem sp1 = pm.getPMemory().pop();
		// System.out.println("Valeur de SP/SP1:" + String.valueOf(sp) + "/"+
		// String.valueOf(sp1) );
		pm.getPMemory().push(sp);
		if (sp1 instanceof StackElemAddr) {
			PInstruction instr = new PInstructionSRO(
					PTypedInstruction.TYPE_BOOL, (int) ((StackElemAddr) sp1)
							.intValue());
			instr.execute(pm);
		} else {
			throw new WrongTypeException(Messages
					.getString("PMachine.STO_b_RefTypeError"));
		}
	}

	public String getName() {
		return "sto";
	}

	public String getArgs() {
		return "";
	}

}
