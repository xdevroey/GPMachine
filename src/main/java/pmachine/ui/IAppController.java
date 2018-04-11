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

package pmachine.ui;

public interface IAppController extends IController {

	public abstract void launchGUI();

	/* Communication from itsIActionController */

	public abstract void bigStep(int nbrSteps);

	public abstract void undoOnes();

	public abstract void run();

	public abstract void loadFile();

	public abstract void reloadFile();

	public abstract void addBreakpoint();

	public abstract void removeBreakpoint();

	public abstract void removeAllBreakpoints();

	public abstract void addWatch();

	public abstract void removeWatch();

	public abstract void removeAllWatchs();

	public abstract void reset();

	public abstract void step();

	/* Communication from itsPStateController */
	public abstract void executionError();

	public abstract void executionHalted();

	public abstract void newStackSize(int i);
}
