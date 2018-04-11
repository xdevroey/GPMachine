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
 * \package pmachine
 *
 * \brief All run-time related classes.
 *
 * Groups all run-time related classes.  Those
 * are further structured into instructions (pmachine.instructions),
 * exceptions raised by the PMachine (pmachine.exceptions) and the
 * graphical user interface (pmachine.ui).
 */
package pmachine;

import java.io.FileNotFoundException;
import java.io.IOException;

import pmachine.exceptions.DivByZeroException;
import pmachine.exceptions.IndexOutOfBoundsException;
import pmachine.exceptions.InvalidPInstructionException;
import pmachine.exceptions.RuntimeException;
import pmachine.exceptions.StackException;
import pmachine.exceptions.WrongTypeException;
import pmachine.instructions.PInstruction;
import pmachine.ui.AppController;
import pmachine.ui.IAppController;

/**
 * \class PMachine
 *
 * \brief The pseudo-machine state.
 *
 * This class represents a PMachine state. A PMachine state groups all the
 * information needed to interpret a PCode program.
 *
 * The relevant information for executing a PMachine are \li \c store: A
 * IPMemory, which contains the data manipulated by the program being executed.
 * In PMachines, the store is a stack. \li \c program: An IPProgramMemory, which
 * contains the sequence of instructions (PCode statements) being executed. This
 * program retains the value of the p-counter (program counter). The PMachine
 * contains no register. \li \c reader: A PReader, which is an input bus.
 * Through this channel, the virtual machine can read integers. \li \c writer: A
 * PWriter, which is an output bus. The virtual machine writes its output on
 * this bus.
 */
public class PMachine implements Cloneable {

    /**
     * The data store of this virtual machine.
     */
    private IPMemory store;

    /**
     * The instruction memory.
     */
    private IPProgramMemory program;
    /**
     * The input bus.
     */
    private PReader reader;
    /**
     * The output bus.
     */
    private PWriter writer;

    /**
     * true iff this pmachine is halted. The machine halts when
     */
    private boolean halted = false;

    /**
     * \pre program and store are not null
     *
     * \post \li store has been reset, \li program has been reset, \li halted is
     * false.
     */
    public synchronized void reset() {
        assert (program != null);
        assert (store != null);
        program.reset();
        store.reset();
        halted = false;
    }

    /**
     * \pre store is not null
     *
     * @return the maximal size of the store. (\see IPMemory.getMaxSize).
     */
    public int getMemorySize() {
        assert (store != null);
        return store.getMaxSize();
    }

    /**
     * \pre program is not null and lab is a valid label program (i.e. there is
     * an address labeled by lab).
     *
     * \post the PC is now at the address labeled by lab.
     *
     * @param lab a valid label in program, referring to an existing line.
     * @throws RuntimeException if lab is not a valid label.
     */
    public void jumpToLabel(String lab) throws RuntimeException {
        assert (program != null);
        program.jumpToLabel(lab);
    }

    /**
     * \pre program is not null and addr is a correct address of program memory
     * (addr >= 0 and addr < max address).
     *
     * \post the PC is now at addr.
     *
     * @param addr a correct address in program
     * @throws RuntimeException if addr is not a correct address.
     */
    public void jumpToAddress(StackElemPCAddr addr) throws RuntimeException {
        program.jumpToAddress(addr);
    }

    /**
     * Creates a new PMachine instance, with an empty store of SSize cells. This
     * machine will read its input from r and write its output on w . Reads its
     * PCode from FName (i.e. program contains FName).
     *
     * @throws IOException if reading from FName fails.
     * @throws FileNotFoundException if FName does not refer to a valid File.
     * @throws InvalidPInstructionException if loading FName fails, because
     * FName does not contain a valid PCode program.
     */
    public PMachine(String FName, int SSize, PReader r, PWriter w)
            throws InvalidPInstructionException, FileNotFoundException,
            IOException {
        store = new PMemory(SSize);
        program = new PProgramMemory();
        program.loadProgram(FName);
        // StepByStep = false;
        reader = r;
        writer = w;
    }

    /**
     * Constructor for clone method
     */
    private PMachine(IPMemory store, IPProgramMemory program, PReader reader,
            PWriter writer) {
        this.store = (IPMemory) ((PMemory) store).clone();
        this.program = (IPProgramMemory) ((PProgramMemory) program).clone();
        this.reader = reader;
        this.writer = writer;
    }

    /**
     * PMachine clone method
     */
    public Object clone() {
        return new PMachine(this.store, this.program, this.reader, this.writer);
    }

    /**
     * Tells the pmachine that it is now halted.
     */
    public void halt() {
        setHalted(true);
    }

    /**
     * @return the output bus.
     */
    public PWriter getOutput() {
        return writer;
    }

    /**
     * @return the input bus.
     */
    public PReader getInput() {
        return reader;
    }

    /**
     * \pre program, store, input and output are not null
     *
     * Runs the PMachine on the provided PCode. Will stop when a stp statement
     * is executed.
     *
     * @throws RuntimeException if a runtime error occurs.
     */
    protected void analyse() throws RuntimeException {
        assert (program != null);
        assert (store != null);
        assert (reader != null);
        assert (writer != null);
        while (!isHalted()) {
            runNextInstr();
        }
    }

    /**
     * Fetches the next instruction and executes it.
     */
    public synchronized void runNextInstr() throws RuntimeException {
        getNextInstr().execute(this);
        if (ExecutionEnvironment.getVerbosityLevel() > 0) // TODO: Avoid explicit reference to System.out.println for
        // debugging.
        {
            System.out.println(getStackPresentation().asDisplayableVector());
        }
    }

    /**
     * Fetches the next instruction (i.e. the instruction at address PC in
     * program memory)
     */
    public PInstruction getNextInstr() throws RuntimeException {
        return program.getInstruction();
        // throw new
        // InvalidPInstructionException(Messages.getString("PMachine.EOFReachedError"));
    }

    public IPMemory getPMemory() {
        return store;
    }

    public IListDisplayServer getStackPresentation() {
        return store.getStackPresentation();
    }

    public IListDisplayServer getHeapPresentation() {
        return store.getHeapPresentation();
    }

    /**
     * Returns the value of the MP registry.
     */
    public int getMP() {
        return store.getMP();
    }

    /**
     * Returns the PCode executed by the machine, as a displayable Vector.
     */
    // TODO: Delete it, too slow, never use it !
    /*
	 * public Vector getInstr(){ return program.asDisplayableVector(); }
     */
    /**
     * Returns the value of the PC registry.
     */
    public int getPC() {
        return program.getPC();
    }

    /**
     * Returns the value of the SP registry.
     */
    public int getSP() {
        return store.getSP();
    }

    /**
     * Sets the SP registry to val.
     *
     * @param val the new value of SP registry.
     * @throws StackException if val is not a correct stack value (i.e. less
     * than zero or beyond current SP).
     */
    public void setSP(int val) throws StackException {
        store.setSP(val);
    }

    /**
     * Returns the value of the EP registry
     */
    public int getEP() {
        return store.getHP();
    }

    // TODO: delete it
    /*
	 * Runs the PMachine in CRT mode. The program is run with no breaks. The
	 * user will be prompted, on stdin, when input is needed.
     */
 /*
	 * public static void runPMachine(String fname, int stackSize, boolean sBs)
	 * throws FileNotFoundException, IOException, RuntimeException { SimpleCRT
	 * crt = new SimpleCRT(); PMachine pm = new
	 * PMachine(fname,stackSize,crt,crt); //pm.StepByStep = sBs; if(!sBs)
	 * pm.analyse(); else pm.runNextInstr(); }
     */
    public static void enterPoint(String args[]) throws FileNotFoundException,
            IOException, RuntimeException {
        ExecutionEnvironment.parseArguments(args);

        if (ExecutionEnvironment.isLicenseShouldBePrinted()) {
            ExecutionEnvironment.printLicense();
        }

        if (ExecutionEnvironment.isGuiEnabled()) {
            // Runs with the GUI ==> disable the -v option
            // and launch the GUI
            IAppController application = new AppController();
            application.launchGUI();
        } else {
            // Runs in CRT mode.
            // Create I/O functionnalities for CRT mode and
            // a new PMachine.
            SimpleCRT crt = new SimpleCRT();
            PMachine pm = null;
            try {
                // Loading file
                pm = new PMachine(ExecutionEnvironment.getFilename(),
                        ExecutionEnvironment.getStackSize(), crt, crt);
            } catch (InvalidPInstructionException e) {
                System.err.println(Messages
                        .getString("PMachine.InvalidInstructionError"));
                System.err.println(e.getMessage());
                // System.exit(1);
                exitInstructionError("error : Unknown Instruction");
            }
            try {
                // launch code execution
                pm.analyse();
            } catch (DivByZeroException e) {
                System.err.println(Messages
                        .getString("PMachine.DivByZeroError"));
                System.err.println(e.getMessage());
                // System.exit(1);
                exitInstructionError(((PInstruction) pm.program.getElementAt(pm
                        .getPC() - 1)).getName());
            } catch (InvalidPInstructionException e) {
                System.err.println(Messages
                        .getString("PMachine.InvalidInstructionError"));
                System.err.println(e.getMessage());
                // System.exit(1);
                exitInstructionError(((PInstruction) pm.program.getElementAt(pm
                        .getPC() - 1)).getName());
            } catch (IndexOutOfBoundsException e) {
                System.err.println(Messages
                        .getString("PMachine.IndexOutOfBoundsError"));
                System.err.println(Messages
                        .getString("PMachine.IndexOutOfBoundsWarning"));
                System.err.println(e.getMessage());
                // System.exit(0);
                exitInstructionError(((PInstruction) pm.program.getElementAt(pm
                        .getPC() - 1)).getName());
            } catch (WrongTypeException e) {
                System.err.println(Messages
                        .getString("PMachine.WrongTypeError"));
                System.err.println(e.getMessage());
                // System.exit(1);
                exitInstructionError(((PInstruction) pm.program.getElementAt(pm
                        .getPC() - 1)).getName());
            } catch (StackException e) {
                System.err.println(Messages
                        .getString("PMachine.StackExceptionError"));
                System.err.println(e.getMessage());
                // System.exit(1);
                exitInstructionError(((PInstruction) pm.program.getElementAt(pm
                        .getPC() - 1)).getName());
            }
        }
    }

    public static void exitInstructionError(String name) {

        // Error codes are defined here
        if (name.equals("add")) {
            System.exit(2);
        }
        if (name.equals("and")) {
            System.exit(3);
        }
        if (name.equals("chk")) {
            System.exit(4);
        }
        if (name.equals("cup")) {
            System.exit(5);
        }
        if (name.equals("cupi")) {
            System.exit(6);
        }
        if (name.equals("dec")) {
            System.exit(7);
        }
        if (name.equals("define")) {
            System.exit(8);
        }
        if (name.equals("div")) {
            System.exit(9);
        }
        if (name.equals("dpl")) {
            System.exit(10);
        }
        if (name.equals("equ")) {
            System.exit(11);
        }
        if (name.equals("fjp")) {
            System.exit(12);
        }
        if (name.equals("geq")) {
            System.exit(13);
        }
        if (name.equals("grt")) {
            System.exit(14);
        }
        if (name.equals("inc")) {
            System.exit(15);
        }
        if (name.equals("ind")) {
            System.exit(16);
        }
        if (name.equals("ixa")) {
            System.exit(17);
        }
        if (name.equals("ixj")) {
            System.exit(18);
        }
        if (name.equals("lda")) {
            System.exit(19);
        }
        if (name.equals("ldc")) {
            System.exit(20);
        }
        if (name.equals("ldd")) {
            System.exit(21);
        }
        if (name.equals("ldo")) {
            System.exit(22);
        }
        if (name.equals("leq")) {
            System.exit(23);
        }
        if (name.equals("les")) {
            System.exit(24);
        }
        if (name.equals("lod")) {
            System.exit(25);
        }
        if (name.equals("movd")) {
            System.exit(26);
        }
        if (name.equals("movs")) {
            System.exit(27);
        }
        if (name.equals("mst")) {
            System.exit(28);
        }
        if (name.equals("mstf")) {
            System.exit(29);
        }
        if (name.equals("mul")) {
            System.exit(30);
        }
        if (name.equals("neg")) {
            System.exit(31);
        }
        if (name.equals("neq")) {
            System.exit(32);
        }
        if (name.equals("new")) {
            System.exit(33);
        }
        if (name.equals("not")) {
            System.exit(34);
        }
        if (name.equals("or")) {
            System.exit(35);
        }
        if (name.equals("pop")) {
            System.exit(36);
        }
        if (name.equals("prin")) {
            System.exit(37);
        }
        if (name.equals("read")) {
            System.exit(38);
        }
        if (name.equals("retf")) {
            System.exit(39);
        }
        if (name.equals("retp")) {
            System.exit(40);
        }
        if (name.equals("sli")) {
            System.exit(41);
        }
        if (name.equals("smp")) {
            System.exit(42);
        }
        if (name.equals("sro")) {
            System.exit(43);
        }
        if (name.equals("ssp")) {
            System.exit(44);
        }
        if (name.equals("sto")) {
            System.exit(45);
        }
        if (name.equals("stp")) {
            System.exit(46);
        }
        if (name.equals("str")) {
            System.exit(47);
        }
        if (name.equals("sub")) {
            System.exit(48);
        }
        if (name.equals("ujp")) {
            System.exit(49);
        }
        if (name.equals("error : Unknown Instruction")) {
            System.exit(50);
        }
        // normaly program finished before this line ;)
        // if not it's GPMachine error
        System.exit(1);
    }

    /**
     * @param i
     */
    public void setMP(int i) {
        store.setMP(i);
    }

    /**
     * @return Returns the halted.
     */
    public boolean isHalted() {
        return halted;
    }

    /**
     * @param halted The halted to set.
     */
    public void setHalted(boolean halted) {
        this.halted = halted;
    }

    /**
     * @return
     */
    public IPProgramMemory getProgramMemory() {
        return program;
    }
}
