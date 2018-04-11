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

 import javax.swing.JPanel;
 * TODO Comment this class
 */

package pmachine.ui;

import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import pmachine.ExecutionEnvironment;
import pmachine.Messages;

public class ActionPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private IActionPanelEventHandler itsEventHandler;

	private JMenuBar jJMenuBar = null;
	private JMenu jFileMenu = null;
	private JToolBar jToolBar = null;
	private JButton undoOnesButton = null;
	private JButton runButton = null;
	private JButton stepButton = null;
	private JMenuItem jOpenMenuItem = null;
	private JButton openButton = null;
	private JMenuItem jReloadFileMenuItem = null;
	private JMenuItem jExitMenuItem = null;
	private JMenu jExecutionMenu = null;
	private JMenuItem jResetMenuItem = null;
	private JMenuItem jUndoOnesMenuItem = null;
	private JMenuItem jRunMenuItem = null;
	private JMenuItem jStepMenuItem = null;
	private JMenuItem jBigStepMenuItem = null;
	private JMenu jBreakpointMenu = null;
	private JMenuItem jAddBreakpointMenuItem = null;
	private JMenuItem jRemoveBreakpointMenuItem = null;
	private JMenuItem jRemoveAllBreakpointsMenuItem = null;
	private JMenu jWatchMenu = null;
	private JMenuItem jAddWatchMenuItem = null;
	private JMenuItem jRemoveWatchMenuItem = null;
	private JMenuItem jRemoveAllWatchsMenuItem = null;
	private JMenu jOptionsMenu = null;
	private JMenu jStackSizeMenu = null;
	private JMenuItem jInputStackSizeMenuItem = null;
	private JMenuItem jStackSize200MenuItem = null;
	private JMenuItem jStackSize500MenuItem = null;
	private JMenuItem jStackSize1000MenuItem = null;

	private JButton resetButton = null;
	private JButton bigStepButton = null;
	private JButton addBreakpointButton = null;
	private JButton removeBreakpointButton = null;
	private JButton removeAllBreakpointsButton = null;
	private JButton addWatchButton = null;
	private JButton removeWatchButton = null;
	private JButton removeAllWatchsButton = null;
	private JMenu jMenuHelp = null;
	private JMenuItem jAboutMenuItem = null;

	private JButton reloadButton;

	/**
	 * This is the default constructor
	 */
	public ActionPanel() {
		super();
		initialize();
	}

	public ActionPanel(IActionPanelEventHandler h) {
		this();
		itsEventHandler = h;
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(137, 61);
		this.setLayout(new GridLayout(2, 1));
		this.add(getJJMenuBar(), null);
		this.add(getJToolBar(), null);
	}

	/**
	 * This method initializes jJMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getJJMenuBar() {
		if (jJMenuBar == null) {
			jJMenuBar = new JMenuBar();
			jJMenuBar.add(getJFileMenu());
			jJMenuBar.add(getJExecutionMenu());
			jJMenuBar.add(getJOptionsMenu());
			jJMenuBar.add(Box.createHorizontalGlue());
			jJMenuBar.add(getJMenuHelp());
		}
		return jJMenuBar;
	}

	/**
	 * This method initializes jFileMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJFileMenu() {
		if (jFileMenu == null) {
			jFileMenu = new JMenu();
			jFileMenu.setText(Messages.getString("PMachineJUI.FileMenuName"));
			jFileMenu.add(getJOpenMenuItem());
			jFileMenu.add(getJReloadFileMenuItem());
			jFileMenu.add(new JSeparator());
			jFileMenu.add(getJExitMenuItem());
		}
		return jFileMenu;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jToolBar = new JToolBar();
			// tool bar is fixed
			jToolBar.setFloatable(false);
			jToolBar.add(getOpenButton());
			jToolBar.add(getReloadButton());
			jToolBar.add(new JToolBar.Separator());
			jToolBar.add(getResetButton());
			jToolBar.add(getUndoOnesButton());
			jToolBar.add(getRunButton());
			jToolBar.add(getStepButton());
			jToolBar.add(getBigStepButton());
			//
			jToolBar.add(new JToolBar.Separator());
			jToolBar.add(getAddBreakpointButton());
			jToolBar.add(getRemoveBreakpointButton());
			jToolBar.add(getRemoveAllBreakpointsButton());
			//
			jToolBar.add(new JToolBar.Separator());
			jToolBar.add(getAddWatchButton());
			jToolBar.add(getRemoveWatchButton());
			jToolBar.add(getRemoveAllWatchsButton());
		}
		return jToolBar;
	}

	/**
	 * This method initializes jUndoOnesMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJUndoOnesMenuItem() {
		if (jUndoOnesMenuItem == null) {
			jUndoOnesMenuItem = new JMenuItem();
			jUndoOnesMenuItem.setText(Messages
					.getString("PMachineJUI.UndoMenuName"));
			jUndoOnesMenuItem.setIcon(ExecutionEnvironment.getUndoOnesIcon());
			jUndoOnesMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.undoOnesEvent();
						}
					});
		}
		return jUndoOnesMenuItem;
	}

	/**
	 * This method initializes undoOnesButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getUndoOnesButton() {
		if (undoOnesButton == null) {
			undoOnesButton = new JButton();
			undoOnesButton.setToolTipText(Messages
					.getString("PMachineJUI.UndoTipText"));
			if (ExecutionEnvironment.getUndoOnesIcon() == null)
				undoOnesButton.setText(Messages
						.getString("PMachineJUI.UndoMenuName"));
			else
				undoOnesButton.setIcon(ExecutionEnvironment.getUndoOnesIcon());
			undoOnesButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.undoOnesEvent();
						}
					});
		}
		return undoOnesButton;
	}

	/**
	 * This method initializes runButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getRunButton() {
		if (runButton == null) {
			runButton = new JButton();
			runButton.setToolTipText(Messages
					.getString("PMachineJUI.RunTipText"));
			if (ExecutionEnvironment.getRunIcon() == null)
				runButton
						.setText(Messages.getString("PMachineJUI.RunMenuName"));
			else
				runButton.setIcon(ExecutionEnvironment.getRunIcon());
			runButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					itsEventHandler.runEvent();
				}
			});
		}
		return runButton;
	}

	/**
	 * This method initializes stepButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getStepButton() {
		if (stepButton == null) {
			stepButton = new JButton();
			stepButton.setToolTipText(Messages
					.getString("PMachineJUI.StepTipText"));
			if (ExecutionEnvironment.getNextIcon() == null)
				stepButton.setText(Messages
						.getString("PMachineJUI.StepMenuName"));
			else
				stepButton.setIcon(ExecutionEnvironment.getNextIcon());
			stepButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					itsEventHandler.stepEvent();
				}
			});
		}
		return stepButton;
	}

	/**
	 * This method initializes jOpenMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJOpenMenuItem() {
		if (jOpenMenuItem == null) {
			jOpenMenuItem = new JMenuItem();
			jOpenMenuItem.setText(Messages
					.getString("PMachineJUI.OpenMenuName"));
			jOpenMenuItem.setIcon(ExecutionEnvironment.getOpenFileIcon());
			jOpenMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.loadFileEvent();
						}
					});
		}
		return jOpenMenuItem;
	}

	/**
	 * This method initializes jReloadFileMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJReloadFileMenuItem() {
		if (jReloadFileMenuItem == null) {
			jReloadFileMenuItem = new JMenuItem();
			jReloadFileMenuItem.setText(Messages
					.getString("PMachineJUI.ReloadMenuName"));
			jReloadFileMenuItem.setIcon(ExecutionEnvironment
					.getReloadFileIcon());
			jReloadFileMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.reloadFileEvent();
						}
					});
		}
		return jReloadFileMenuItem;
	}

	/**
	 * This method initializes jExitMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJExitMenuItem() {
		if (jExitMenuItem == null) {
			jExitMenuItem = new JMenuItem();
			jExitMenuItem.setText(Messages
					.getString("PMachineJUI.ExitMenuName"));
			jExitMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.exit(0);
						}
					});
		}
		return jExitMenuItem;
	}

	/**
	 * This method initializes jExecutionMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJExecutionMenu() {
		if (jExecutionMenu == null) {
			jExecutionMenu = new JMenu();
			jExecutionMenu.setText(Messages
					.getString("PMachineJUI.ExecutionMenuName"));
			jExecutionMenu.add(getJResetMenuItem());
			jExecutionMenu.add(getJUndoOnesMenuItem());
			jExecutionMenu.add(getJRunMenuItem());
			jExecutionMenu.add(getJStepMenuItem());
			jExecutionMenu.add(getJBigStepMenuItem());
			jExecutionMenu.add(new JSeparator());
			jExecutionMenu.add(getJBreakpointMenu());
			jExecutionMenu.add(new JSeparator());
			jExecutionMenu.add(getJWatchMenu());
			jExecutionMenu.add(new JSeparator());
		}
		return jExecutionMenu;
	}

	/**
	 * This method initializes jRunMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJRunMenuItem() {
		if (jRunMenuItem == null) {
			jRunMenuItem = new JMenuItem();
			jRunMenuItem.setText(Messages.getString("PMachineJUI.RunMenuName"));
			jRunMenuItem.setIcon(ExecutionEnvironment.getRunIcon());
			jRunMenuItem.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					itsEventHandler.runEvent();
				}
			});
		}
		return jRunMenuItem;
	}

	/**
	 * This method initializes jStepMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJStepMenuItem() {
		if (jStepMenuItem == null) {
			jStepMenuItem = new JMenuItem();
			// jStepMenuItem.setActionCommand("");
			jStepMenuItem.setText(Messages
					.getString("PMachineJUI.StepMenuName"));
			jStepMenuItem.setIcon(ExecutionEnvironment.getNextIcon());
			jStepMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.stepEvent();
						}
					});
		}
		return jStepMenuItem;
	}

	/**
	 * This method initializes jBigStepMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJBigStepMenuItem() {
		if (jBigStepMenuItem == null) {
			jBigStepMenuItem = new JMenuItem();
			jBigStepMenuItem.setText(Messages
					.getString("PMachineJUI.BigStepMenuName"));
			jBigStepMenuItem.setIcon(ExecutionEnvironment.getBigStepIcon());
			jBigStepMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.bigStepEvent();
						}
					});
		}
		return jBigStepMenuItem;
	}

	/**
	 * This method initializes jBreakpointMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJBreakpointMenu() {
		if (jBreakpointMenu == null) {
			jBreakpointMenu = new JMenu();
			jBreakpointMenu.setText(Messages
					.getString("PMachineJUI.BreakpointsMenuName"));
			jBreakpointMenu.setIcon(ExecutionEnvironment.getBreakpointIcon());
			jBreakpointMenu.add(getJAddBreakpointMenuItem());
			jBreakpointMenu.add(getJRemoveBreakpointMenuItem());
			jBreakpointMenu.add(getJRemoveAllBreakpointsMenuItem());
		}
		return jBreakpointMenu;
	}

	/**
	 * This method initializes jAddBreakpointMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJAddBreakpointMenuItem() {
		if (jAddBreakpointMenuItem == null) {
			jAddBreakpointMenuItem = new JMenuItem();
			jAddBreakpointMenuItem.setText(Messages
					.getString("PMachineJUI.BreakpointsAddMenuName"));
			jAddBreakpointMenuItem.setIcon(ExecutionEnvironment
					.getAddBreakpointIcon());
			jAddBreakpointMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.addBreakpointEvent();
						}
					});
		}
		return jAddBreakpointMenuItem;
	}

	/**
	 * This method initializes jRemoveBreakpointMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJRemoveBreakpointMenuItem() {
		if (jRemoveBreakpointMenuItem == null) {
			jRemoveBreakpointMenuItem = new JMenuItem();
			jRemoveBreakpointMenuItem.setText(Messages
					.getString("PMachineJUI.BreakpointsRemoveMenuName"));
			jRemoveBreakpointMenuItem.setIcon(ExecutionEnvironment
					.getRemoveBreakpointIcon());
			jRemoveBreakpointMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.removeBreakpointEvent();
						}
					});
		}
		return jRemoveBreakpointMenuItem;
	}

	/**
	 * This method initializes jRemoveAllBreakpointsMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJRemoveAllBreakpointsMenuItem() {
		if (jRemoveAllBreakpointsMenuItem == null) {
			jRemoveAllBreakpointsMenuItem = new JMenuItem();
			jRemoveAllBreakpointsMenuItem.setText(Messages
					.getString("PMachineJUI.BreakpointsRemoveAllMenuName"));
			jRemoveAllBreakpointsMenuItem.setIcon(ExecutionEnvironment
					.getRemoveAllBreakpointsIcon());
			jRemoveAllBreakpointsMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.removeAllBreakpointsEvent();
						}
					});
		}
		return jRemoveAllBreakpointsMenuItem;
	}

	/**
	 * This method initializes jWatchMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJWatchMenu() {
		if (jWatchMenu == null) {
			jWatchMenu = new JMenu();
			jWatchMenu
					.setText(Messages.getString("PMachineJUI.WatchsMenuName"));
			jWatchMenu.setIcon(ExecutionEnvironment.getWatchIcon());
			jWatchMenu.add(getJAddWatchMenuItem());
			jWatchMenu.add(getJRemoveWatchMenuItem());
			jWatchMenu.add(getJRemoveAllWatchsMenuItem());
		}
		return jWatchMenu;
	}

	/**
	 * This method initializes jAddWatchMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJAddWatchMenuItem() {
		if (jAddWatchMenuItem == null) {
			jAddWatchMenuItem = new JMenuItem();
			jAddWatchMenuItem.setText(Messages
					.getString("PMachineJUI.WatchsAddMenuName"));
			jAddWatchMenuItem.setIcon(ExecutionEnvironment.getAddWatchIcon());
			jAddWatchMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.addWatchEvent();
						}
					});
		}
		return jAddWatchMenuItem;
	}

	/**
	 * This method initializes jRemoveWatchMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJRemoveWatchMenuItem() {
		if (jRemoveWatchMenuItem == null) {
			jRemoveWatchMenuItem = new JMenuItem();
			jRemoveWatchMenuItem.setText(Messages
					.getString("PMachineJUI.WatchsRemoveMenuName"));
			jRemoveWatchMenuItem.setIcon(ExecutionEnvironment
					.getRemoveWatchIcon());
			jRemoveWatchMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.removeWatchEvent();
						}
					});
		}
		return jRemoveWatchMenuItem;
	}

	/**
	 * This method initializes jRemoveAllWatchsMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJRemoveAllWatchsMenuItem() {
		if (jRemoveAllWatchsMenuItem == null) {
			jRemoveAllWatchsMenuItem = new JMenuItem();
			jRemoveAllWatchsMenuItem.setText(Messages
					.getString("PMachineJUI.WatchsRemoveAllMenuName"));
			jRemoveAllWatchsMenuItem.setIcon(ExecutionEnvironment
					.getRemoveAllWatchsIcon());
			jRemoveAllWatchsMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.removeAllWatchsEvent();
						}
					});
		}
		return jRemoveAllWatchsMenuItem;
	}

	/**
	 * This method initializes jOptionsMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJOptionsMenu() {
		if (jOptionsMenu == null) {
			jOptionsMenu = new JMenu();
			jOptionsMenu.setText(Messages
					.getString("PMachineJUI.OptionsMenuName"));
			jOptionsMenu.add(getJStackSizeMenu());
		}
		return jOptionsMenu;
	}

	/**
	 * This method initializes jStackSizeMenu
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJStackSizeMenu() {
		if (jStackSizeMenu == null) {
			jStackSizeMenu = new JMenu();
			jStackSizeMenu.setText(Messages
					.getString("PMachineJUI.StackSizeMenuName"));
			jStackSizeMenu.add(getJInputStackSizeMenuItem());
			jStackSizeMenu.add(new JSeparator());
			jStackSizeMenu.add(getJStackSize200MenuItem());
			jStackSizeMenu.add(getJStackSize500MenuItem());
			jStackSizeMenu.add(getJStackSize1000MenuItem());
		}
		return jStackSizeMenu;
	}

	/**
	 * This method initializes jInputStackSizeMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJInputStackSizeMenuItem() {
		if (jInputStackSizeMenuItem == null) {
			jInputStackSizeMenuItem = new JMenuItem();
			jInputStackSizeMenuItem.setText(Messages
					.getString("PMachineJUI.StackSizeNewMenuName"));
			jInputStackSizeMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							int result;
							try {
								String res = JOptionPane
										.showInputDialog(Messages
												.getString("PMachineJUI.StackSizeNewDialogText"));
								result = Integer.parseInt(res);
							} catch (NumberFormatException e1) {
								result = 200;
							}
							itsEventHandler.newStackSizeEvent(result);
						}
					});
		}
		return jInputStackSizeMenuItem;
	}

	/**
	 * This method initializes jStackSize200MenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJStackSize200MenuItem() {
		if (jStackSize200MenuItem == null) {
			jStackSize200MenuItem = new JMenuItem();
			jStackSize200MenuItem.setText(Messages
					.getString("PMachineJUI.StackSize200MenuName"));
			jStackSize200MenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.newStackSizeEvent(200);
						}
					});
		}
		return jStackSize200MenuItem;
	}

	/**
	 * This method initializes jStackSize500MenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJStackSize500MenuItem() {
		if (jStackSize500MenuItem == null) {
			jStackSize500MenuItem = new JMenuItem();
			jStackSize500MenuItem.setText(Messages
					.getString("PMachineJUI.StackSize500MenuName"));
			jStackSize500MenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.newStackSizeEvent(500);
						}
					});
		}
		return jStackSize500MenuItem;
	}

	/**
	 * This method initializes jStackSize1000MenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJStackSize1000MenuItem() {
		if (jStackSize1000MenuItem == null) {
			jStackSize1000MenuItem = new JMenuItem();
			jStackSize1000MenuItem.setText(Messages
					.getString("PMachineJUI.StackSize1000MenuName"));
			jStackSize1000MenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.newStackSizeEvent(1000);
						}
					});
		}
		return jStackSize1000MenuItem;
	}

	/**
	 * This method initializes jResetMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJResetMenuItem() {
		if (jResetMenuItem == null) {
			jResetMenuItem = new JMenuItem();
			jResetMenuItem.setText(Messages
					.getString("PMachineJUI.ResetMenuName"));
			jResetMenuItem.setIcon(ExecutionEnvironment.getResetIcon());
			jResetMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.resetEvent();
						}
					});
		}
		return jResetMenuItem;
	}

	/**
	 * Enable or Disable execution buttons
	 */
	public void setExecutionEnabled(boolean b) {
		getJExecutionMenu().setEnabled(b);
		if (!b)
			getJUndoOnesMenuItem().setEnabled(b);
		if (!b)
			getUndoOnesButton().setEnabled(b);
		// TODO delete it
		// getJOptionsMenu().setEnabled(b);
		// getResetButton().setEnabled(b);
		// getRunButton().setEnabled(b);
		getStepButton().setEnabled(b);
		getBigStepButton().setEnabled(b);
		/*
		 * getAddBreakpointButton().setEnabled(b);
		 * getRemoveBreakpointButton().setEnabled(b);
		 * getRemoveAllBreakpointsButton().setEnabled(b);
		 * getAddWatchButton().setEnabled(b);
		 * getRemoveWatchButton().setEnabled(b);
		 * getRemoveAllWatchsButton().setEnabled(b);
		 */
	}

	/**
	 * Enable or Disable Undo button
	 */
	public void setUndoEnabled(boolean b) {
		getJUndoOnesMenuItem().setEnabled(b);
		getUndoOnesButton().setEnabled(b);
	}

	/**
	 * @param b
	 */
	public void setFileReloadingEnabled(boolean b) {
		getJReloadFileMenuItem().setEnabled(b);
		getReloadButton().setEnabled(b);

	}

	/**
	 * This method initializes resetButton
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getResetButton() {
		if (resetButton == null) {
			resetButton = new JButton();

			resetButton.setToolTipText(Messages
					.getString("PMachineJUI.ResetTipText"));
			resetButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					itsEventHandler.resetEvent();
				}
			});
			if (ExecutionEnvironment.getResetIcon() == null) {
				resetButton.setText(Messages
						.getString("PMachineJUI.ResetMenuName"));
			} else {
				resetButton.setIcon(ExecutionEnvironment.getResetIcon());
			}
		}
		return resetButton;
	}

	/**
	 * This method initializes jButton3
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBigStepButton() {
		if (bigStepButton == null) {
			bigStepButton = new JButton();
			bigStepButton.setToolTipText(Messages
					.getString("PMachineJUI.BigStepTipText"));
			bigStepButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.bigStepEvent();
						}
					});
			if (ExecutionEnvironment.getBigStepIcon() == null) {
				bigStepButton.setText(Messages
						.getString("PMachineJUI.BigStepMenuName"));
			} else {
				bigStepButton.setIcon(ExecutionEnvironment.getBigStepIcon());
			}
		}
		return bigStepButton;
	}

	/**
	 * This method initializes AddBreakpoint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddBreakpointButton() {
		if (addBreakpointButton == null) {
			addBreakpointButton = new JButton();
			addBreakpointButton.setToolTipText(Messages
					.getString("PMachineJUI.BreakpointsAddTipText"));
			addBreakpointButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.addBreakpointEvent();
						}
					});
			if (ExecutionEnvironment.getAddBreakpointIcon() == null) {
				addBreakpointButton.setText(Messages
						.getString("PMachineJUI.BreakpointsAddMenuName"));
			} else {
				addBreakpointButton.setIcon(ExecutionEnvironment
						.getAddBreakpointIcon());
			}
		}
		return addBreakpointButton;
	}

	/**
	 * This method initializes RemoveBreakpoint
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getRemoveBreakpointButton() {
		if (removeBreakpointButton == null) {
			removeBreakpointButton = new JButton();
			removeBreakpointButton.setToolTipText(Messages
					.getString("PMachineJUI.BreakpointsRemoveTipText"));
			removeBreakpointButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.removeBreakpointEvent();
						}
					});
			if (ExecutionEnvironment.getRemoveBreakpointIcon() == null) {
				removeBreakpointButton.setText(Messages
						.getString("PMachineJUI.BreakpointsRemoveMenuName"));
			} else {
				removeBreakpointButton.setIcon(ExecutionEnvironment
						.getRemoveBreakpointIcon());
			}
		}
		return removeBreakpointButton;
	}

	/**
	 * This method initializes RemoveAllBreakpoints
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getRemoveAllBreakpointsButton() {
		if (removeAllBreakpointsButton == null) {
			removeAllBreakpointsButton = new JButton();
			removeAllBreakpointsButton.setToolTipText(Messages
					.getString("PMachineJUI.BreakpointsRemoveAllTipText"));
			removeAllBreakpointsButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.removeAllBreakpointsEvent();
						}
					});
			if (ExecutionEnvironment.getRemoveAllBreakpointsIcon() == null) {
				removeAllBreakpointsButton.setText(Messages
						.getString("PMachineJUI.BreakpointsRemoveAllMenuName"));
			} else {
				removeAllBreakpointsButton.setIcon(ExecutionEnvironment
						.getRemoveAllBreakpointsIcon());
			}
		}
		return removeAllBreakpointsButton;
	}

	/**
	 * This method initializes AddWatch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getAddWatchButton() {
		if (addWatchButton == null) {
			addWatchButton = new JButton();
			addWatchButton.setToolTipText(Messages
					.getString("PMachineJUI.WatchsAddTipText"));
			addWatchButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.addWatchEvent();
						}
					});
			if (ExecutionEnvironment.getAddWatchIcon() == null) {
				addWatchButton.setText(Messages
						.getString("PMachineJUI.WatchsAddMenuName"));
			} else {
				addWatchButton.setIcon(ExecutionEnvironment.getAddWatchIcon());
			}
		}
		return addWatchButton;
	}

	/**
	 * This method initializes RemoveWatch
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getRemoveWatchButton() {
		if (removeWatchButton == null) {
			removeWatchButton = new JButton();
			removeWatchButton.setToolTipText(Messages
					.getString("PMachineJUI.WatchsRemoveTipText"));
			removeWatchButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.removeWatchEvent();
						}
					});
			if (ExecutionEnvironment.getRemoveWatchIcon() == null) {
				removeWatchButton.setText(Messages
						.getString("PMachineJUI.WatchsRemoveMenuName"));
			} else {
				removeWatchButton.setIcon(ExecutionEnvironment
						.getRemoveWatchIcon());
			}
		}
		return removeWatchButton;
	}

	/**
	 * This method initializes RemoveAllWatchs
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getRemoveAllWatchsButton() {
		if (removeAllWatchsButton == null) {
			removeAllWatchsButton = new JButton();
			removeAllWatchsButton.setToolTipText(Messages
					.getString("PMachineJUI.WatchsRemoveAllTipText"));
			removeAllWatchsButton
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.removeAllWatchsEvent();
						}
					});
			if (ExecutionEnvironment.getRemoveAllWatchsIcon() == null) {
				removeAllWatchsButton.setText(Messages
						.getString("PMachineJUI.WatchsRemoveAllMenuName"));
			} else {
				removeAllWatchsButton.setIcon(ExecutionEnvironment
						.getRemoveAllWatchsIcon());
			}
		}
		return removeAllWatchsButton;
	}

	/**
	 * This method initializes jMenuHelp
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenuHelp() {
		if (jMenuHelp == null) {
			jMenuHelp = new JMenu();
			jMenuHelp.setText(Messages.getString("PMachineJUI.HelpMenuName"));
			jMenuHelp
					.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
			jMenuHelp.add(getJAboutMenuItem());
		}
		return jMenuHelp;
	}

	/**
	 * This method initializes jAboutMenuItem
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJAboutMenuItem() {
		if (jAboutMenuItem == null) {
			jAboutMenuItem = new JMenuItem();
			jAboutMenuItem.setText(Messages
					.getString("PMachineJUI.AboutMenuName"));
			jAboutMenuItem.setIcon(ExecutionEnvironment.getAboutIcon());
			jAboutMenuItem
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							itsEventHandler.aboutEvent();
						}
					});
		}
		return jAboutMenuItem;
	}

	/**
	 * @return Returns the openButton.
	 */
	private JButton getOpenButton() {
		if (openButton == null) {
			openButton = new JButton();
			openButton.setToolTipText(Messages
					.getString("PMachineJUI.OpenTipText"));
			if (ExecutionEnvironment.getOpenFileIcon() == null) {
				openButton.setText(Messages
						.getString("PMachineJUI.OpenMenuName"));
			} else {
				openButton.setIcon(ExecutionEnvironment.getOpenFileIcon());
			}
			openButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					itsEventHandler.loadFileEvent();
				}
			});
		}
		return openButton;
	}

	/**
	 * @return Returns the openButton.
	 */
	private JButton getReloadButton() {
		if (reloadButton == null) {
			reloadButton = new JButton();
			reloadButton.setToolTipText(Messages
					.getString("PMachineJUI.ReloadTipText"));
			if (ExecutionEnvironment.getReloadFileIcon() == null) {
				reloadButton.setText(Messages
						.getString("PMachineJUI.ReloadMenuName"));
			} else {
				reloadButton.setIcon(ExecutionEnvironment.getReloadFileIcon());
			}
			reloadButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					itsEventHandler.reloadFileEvent();
				}
			});
		}
		return reloadButton;
	}
}
