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
import pmachine.StackElemAddr;
import pmachine.exceptions.InvalidPInstructionException;
import pmachine.exceptions.RuntimeException;
import pmachine.exceptions.StackException;
import pmachine.exceptions.WrongTypeException;

/**
 * @author ybo
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PInstructionLDA extends PTypedMemoryInstruction {

	private int p;
	private int q;

	/**
	 * @param type
	 */
	public PInstructionLDA(int type, int p, int q) {
		super(type);
		this.p = p;
		this.q = q;
	}

	public void execute(PMachine pm) throws RuntimeException {
		if (isAddrOperation()) {
			ldaA(pm);
		} else if (isBooleanOperation()) {
			ldaB(pm);
		} else if (isIntegerOperation()) {
			ldaI(pm);
		} else
			throw new InvalidPInstructionException(Messages
					.getString("PInstruction.LDATypeIntAddrBool"));
	}

	protected void ldaA(PMachine pm) throws WrongTypeException, StackException {
		StackElemAddr s = (StackElemAddr) StackElemAddr.add(base(pm
				.getPMemory(), p, pm.getMP()), new StackElemAddr(q));
		pm.getPMemory().push(s);
	}

	protected void ldaI(PMachine pm) throws WrongTypeException, StackException {
		StackElemAddr s = (StackElemAddr) StackElemAddr.add(base(pm
				.getPMemory(), p, pm.getMP()), new StackElemAddr(q));
		pm.getPMemory().push(s);
	}

	protected void ldaB(PMachine pm) throws WrongTypeException, StackException {
		StackElemAddr s = (StackElemAddr) StackElemAddr.add(base(pm
				.getPMemory(), p, pm.getMP()), new StackElemAddr(q));
		pm.getPMemory().push(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.instructions.PTypedInstruction#getName()
	 */
	public String getName() {
		return "lda";
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
