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

import pmachine.PMachine;
import pmachine.StackElemAddr;
import pmachine.StackElemPCAddr;
import pmachine.exceptions.RuntimeException;

public class PInstructionCUPI extends PTypedMemoryInstruction {

	private int p, q;

	public PInstructionCUPI(int p, int q) {
		super(PTypedInstruction.NO_TYPE);
		this.p = p;
		this.q = q;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.instructions.PInstructionAbstract#getName()
	 */
	public String getName() {
		return "cupi";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.instructions.PInstructionAbstract#getArgs()
	 */
	public String getArgs() {
		return p + " " + q;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.instructions.PInstruction#execute(pmachine.PMachine)
	 */
	public void execute(PMachine pm) throws RuntimeException {
		pm.getPMemory()
				.storeAt(pm.getMP() + 4, new StackElemPCAddr(pm.getPC()));
		pm.jumpToAddress(new StackElemPCAddr(((StackElemAddr) pm.getPMemory()
				.elemAt(
						base(
								pm.getPMemory(),
								p,
								((StackElemAddr) pm.getPMemory().elemAt(
										pm.getMP() + 2)).intValue()).intValue()
								+ q)).intValue()));
	}

}
