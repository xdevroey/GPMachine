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
package pmachine;

import java.awt.Color;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/*
 * This is "gpmachine" a PCode interpreter, by Yiti Group, rewritten by Yves
 * Bontemps Copyright (C) 2002-2004 Yves Bontemps
 * 
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place - Suite 330, Boston, MA 02111-1307, USA.
 * 
 * Contact: Yves Bontemps CS Dept - University of Namur rue Grandgagnage, 21
 * B5000 Namur Belgium ybo@info.fundp.ac.be
 * 
 */

 /*
 * Created on 27-juil.-2004
 * 
 */
/**
 * @author ybo
 *
 * \brief Runtime options as initialized by CLI parameters.
 *
 * This class embeds all information about user-selected runtime options. It
 * also provides methods to parse command-line parameters to initialize those
 * runtime options properly.
 */
public class ExecutionEnvironment {

    /**
     * Stoped the execution or not
     */
    private static boolean stopPMachine = false;

    public static void setStopPMachineEnabled(boolean s) {
        stopPMachine = s;
    }

    public static boolean isStopPMachineEnabled() {
        return stopPMachine;
    }

    /**
     * The name of the file containing the PCode to be executed
     */
    private static String filename = null;

    /**
     * The maximum size of the memory, in number of cells.
     */
    private static int stackSize = 200;

    /**
     * True iff the Swing Graphical User Interface must be displayed.
     */
    private static boolean guiEnabled = true;

    /**
     * The level of verbosity. If larger than 0, debug, information and warning
     * messages shall be displayed.
     */
    private static int verbosityLevel = 0;

    /**
     * True iff the GPL shall be displayed when the PMachine is launched.
     */
    // TODO: change to true in Final Version
    private static boolean licenseShouldBePrinted = true;

    /* Constants representing the colors used in the GUI mode */
    /**
     * The color to highlight instructions with breakpoints
     */
    private static Color BREAK_INSTR;
    /**
     * The color to highlight Stack ans Heap with watchs
     */
    private static Color WATCH_INSTR;
    /**
     * The color to use on the current instruction if it is a breakpoint TODO
     * remove and use color blending.
     */
    private static Color BREAK_CUR_INSTR;
    /**
     * The color to use on the current stack if it is a watch TODO remove and
     * use color blending.
     */
    private static Color WATCH_CUR_INSTR;
    /**
     * The color to use on the current instruction if it is not a breakpoint
     */
    private static Color CUR_INSTR;
    /**
     * The color to use on the stack cell at MP
     */
    private static Color MP_LINE;
    /**
     * The color to use as a background on call frame cells
     */
    private static Color CALL_BG;

    /*
	 * The various icons used in the UI. Note: to change them, modify the
	 * strings in the properties file
     */
    private static Icon UNDO_ONES_ICON = null;
    private static Icon EXECUTE_ICON = null;
    private static Icon RESET_ICON = null;
    private static Icon BIG_STEP_ICON = null;
    private static Icon NEXT_ICON = null;
    private static Icon OPEN_FILE_ICON = null;
    private static Icon RELOAD_FILE_ICON = null;
    private static Icon BREAKPOINT_ICON = null;
    private static Icon ADD_BREAKPOINT_ICON = null;
    private static Icon REMOVE_BREAKPOINT_ICON = null;
    private static Icon REMOVE_ALL_BREAKPOINT_ICON = null;
    private static Icon WATCH_ICON = null;
    private static Icon ADD_WATCH_ICON = null;
    private static Icon REMOVE_WATCH_ICON = null;
    private static Icon REMOVE_ALL_WATCH_ICON = null;
    private static Icon EXIT_ICON = null;
    private static Icon ABOUT_ICON = null;

    private static Icon loadIconFromResource(String resourceName) {
        String resource = Messages.getString(resourceName);
        URL icon = Runtime.getRuntime().getClass().getResource(resource);
        return new ImageIcon(icon); //$NON-NLS-1$
    }

    /**
     * @return the Icon for the "about" button.
     */
    public final static Icon getAboutIcon() {
        if (ABOUT_ICON == null) {
            ABOUT_ICON = loadIconFromResource("ExecutionEnvironment.AboutIconFile");
        }
        return ABOUT_ICON;
    }

    /**
     * @return the Icon for "Undo Ones" button
     */
    public final static Icon getUndoOnesIcon() {
        if (UNDO_ONES_ICON == null) {
            UNDO_ONES_ICON = loadIconFromResource("ExecutionEnvironment.UndoOnesIconFile");
        }
        return UNDO_ONES_ICON;
    }

    /**
     * @return the Icon for "Run PCode" button
     */
    public final static Icon getRunIcon() {
        if (EXECUTE_ICON == null) {
            EXECUTE_ICON = loadIconFromResource("ExecutionEnvironment.ExecuteIconFile");
        }
        return EXECUTE_ICON;
    }

    /**
     * @return the Icon for "Next Step" button
     */
    public final static Icon getNextIcon() {
        if (NEXT_ICON == null) {
            NEXT_ICON = loadIconFromResource("ExecutionEnvironment.RunIconFile");
        }
        return NEXT_ICON;
    }

    /**
     * @return the Icon for "Next n Steps" button
     */
    public final static Icon getBigStepIcon() {
        if (BIG_STEP_ICON == null) {
            BIG_STEP_ICON = loadIconFromResource("ExecutionEnvironment.BigStepIconFile");
        }
        return BIG_STEP_ICON;
    }

    /**
     * @return the Icon for "Reset" button
     */
    public final static Icon getResetIcon() {
        if (RESET_ICON == null) {
            RESET_ICON = loadIconFromResource("ExecutionEnvironment.ResetIconFile");
        }
        return RESET_ICON;
    }

    /**
     * @return the Icon for "Open File" button
     */
    public final static Icon getOpenFileIcon() {
        if (OPEN_FILE_ICON == null) {
            OPEN_FILE_ICON = loadIconFromResource("ExecutionEnvironment.OpenFileIconFile");
        }
        return OPEN_FILE_ICON;
    }

    /**
     * @return the Icon for "Reload File" button
     */
    public final static Icon getReloadFileIcon() {
        if (RELOAD_FILE_ICON == null) {
            RELOAD_FILE_ICON = loadIconFromResource("ExecutionEnvironment.ReloadFileIconFile");
        }
        return RELOAD_FILE_ICON;
    }

    /**
     * @return the Icon for "Breakpoint" button
     */
    public final static Icon getBreakpointIcon() {
        if (BREAKPOINT_ICON == null) {
            BREAKPOINT_ICON = loadIconFromResource("ExecutionEnvironment.BreakpointIconFile");
        }
        return BREAKPOINT_ICON;
    }

    /**
     * @return the Icon for "Add Breakpoint" button
     */
    public final static Icon getAddBreakpointIcon() {
        if (ADD_BREAKPOINT_ICON == null) {
            ADD_BREAKPOINT_ICON = loadIconFromResource("ExecutionEnvironment.AddBreakpointIconFile");
        }
        return ADD_BREAKPOINT_ICON;
    }

    /**
     * @return the Icon for "Remove Breakpoint" button
     */
    public final static Icon getRemoveBreakpointIcon() {
        if (REMOVE_BREAKPOINT_ICON == null) {
            REMOVE_BREAKPOINT_ICON = loadIconFromResource("ExecutionEnvironment.RemoveBreakpointIconFile");
        }
        return REMOVE_BREAKPOINT_ICON;
    }

    /**
     * @return the Icon for "Remove All Breakpoints" button
     */
    public final static Icon getRemoveAllBreakpointsIcon() {
        if (REMOVE_ALL_BREAKPOINT_ICON == null) {
            REMOVE_ALL_BREAKPOINT_ICON = loadIconFromResource("ExecutionEnvironment.RemoveAllBreakpointsIconFile");
        }
        return REMOVE_ALL_BREAKPOINT_ICON;
    }

    /**
     * @return the Icon for "Watch" button
     */
    public final static Icon getWatchIcon() {
        if (WATCH_ICON == null) {
            WATCH_ICON = loadIconFromResource("ExecutionEnvironment.WatchIconFile");
        }
        return WATCH_ICON;
    }

    /**
     * @return the Icon for "Add Watch" button
     */
    public final static Icon getAddWatchIcon() {
        if (ADD_WATCH_ICON == null) {
            ADD_WATCH_ICON = loadIconFromResource("ExecutionEnvironment.AddWatchIconFile");
        }
        return ADD_WATCH_ICON;
    }

    /**
     * @return the Icon for "Remove Watch" button
     */
    public final static Icon getRemoveWatchIcon() {
        if (REMOVE_WATCH_ICON == null) {
            REMOVE_WATCH_ICON = loadIconFromResource("ExecutionEnvironment.RemoveWatchIconFile");
        }
        return REMOVE_WATCH_ICON;
    }

    /**
     * @return the Icon for "Remove All Watchs" button
     */
    public final static Icon getRemoveAllWatchsIcon() {
        if (REMOVE_ALL_WATCH_ICON == null) {
            REMOVE_ALL_WATCH_ICON = loadIconFromResource("ExecutionEnvironment.RemoveAllWatchsIconFile");
        }
        return REMOVE_ALL_WATCH_ICON;
    }

    /**
     * @return the Icon for "exit button"
     */
    public final static Icon getExitIcon() {
        if (EXIT_ICON == null) {
            // do nothing, no exit Icon at the moment
        }
        return EXIT_ICON;
    }

    /**
     * @return the Color used to show breakpoints in PCode
     */
    public final static Color getBreakPointColor() {
        if (BREAK_INSTR == null) {
            BREAK_INSTR = Color.red;
        }
        return BREAK_INSTR;
    }

    /**
     * @return the Color used to show watchs in Stack and Heap
     */
    public final static Color getWatchColor() {
        if (WATCH_INSTR == null) {
            WATCH_INSTR = Color.red;
        }
        return WATCH_INSTR;
    }

    /**
     * @return the Color used to show breakpoints, when the PC is at this
     * breakpoint.
     */
    public final static Color getBreakPointCursorColor() {
        if (BREAK_CUR_INSTR == null) {
            BREAK_CUR_INSTR = Color.orange;
        }
        return BREAK_CUR_INSTR;
    }

    /**
     * @return the Color used to show watchs, when the SP or EP are at this
     * watch.
     */
    public final static Color getWatchCursorColor() {
        if (WATCH_CUR_INSTR == null) {
            WATCH_CUR_INSTR = Color.orange;
        }
        return WATCH_CUR_INSTR;
    }

    /**
     * @return the Color used to show the instruction at which the PC is.
     */
    public final static Color getPCColor() {
        if (CUR_INSTR == null) {
            CUR_INSTR = Color.yellow;
        }
        return CUR_INSTR;
    }

    /**
     *
     * @return the Color used to show the memory cell at which the MP is.
     */
    public final static Color getMPColor() {
        if (MP_LINE == null) {
            MP_LINE = Color.green;
        }
        return MP_LINE;
    }

    /**
     *
     * @return the Color used to show that a memory cell belongs to a call frame
     * (i.e. is one of the five cells that are automatically allocated when a
     * procedure call is made).
     */
    public final static Color getCallFrameBackground() {
        if (CALL_BG == null) {
            CALL_BG = Color.lightGray;
        }
        return CALL_BG;
    }

    /**
     * @return the current version of GPMachine
     */
    public static String getVersion() {
        return Messages.getString("PMachine.VersionNumber");
    }

    /**
     * @return the synopsis of command-line options
     */
    public static String getSynopsis() {
        String s = "";
        s += Messages.getString("ExecutionEnvironment.CommandLine");
        s += Messages.getString("ExecutionEnvironment.CommandLineDescription");
        // s+=
        // Messages.getString(Messages.getString("ExecutionEnvironment.OptionsDescriptionTitle"))+"\n";
        // s+=
        // Messages.getString("ExecutionEnvironment.StackSizeOptionDescription");
        // s+= "\t --stack<int>, -s<int> : see -S:<int>\n";
        // s+= "\t -H:<int> : \t set heap size to <int> (default: -H:200)\n";
        s += Messages
                .getString("ExecutionEnvironment.VerboseOptionDescription");
        s += Messages.getString("ExecutionEnvironment.GUIOptionDescription");
        s += Messages
                .getString("ExecutionEnvironment.LicenseOptionDescription");
        return s;
    }

    /**
     * prints the basic usage summary of GPMachine
     */
    public static void printUsage() {
        System.err.println(getSynopsis());
    }

    /**
     * prints license information about GPMachine on stderr
     */
    public static void printLicense() {
        System.err.println(Messages.getString("PMachine.ProgramName") + " "
                + getVersion()
                + Messages.getString("PMachine.CopyrightStatement"));
        System.err.println(Messages.getString("PMachine.NoWarranty"));
        System.err.println(Messages.getString("PMachine.ThisIsFreeSoftware"));
        System.err.println(Messages.getString("PMachine.ToRedistributeIt"));
        System.err.println();
    }

    /**
     * @param args an array of Strings containing command-line arguments of
     * GPMachine. \post the variables of ExecutionEnvironment are all
     * initialized to default values, unless they are modified by some
     * command-line argument. Then, they are initialized to the specified value.
     */
    public static void parseArguments(String args[]) {
        int i = 0;

        while (i < args.length) {
            args[i] = args[i].trim();
            if (args[i].startsWith("-S:")) {
                setStackSize(Integer.parseInt(args[i].substring(3)));
            } else if (args[i].startsWith("--stack")) {
                setStackSize(Integer.parseInt(args[i].substring(7)));
            } else if (args[i].startsWith("-s")) {
                setStackSize(Integer.parseInt(args[i].substring(2)));
            } else if (args[i].equalsIgnoreCase("-v")) {
                verbosityLevel++;
            } else if (args[i].equalsIgnoreCase("-gui")
                    || args[i].equalsIgnoreCase("--gui")
                    || args[i].equalsIgnoreCase("-g")) {
                setGuiEnabled(true);
            } else if (args[i].equalsIgnoreCase("-nogui")
                    || args[i].equalsIgnoreCase("--nogui")
                    || args[i].equalsIgnoreCase("-g")) {
                setGuiEnabled(false);
            } else if (args[i].equalsIgnoreCase("-n")
                    || args[i].equalsIgnoreCase("--no-license")) {
                setLicenseShouldBePrinted(false);
            } // TODO: delete it
            /*
			 * else if (args[i].startsWith("-H:")) //$NON-NLS-1$ {
			 * setHeapSize(Integer.parseInt(args[i].substring(3))); } else if
			 * (args[i].startsWith("--heap")){ //$NON-NLS-1$
			 * setHeapSize(Integer.parseInt(args[i].substring(6))); } else if
			 * (args[i].startsWith("-h")){ //$NON-NLS-1$
			 * setHeapSize(Integer.parseInt(args[i].substring(2))); }
             */ else if (!args[i].startsWith("-") && i == args.length - 1) {
                setFilename(args[i]);
            } else {
                printUsage();
            }
            i++;
        }
    }

    /**
     * @return Returns the filename.
     */
    public static String getFilename() {
        return filename;
    }

    /**
     * @param filename The filename to set.
     */
    public static void setFilename(String filename) {
        ExecutionEnvironment.filename = filename;
    }

    /**
     * @return Returns the guiEnabled.
     */
    public static boolean isGuiEnabled() {
        return guiEnabled;
    }

    /**
     * @param guiEnabled The guiEnabled to set.
     */
    public static void setGuiEnabled(boolean guiEnabled) {
        ExecutionEnvironment.guiEnabled = guiEnabled;
    }

    /**
     * @return Returns the licenseShouldBePrinted.
     */
    public static boolean isLicenseShouldBePrinted() {
        return licenseShouldBePrinted;
    }

    /**
     * @param licenseShouldBePrinted The licenseShouldBePrinted to set.
     */
    public static void setLicenseShouldBePrinted(boolean licenseShouldBePrinted) {
        ExecutionEnvironment.licenseShouldBePrinted = licenseShouldBePrinted;
    }

    /**
     * @return Returns the stackSize.
     */
    public static int getStackSize() {
        return stackSize;
    }

    /**
     * @param stackSize The stackSize to set.
     */
    public static void setStackSize(int stackSize) {
        ExecutionEnvironment.stackSize = stackSize;
    }

    /**
     * @return Returns the verbosityLevel.
     */
    public static int getVerbosityLevel() {
        return verbosityLevel;
    }

    /**
     * @param verbosityLevel The verbosityLevel to set.
     */
    public static void setVerbosityLevel(int verbosityLevel) {
        ExecutionEnvironment.verbosityLevel = verbosityLevel;
    }
}
