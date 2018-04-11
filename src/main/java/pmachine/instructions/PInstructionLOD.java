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
import pmachine.StackElemBool;
import pmachine.StackElemInt;
import pmachine.exceptions.InvalidPInstructionException;
import pmachine.exceptions.StackException;
import pmachine.exceptions.WrongTypeException;

/**
 * @author ybo
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PInstructionLOD extends PTypedMemoryInstruction {

	private int p;
	private int q;

	/**
	 * @param type
	 */
	public PInstructionLOD(int type, int p, int q) {
		super(type);
		this.p = p;
		this.q = q;
	}

	public void execute(PMachine pm) throws WrongTypeException, StackException,
			InvalidPInstructionException {
		if (isAddrOperation()) {
			lodA(pm);
		} else if (isBooleanOperation()) {
			lodB(pm);
		} else if (isIntegerOperation()) {
			lodI(pm);
		} else
			throw new InvalidPInstructionException(Messages
					.getString("PInstruction.LODTypeIntAddrBool"));
	}

	protected void lodA(PMachine pm) throws WrongTypeException, StackException {
		StackElemAddr t = base(pm.getPMemory(), p, pm.getMP());
		// System.out.println("base is "+t);
		StackElem s = pm.getPMemory().elemAt(
				(StackElemAddr) StackElemAddr.add(t, new StackElemAddr(q)));
		if (s instanceof StackElemAddr) {
			pm.getPMemory().push(s);
		} else {
			throw new WrongTypeException(Messages
					.getString("PMachine.LOD_a_TypeError"));
		}
	}

	protected void lodI(PMachine pm) throws WrongTypeException, StackException {
		StackElemAddr t = base(pm.getPMemory(), p, pm.getMP());

		StackElem s = pm.getPMemory().elemAt(
				(StackElemAddr) StackElemAddr.add(t, new StackElemAddr(q)));
		if (s instanceof StackElemInt) {
			pm.getPMemory().push(s);
		} else {
			throw new WrongTypeException(Messages
					.getString("PMachine.LOD_i_TypeError"));
		}
	}

	protected void lodB(PMachine pm) throws WrongTypeException, StackException {
		StackElemAddr t = base(pm.getPMemory(), p, pm.getMP());

		StackElem s = pm.getPMemory().elemAt(
				(StackElemAddr) StackElemAddr.add(t, new StackElemAddr(q)));
		if (s instanceof StackElemBool) {
			pm.getPMemory().push(s);
		} else {
			throw new WrongTypeException(Messages
					.getString("PMachine.LOD_b_TypeError"));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.instructions.PTypedInstruction#getName()
	 */
	public String getName() {
		return "lod";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.instructions.PTypedInstruction#getArgs()
	 */
	public String getArgs() {
		return p + " " + q;
	}

}
