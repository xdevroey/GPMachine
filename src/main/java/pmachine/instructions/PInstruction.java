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
 * \package pmachine.instructions Contains classes representing
 * all possible PInstructions.  Classes are "types of instructions"
 * and actual objects embed the parameters of these instructions,
 * as they appear in the actual PCode program.
 */
package pmachine.instructions;

import pmachine.PMachine;
import pmachine.exceptions.RuntimeException;

/**
 * @author ybo
 * 
 * The interface specifying what methods shall be implemented by PInstructions
 * in order to execute correctly a PMachine.
 */
public interface PInstruction {

	/**
	 * \pre pm is a valid GPMachine state \post pm is a valid GPMachine
	 * resulting from the execution of this PInstruction in pm.
	 * 
	 * @param pm
	 * @throws RuntimeException
	 *             if some error occurs while executing this instruction.
	 */
	public void execute(PMachine pm) throws RuntimeException;

	public String getName();
}
