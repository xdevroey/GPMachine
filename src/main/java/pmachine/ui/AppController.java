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

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import pmachine.ExecutionEnvironment;
import pmachine.Messages;
import pmachine.PMachine;

public class AppController implements IAppController {

	/** The visual component associated with this Controller */
	private JFrame itsFrame;

	/** The two sub-controllers this Controller controls */
	private IActionController itsActionController;
	private StateController itsStateController;

	public AppController() {
		itsFrame = new JFrame(Messages.getString("PMachineJUI.PMachineName")
				+ " " + ExecutionEnvironment.getVersion());

		// Exit application on close operation
		itsFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		itsActionController = new ActionController(this);
		itsStateController = new StateController(this);
		itsFrame.getContentPane().add(itsActionController.getComponent(),
				BorderLayout.NORTH);
		itsFrame.getContentPane().add(itsStateController.getComponent(),
				BorderLayout.CENTER);
		// Smartly resize main frame
		itsFrame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				// System.out.println("this.componentResized, event=" + evt);
				// Take params like : COMPONENT_RESIZED (0,0 800x600)
				String param = evt.paramString();
				if (param.startsWith("COMPONENT_RESIZED")) {
					// Take : 0,0 800x600
					param = param.substring(param.indexOf("(") + 1, param
							.indexOf(")"));
					int height, width;
					// Parse from String to int height,width
					height = Integer.parseInt(param.substring(param
							.indexOf(" ") + 1, param.indexOf("x")));
					width = Integer.parseInt(param
							.substring(param.indexOf("x") + 1));

					// System.out.println(param + " : " + height + width);
					itsStateController.resize(height, width);
				}
			}
		});
		initialize();
	}

	private void initialize() {
		// Default Size : 800x600
		itsFrame.setSize(800, 600);
		itsFrame.setVisible(true);
		itsStateController.init();
		if (ExecutionEnvironment.getFilename() == null) {
			itsActionController.disableExecution();
			itsActionController.disableFileReloading();
		} else {
			try {
				itsActionController.disableExecution();
				itsActionController.disableFileReloading();
				String fileName = DialogManager.askChooseFile(itsFrame);
				ExecutionEnvironment.setFilename(fileName);
				List errors = itsStateController.loadProgram();
				if (errors == null) {
					itsActionController.enableExecution();
					itsActionController.enableFileReloading();
				} else {
					JOptionPane.showMessageDialog(itsFrame, errors, Messages
							.getString("PMachineJUI.ErrorWhileLoadingFile"),
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (CancelException e) {
				// Openning is canceled => do nothing
			}
		}
	}

	/* Communication with itsIActionController */

	public void bigStep(int nbrSteps) {
		itsStateController.bigStep(nbrSteps);
	}

	public void undoOnes() {
		itsActionController.disableUndo();
		itsStateController.setPMachine(itsStateController.getUndoPMachine());
		itsActionController.enableExecution();
	}

	private static Thread threadPMachine;

	public void run() {
		if (ExecutionEnvironment.isStopPMachineEnabled()
				&& (ExecutionEnvironment.getFilename() != null)
				&& (threadPMachine == null || (threadPMachine.getState() != Thread.State.RUNNABLE))) {
			ExecutionEnvironment.setStopPMachineEnabled(false);
			itsActionController.disableExecution();
			// Start P-Code execution like Thread
			threadPMachine = new Thread((StateController) itsStateController);
			// System.out.println(threadPMachine.getState());
			threadPMachine.start();
			// System.out.println(threadPMachine.getState());
		} else
			ExecutionEnvironment.setStopPMachineEnabled(true);
		// System.out.println(threadPMachine.getState());
	}

	public void setEnableUndo() {
		itsActionController.enableUndo();
	}

	public void setEnableExecution() {
		itsActionController.enableExecution();
	}

	public void loadFile() {
		try {
			itsActionController.disableExecution();
			String fileName = DialogManager.askChooseFile(itsFrame);
			ExecutionEnvironment.setFilename(fileName);
			List errors = itsStateController.loadProgram();
			itsActionController.enableFileReloading();
			if (errors == null) {
				itsStateController.reset();
				itsFrame.setTitle("GPMachine ("
						+ ExecutionEnvironment.getFilename() + ")");
				itsActionController.enableExecution();
			} else {
				JOptionPane.showMessageDialog(itsFrame, errors, Messages
						.getString("PMachineJUI.ErrorWhileLoadingFile"),
						JOptionPane.ERROR_MESSAGE);
			}
		} catch (CancelException e) {

		}
	}

	public void reloadFile() {
		if (ExecutionEnvironment.getFilename() != null) {
			itsStateController.reset();
			itsActionController.disableExecution();
			List errors = itsStateController.loadProgram();
			itsActionController.enableFileReloading();
			if (errors == null) {
				itsActionController.enableExecution();
			} else {
				JOptionPane.showMessageDialog(itsFrame, errors, Messages
						.getString("PMachineJUI.ErrorWhileLoadingFile"),
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	public void addBreakpoint() {
		itsStateController.addBreakpoint();
	}

	public void removeBreakpoint() {
		itsStateController.removeBreakpoint();
	}

	public void removeAllBreakpoints() {
		itsStateController.removeAllBreakpoints();
	}

	public void addWatch() {
		itsStateController.addWatch();
	}

	public void removeWatch() {
		itsStateController.removeWatch();
	}

	public void removeAllWatchs() {
		itsStateController.removeAllWatchs();
	}

	public void newStackSize(int i) {
		// itsStateController.reset();
		itsStateController.newStackSize(i);
	}

	public void reset() {
		if (ExecutionEnvironment.getFilename() != null) {
			itsActionController.disableUndo();
			itsActionController.enableExecution();
		}
		itsStateController.reset();
	}

	public void step() {
		itsStateController.setUndoPMachine((PMachine) (itsStateController
				.getPMachine()).clone());
		itsStateController.step();
		itsActionController.enableUndo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IAppController#executionError()
	 */
	public void executionError() {
		itsActionController.disableExecution();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IAppController#executionHalted()
	 */
	public void executionHalted() {
		itsActionController.disableExecution();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IController#getComponent()
	 */
	public Component getComponent() {
		return itsFrame;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IAppController#launchGUI()
	 */
	public void launchGUI() {
		if (ExecutionEnvironment.isLicenseShouldBePrinted()) {
			DialogManager.displayGPL();
		}
	}
}
