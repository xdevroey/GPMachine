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

package pmachine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Vector;

import javax.swing.ListModel;

import pmachine.exceptions.InvalidPInstructionException;
import pmachine.exceptions.RuntimeException;
import pmachine.instructions.PInstruction;

public interface IPProgramMemory extends ListModel {

	public int getPC();

	public void setPC(int val) throws RuntimeException;

	public void jumpToAddress(StackElemPCAddr address) throws RuntimeException;

	public void jumpToLabel(String label) throws RuntimeException;

	public int countInstructions(int startCount, int instrNumber)
			throws RuntimeException;

	public PInstruction getInstruction() throws RuntimeException;

	public void loadProgram(String fileName)
			throws InvalidPInstructionException, FileNotFoundException,
			IOException;

	public void loadProgram(File file) throws InvalidPInstructionException,
			FileNotFoundException, IOException;

	public void loadProgramFromString(String program)
			throws InvalidPInstructionException, IOException;

	public Vector asDisplayableVector();

	public void reset();

}
