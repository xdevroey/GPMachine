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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import pmachine.ExecutionEnvironment;
import pmachine.Messages;
import pmachine.PMachine;
import pmachine.exceptions.InvalidPInstructionException;
import pmachine.exceptions.RuntimeException;

public class StateController implements IStateController, Runnable {

	private IAppController parent;
	private PMachine pm;
	private PMachine pmUndo = null;
	private Set breakpoints = new HashSet();
	private Set watchs = new HashSet();

	private JSplitPane itsVisualPart;

	private IOutputController itsOutputController;
	private StatePanel itsStatePanel;
	private JReader itsReader = new JReader();

	public void init() {
		// Sets the divider between the "state panel"
		// part and the "output" part to a ratio of 0.85.
		// This should only show a few lines of output
		// and leave most room for displaying PCode.
		itsVisualPart.setDividerLocation(0.82);
		itsVisualPart.repaint();
	}

	public StateController() {
		super();
		itsOutputController = new OutputController(this);
		itsStatePanel = new StatePanel();
		itsVisualPart = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false,
				itsStatePanel, itsOutputController.getComponent());
	}

	public StateController(IAppController superior) {
		this();
		this.parent = superior;
	}

	/**
	 * This method ajuste grid's dimentions
	 */
	public void resize(int height, int width) {
		// itsVisualPart.setResizeWeight(0.80);
		itsVisualPart.setDividerLocation((int) ((width - 100) * 0.80));
		itsStatePanel.resize(height, width);
	}

	public void updateRegisters() {
		itsStatePanel.setPCDisplayValue(pm.getPC());
		itsStatePanel.setSPDisplayValue(pm.getSP());
		itsStatePanel.setMPDisplayValue(pm.getMP());
		itsStatePanel.setEPDisplayValue(pm.getEP());

		// follow the index in the code viewport
		itsStatePanel.getCodeList().repaint();
		itsStatePanel.ensureIndexIsVisible(itsStatePanel.getCodeList(), pm
				.getPC());

		// if there is no watch triggered, then just show the up of the Stack
		// list
		if (!stackWatchStop)
			itsStatePanel.ensureIndexIsVisible(itsStatePanel.getStackList(), 0);
		else
			itsStatePanel.ensureIndexIsVisible(itsStatePanel.getStackList(),
					lastHeapWatch);
		itsStatePanel.getStackList().repaint();

		// if there is no watch triggered, then just show the bottom of the Heap
		// list
		if (!heapWatchStop)
			itsStatePanel.ensureIndexIsVisible(itsStatePanel.getHeapList(), pm
					.getMemorySize()
					- pm.getEP() - 1);
		else
			itsStatePanel.ensureIndexIsVisible(itsStatePanel.getHeapList(), pm
					.getMemorySize()
					- lastHeapWatch);
		itsStatePanel.getHeapList().repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IStateController#step()
	 */
	public void step() {
		if (pm != null) {
			try {
				pm.runNextInstr();
				stackWatchStop = false;
				heapWatchStop = false;
				if (pm.isHalted()) {
					parent.executionHalted();
				}
				updateRegisters();
			} catch (RuntimeException e) {
				updateRegisters();
				JOptionPane.showMessageDialog(itsVisualPart, e.getMessage(),
						"Runtime Exception", JOptionPane.ERROR_MESSAGE);
				parent.executionError();
			}
		}
	}

	private boolean stackWatchStop;
	private int lastStackWatch;

	public Set getWatchs() {
		return watchs;
	}

	public boolean isStackWatchTriggered() {
		return stackWatchStop;
	}

	public int getStackWatch() {
		return lastStackWatch;
	}

	private boolean heapWatchStop;
	private int lastHeapWatch;

	public boolean isHeapWatchTriggered() {
		return heapWatchStop;
	}

	public int getHeapWatch() {
		return lastHeapWatch;
	}

	/**
	 * Checking if changes are occured in watching stack set
	 * 
	 * @param index
	 */
	private void stackWatchsChecking(int index) {
		if (watchs.contains(new Integer(pm.getSP() - index))) {
			stackWatchStop = true;
			lastStackWatch = pm.getSP() - index;
			updateRegisters();
		}
	}

	/**
	 * Checking if changes are occured in watching heap set
	 * 
	 * @param index
	 */
	private void heapWatchsChecking(int index) {
		if (watchs.contains(new Integer(pm.getMemorySize() - index))) {
			heapWatchStop = true;
			lastHeapWatch = pm.getMemorySize() - index;
			updateRegisters();
		}
	}

	/*
	 * inverse Stack for Interface purpose
	 */
	private Stack inverseStack(Stack stack) {
		Stack res = new Stack();
		for (int i = stack.size() - 1; 0 <= i; i--) {
			res.add(stack.get(i));
		}
		return res;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IStateController#run()
	 */
	public void run() {
		if ((pm != null) && (!ExecutionEnvironment.isStopPMachineEnabled()))
			try {
				ExecutionEnvironment.setStopPMachineEnabled(false);

				// Remove all Data Listener on Stack and Heap
				pm.getStackPresentation().removeAllListDataListener();
				pm.getHeapPresentation().removeAllListDataListener();

				// Init StackList listener for Stack data changes
				pm.getStackPresentation().addListDataListener(
						new ListDataListener() {
							public void intervalAdded(ListDataEvent e) {
								stackWatchsChecking(e.getIndex0());
								// System.out.println("1Stack :
								// "+e.getIndex0()+"-"+e.getIndex1());
							}

							public void intervalRemoved(ListDataEvent e) {
								stackWatchsChecking(e.getIndex0() - 1);
								// System.out.println("2Stack :
								// "+e.getIndex0()+"-"+e.getIndex1());
								// Delete Upper Stack Watchs
								Iterator i = watchs.iterator();
								while (i.hasNext()) {
									Integer element = (Integer) i.next();
									if (((Integer) element).intValue() == (pm
											.getSP()
											- e.getIndex0() - 1)) {
										watchs.remove(element);
									}
								}
							}

							public void contentsChanged(ListDataEvent e) {
								stackWatchsChecking(e.getIndex0());
							}
						});

				// Init HeapList listener for Heap data changes
				pm.getHeapPresentation().addListDataListener(
						new ListDataListener() {
							public void intervalAdded(ListDataEvent e) {
								// Interval creation in Heap list, newly created
								// Heap set : e.getIndex0() - e.getIndex1()
								if (e.getIndex0() != e.getIndex1()) {
									for (int i = pm.getEP() + 1; i <= e
											.getIndex1(); i++) {
										// I don't know why intervals are
										// inversed in this method
										heapWatchsChecking(pm.getMemorySize()
												- i);
									}
								} else
									heapWatchsChecking(e.getIndex0());
								// System.out.println("Heap :
								// "+(pm.getMemorySize()-e.getIndex0())+"-"+(pm.getMemorySize()-e.getIndex1()));
							}

							public void intervalRemoved(ListDataEvent e) {
								// TODO : never heppends, so check it
								heapWatchsChecking(e.getIndex0());
							}

							public void contentsChanged(ListDataEvent e) {
								heapWatchsChecking(e.getIndex0());
							}
						});

				// Start instruction's execution
				long lastPassTime = System.currentTimeMillis();
				boolean firstExecutedInst = true;
				stackWatchStop = false;
				heapWatchStop = false;
				boolean stopped = false;
				while (!stopped) {
					boolean breakpoint = breakpoints.contains(new Integer(pm
							.getPC()));
					if (((!ExecutionEnvironment.isStopPMachineEnabled())
							&& (!breakpoint) && (!stackWatchStop) && (!heapWatchStop))
							|| (firstExecutedInst)) {
						stackWatchStop = false;
						heapWatchStop = false;
						// BackUp PMachine for Undo Action
						this.setUndoPMachine((PMachine) (this.getPMachine())
								.clone());
						pm.runNextInstr();
						stopped = pm.isHalted();
					} else {
						stopped = true;
					}
					firstExecutedInst = false;

					// Stack Update Timer is initialised here to 0.5 sec
					if (System.currentTimeMillis() - lastPassTime > 500) {
						PMachine pmShow = (PMachine) (this.getPMachine())
								.clone();
						pmShow.getStackPresentation()
								.removeAllListDataListener();
						// Stack Data fill in
						itsStatePanel.getStackList().setListData(
								inverseStack((Stack) pmShow.getPMemory()
										.getStack().clone()));
						itsStatePanel.getStackList().setCellRenderer(
								new StackListCellRenderer(this, this
										.getWatchs(), pmShow));
						// Heap Data fill in
						itsStatePanel.getHeapList()
								.setListData(
										new Vector((List) pmShow.getPMemory()
												.getHeap()));
						itsStatePanel.getHeapList().setCellRenderer(
								new HeapListCellRenderer(this,
										this.getWatchs(), pmShow));

						updateRegisters();
						lastPassTime = System.currentTimeMillis();
					}
				}
				if (pm.isHalted()) {
					parent.executionHalted();
				}
			} catch (RuntimeException e) {
				updateRegisters();
				JOptionPane.showMessageDialog(itsVisualPart, e.getMessage(),
						"Runtime Exception", JOptionPane.ERROR_MESSAGE);
				parent.executionError();
				((AppController) parent).setEnableUndo();
			}
		// Reset execution buttons
		((AppController) parent).setEnableExecution();
		((AppController) parent).setEnableUndo();
		// Reset standard Model Presentation and Cell's Renderer on tread exit
		pm.getStackPresentation().removeAllListDataListener();
		itsStatePanel.getStackList().setModel(pm.getStackPresentation());
		itsStatePanel.getStackList().setCellRenderer(
				new StackListCellRenderer(this, watchs, pm));
		pm.getHeapPresentation().removeAllListDataListener();
		itsStatePanel.getHeapList().setModel(pm.getHeapPresentation());
		itsStatePanel.getHeapList().setCellRenderer(
				new HeapListCellRenderer(this, watchs, pm));
		ExecutionEnvironment.setStopPMachineEnabled(true);
		// Update GUI (Lists, Registers ...)
		updateRegisters();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IStateController#reset()
	 */
	public void reset() {
		ExecutionEnvironment.setStopPMachineEnabled(true);
		if (pm != null) {
			itsOutputController.write(Messages
					.getString("PMachineJUI.OutputExecutionSeparater")
					+ " "
					+ Messages.getString("PStack.TextOutput9")
					+ ExecutionEnvironment.getFilename()
					+ Messages.getString("PStack.TextOutput12"));
			pm.reset();
			updateRegisters();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IStateController#loadProgram()
	 */
	public List loadProgram() {
		List l = null;
		try {
			pm = new PMachine(ExecutionEnvironment.getFilename(),
					ExecutionEnvironment.getStackSize(), itsReader,
					itsOutputController);
			pmUndo = null;
			/*
			 * itsOutputController.write(
			 * Messages.getString("PMachineJUI.OutputExecutionSeparater") + " " +
			 * Messages.getString("PStack.TextOutput9") +
			 * ExecutionEnvironment.getFilename() +
			 * Messages.getString("PStack.TextOutput12"));
			 */
			itsStatePanel.getStackList().setModel(pm.getStackPresentation());
			itsStatePanel.getStackList().setCellRenderer(
					new StackListCellRenderer(this, watchs, pm));
			itsStatePanel.getCodeList().setModel(pm.getProgramMemory());
			itsStatePanel.getCodeList().setCellRenderer(
					new InstrListCellRenderer(breakpoints, pm));
			itsStatePanel.getHeapList().setModel(pm.getHeapPresentation());
			itsStatePanel.getHeapList().setCellRenderer(
					new HeapListCellRenderer(this, watchs, pm));
			updateRegisters();
		} catch (InvalidPInstructionException e) {
			l = new Vector();
			l.add(e.getMessage());
		} catch (FileNotFoundException e) {
			l = new Vector();
			l.add(e.getMessage());
		} catch (IOException e) {
			l = new Vector();
			l.add(e.getMessage());
		}
		return l;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IStateController#bigStep(int)
	 */
	public void bigStep(int nbrSteps) {
		if (pm != null)
			try {
				boolean stopped = false;
				boolean firstExecutedInst = true;
				int i = 0;
				while (!stopped) {
					boolean breakpoint = breakpoints.contains(new Integer(pm
							.getPC()));
					if ((!breakpoint) || (firstExecutedInst)) {
						pm.runNextInstr();
						i++;
						stopped = pm.isHalted() || i >= nbrSteps;
					} else {
						stopped = true;
					}
					firstExecutedInst = false;
				}
				if (pm.isHalted()) {
					parent.executionHalted();
				}
				updateRegisters();
			} catch (RuntimeException e) {
				parent.executionError();
			}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IStateController#addBreakpoint()
	 */
	public void addBreakpoint() {
		int selection = itsStatePanel.getCodeList().getSelectedIndex();
		if (selection > 0) {
			// Something has indeed been selected.
			breakpoints.add(new Integer(selection));
		}
		itsStatePanel.getCodeList().repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IStateController#removeBreakpoint()
	 */
	public void removeBreakpoint() {
		int selection = itsStatePanel.getCodeList().getSelectedIndex();
		if (selection > 0) {
			// Something has indeed been selected.
			breakpoints.remove(new Integer(selection));
		}
		itsStatePanel.getCodeList().repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IStateController#removeAllBreakpoints()
	 */
	public void removeAllBreakpoints() {
		breakpoints.clear();
		itsStatePanel.getCodeList().clearSelection();
		itsStatePanel.getCodeList().repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IStateController#addWatch()
	 */
	public void addWatch() {
		int selection;
		if (itsStatePanel.getStackList().getSelectedIndex() != -1) {
			// Stack element is selected
			selection = itsStatePanel.getStackList().getSelectedIndex();
			watchs.add(new Integer(pm.getSP() - selection));
			itsStatePanel.getStackList().repaint();
		}
		if (itsStatePanel.getHeapList().getSelectedIndex() != -1) {
			// Heap element is selected
			selection = itsStatePanel.getHeapList().getSelectedIndex();
			watchs.add(new Integer(pm.getMemorySize() - selection));
			itsStatePanel.getHeapList().repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IStateController#removeWatch()
	 */
	public void removeWatch() {
		int selection;
		if (itsStatePanel.getStackList().getSelectedIndex() != -1) {
			// Stack element is selected
			selection = itsStatePanel.getStackList().getSelectedIndex();
			watchs.remove(new Integer(pm.getSP() - selection));
			itsStatePanel.getStackList().repaint();
		}
		if (itsStatePanel.getHeapList().getSelectedIndex() != -1) {
			// Heap element is selected
			selection = itsStatePanel.getHeapList().getSelectedIndex();
			watchs.remove(new Integer(pm.getMemorySize() - selection));
			itsStatePanel.getHeapList().repaint();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IStateController#removeAllWatchs()
	 */
	public void removeAllWatchs() {
		watchs.clear();
		itsStatePanel.getStackList().clearSelection();
		itsStatePanel.getHeapList().clearSelection();
		itsStatePanel.getStackList().repaint();
		itsStatePanel.getHeapList().repaint();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IStateController#newStackSize()
	 */
	public void newStackSize(int i) {
		if (i <= -1)
			i = -1;
		if (i > 2000)
			i = 2000;
		ExecutionEnvironment.setStackSize(i);
		if (ExecutionEnvironment.getFilename() != null) {
			parent.reloadFile();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.ui.IController#getComponent()
	 */
	public Component getComponent() {
		return itsVisualPart;
	}

	public PMachine getUndoPMachine() {
		return pmUndo;
	}

	public void setUndoPMachine(PMachine pm) {
		this.pmUndo = pm;
	}

	public PMachine getPMachine() {
		return pm;
	}

	public void setPMachine(PMachine pm) {
		this.pm = pm;
		itsStatePanel.getStackList().setModel(pm.getStackPresentation());
		itsStatePanel.getStackList().setCellRenderer(
				new StackListCellRenderer(this, watchs, pm));
		itsStatePanel.getCodeList().setModel(pm.getProgramMemory());
		itsStatePanel.getCodeList().setCellRenderer(
				new InstrListCellRenderer(breakpoints, pm));
		itsStatePanel.getHeapList().setModel(pm.getHeapPresentation());
		itsStatePanel.getHeapList().setCellRenderer(
				new HeapListCellRenderer(this, watchs, pm));
		this.updateRegisters();
	}
}
