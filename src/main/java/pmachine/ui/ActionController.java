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

import java.awt.Component;

public class ActionController implements IActionController,
		IActionPanelEventHandler {

	private ActionPanel itsVisualPart;
	private IAppController parent;

	public ActionController() {
		itsVisualPart = new ActionPanel(this);
	}

	public ActionController(IAppController superior) {
		this();
		parent = superior;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionController#disableExecution()
	 */
	public void disableExecution() {
		itsVisualPart.setExecutionEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionController#enableExecution()
	 */
	public void enableExecution() {
		itsVisualPart.setExecutionEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionController#disableUndo()
	 */
	public void disableUndo() {
		itsVisualPart.setUndoEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionController#enableUndo()
	 */
	public void enableUndo() {
		itsVisualPart.setUndoEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionController#disableFileReloading()
	 */
	public void disableFileReloading() {
		itsVisualPart.setFileReloadingEnabled(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionController#enableFileReloading()
	 */
	public void enableFileReloading() {
		itsVisualPart.setFileReloadingEnabled(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionController#disableAll()
	 */
	public void disableAll() {
		disableExecution();
		disableFileReloading();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionController#enableAll()
	 */
	public void enableAll() {
		enableExecution();
		enableFileReloading();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IController#getComponent()
	 */
	public Component getComponent() {
		return itsVisualPart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionPanelEventHandler#loadFileEvent()
	 */
	public void loadFileEvent() {
		parent.loadFile();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionPanelEventHandler#reloadFileEvent()
	 */
	public void reloadFileEvent() {
		parent.reloadFile();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionPanelEventHandler#undoOnesEvent()
	 */
	public void undoOnesEvent() {
		parent.undoOnes();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionPanelEventHandler#runEvent()
	 */
	public void runEvent() {
		parent.run();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionPanelEventHandler#addBreakpointEvent()
	 */
	public void addBreakpointEvent() {
		parent.addBreakpoint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionPanelEventHandler#removeBreakpointEvent()
	 */
	public void removeBreakpointEvent() {
		parent.removeBreakpoint();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionPanelEventHandler#removeBreakpointEvent()
	 */
	public void removeAllBreakpointsEvent() {
		parent.removeAllBreakpoints();

	}

	public void addWatchEvent() {
		parent.addWatch();

	}

	public void removeWatchEvent() {
		parent.removeWatch();

	}

	public void removeAllWatchsEvent() {
		parent.removeAllWatchs();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionPanelEventHandler#resetEvent()
	 */
	public void resetEvent() {
		parent.reset();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionPanelEventHandler#stepEvent()
	 */
	public void stepEvent() {
		parent.step();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionPanelEventHandler#bigStepEvent()
	 */
	public void bigStepEvent() {
		try {
			int nbrOfSteps = DialogManager.askNumberOfSteps(itsVisualPart);
			parent.bigStep(nbrOfSteps);
		} catch (CancelException e) {

		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IActionPanelEventHandler#addStackSizeEvent()
	 */
	public void newStackSizeEvent(int i) {
		parent.newStackSize(i);
	}

	public void aboutEvent() {
		DialogManager.displayGPL();
	}
}
