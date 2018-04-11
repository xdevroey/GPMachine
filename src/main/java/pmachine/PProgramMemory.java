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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import pmachine.exceptions.InvalidPInstructionException;
import pmachine.exceptions.RuntimeException;
import pmachine.instructions.PInstruction;
import pmachine.instructions.PInstructionBuilder;
import pmachine.instructions.PInstructionDEFINE;
import pmachine.instructions.PInstructionNull;

public class PProgramMemory implements IPProgramMemory, ListModel, Cloneable {

    /**
     * This is the p-counter (program counter), pointing on the next instruction
     * to be executed.
     */
    private int pc = 0;
    private List memoryContent = new Vector();
    private Map labelToAddress = new HashMap();

    public void incPC() throws RuntimeException {
        pc++;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.IPProgramMemory#getPC()
     */
    public int getPC() {
        return pc;
    }

    public void setPC(int val) throws RuntimeException {
        if (val >= memoryContent.size() || val < 0) {
            throw new InvalidPInstructionException(Messages
                    .getString("PMachine.JumpOutsiteProgramMemoBounds"));
        }
        pc = val;
    }

    public void jumpToAddress(int addr) throws RuntimeException {
        setPC(addr);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.IPProgramMemory#jumpToAddress(pmachine.StackElemAddr)
     */
    public void jumpToAddress(StackElemPCAddr address) throws RuntimeException {
        jumpToAddress(address.intValue());
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.IPProgramMemory#jumpToLabel(java.lang.String)
     */
    public void jumpToLabel(String label) throws RuntimeException {
        Integer addr = (Integer) labelToAddress.get(label);
        if (addr == null) {
            throw new InvalidPInstructionException(Messages
                    .getString("PMachine.JumpToUnknownAddress"));
        }
        jumpToAddress(addr.intValue());
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.IPProgramMemory#getInstruction()
     */
    public PInstruction getInstruction() throws RuntimeException {
        if (pc >= memoryContent.size()) {
            throw new InvalidPInstructionException(Messages
                    .getString("PMachine.JumpOutsiteProgramMemoBounds"));
        }
        PInstruction instr = (PInstruction) memoryContent.get(pc);
        incPC();
        return instr;
    }

    public void reset() {
        pc = 0;
    }

    /**
     * Return PCAddr from startCount + executable instrNumber +
     * nullInstructions, so return could be >= startCount + instrNumber
     *
     * @param startCount, instrNumber
     */
    public int countInstructions(int startCount, int instrNumber)
            throws RuntimeException {
        int nullInstructions = 0;
        int i = 0;
        if (instrNumber > 0) {
            while (i < instrNumber) {
                if (startCount + i + nullInstructions >= memoryContent.size()) {
                    throw new InvalidPInstructionException(Messages
                            .getString("PMachine.JumpOutsiteProgramMemoBounds"));
                } else if (memoryContent.get(startCount + i + nullInstructions) instanceof PInstructionNull) {
                    nullInstructions++;
                } else {
                    i++;
                }
            }
            return startCount + instrNumber - 1 + nullInstructions;
        } else if (instrNumber < 0) {
            while (i > instrNumber) {
                if (startCount + i - nullInstructions < 0) {
                    throw new InvalidPInstructionException(Messages
                            .getString("PMachine.JumpOutsiteProgramMemoBounds"));
                } else if (memoryContent.get(startCount + i - nullInstructions) instanceof PInstructionNull) {
                    nullInstructions++;
                } else {
                    i--;
                }
            }
            return startCount + instrNumber + 1 - nullInstructions;
        }
        return startCount;
    }

    /**
     * Delete Empty lines at the end of P-Code
     *
     * @param pCodeVertor
     */
    private List deleteEmptyLines(List pCodeVertor) {
        int i = pCodeVertor.size();
        while (pCodeVertor.get(i - 1) instanceof PInstructionNull) {
            // Delete only if it's begins with Empty : "", not delete comments
            if ((((PInstructionNull) (pCodeVertor.get(i - 1))).getName())
                    .compareTo("") == 0) {
                pCodeVertor.remove(i - 1);
            }
            i--;
        }
        return pCodeVertor;
    }

    public void loadProgram(BufferedReader f)
            throws InvalidPInstructionException, IOException {
        memoryContent = new Vector();
        labelToAddress = new HashMap();
        PInstructionBuilder parser = new PInstructionBuilder();

        while (f.ready()) {
            PInstruction newInstr = parser.createInstruction(f.readLine());
            memoryContent.add(newInstr);
            if (newInstr instanceof PInstructionDEFINE) {
                labelToAddress.put(((PInstructionDEFINE) newInstr).getLabel(),
                        new Integer(memoryContent.size() - 1));
            }
        }
        memoryContent = deleteEmptyLines(memoryContent);
        notifyAdd(0, getSize() - 1);
        reset();
    }

    public void loadProgram(String fileName)
            throws InvalidPInstructionException, FileNotFoundException,
            IOException {
        loadProgram(new BufferedReader(new FileReader(fileName)));
    }

    public void loadProgram(File file) throws InvalidPInstructionException,
            FileNotFoundException, IOException {
        loadProgram(new BufferedReader(new FileReader(file)));
    }

    public void loadProgramFromString(String description)
            throws InvalidPInstructionException, IOException {
        loadProgram(new BufferedReader(new StringReader(description)));
    }

    public Vector asDisplayableVector() {
        Vector res = new Vector();
        Iterator it = memoryContent.iterator();
        int i = 0;

        while (it.hasNext()) {
            res.add("[" + i + "]  " + it.next().toString());
            i++;
        }
        return res;
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getSize()
     */
    public int getSize() {
        return memoryContent.size();
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
     */
    public Object getElementAt(int index) {
        if (index >= 0 && index < memoryContent.size()) {
            return memoryContent.get(index);
        } else {
            return new PInstructionNull(Messages
                    .getString("PMachine.NoInstruction"));
        }
    }

    private List dataListeners = new Vector();

    /*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#addListDataListener(javax.swing.event.ListDataListener)
     */
    public void addListDataListener(ListDataListener l) {
        dataListeners.add(l);
    }

    /*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#removeListDataListener(javax.swing.event.ListDataListener)
     */
    public void removeListDataListener(ListDataListener l) {
        dataListeners.remove(l);
    }

    /*
	 * hto : unused methods private void notifyRemoval(int i, int j){ Iterator
	 * iter = dataListeners.iterator(); while (iter.hasNext()) {
	 * ListDataListener listener = (ListDataListener) iter.next();
	 * listener.intervalRemoved(new ListDataEvent(this,
	 * ListDataEvent.INTERVAL_REMOVED,i , j)); } }
	 * 
	 * private void notifyChange(int i, int j){ Iterator iter =
	 * dataListeners.iterator(); while (iter.hasNext()) { ListDataListener
	 * listener = (ListDataListener) iter.next(); listener.contentsChanged(new
	 * ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED,i , j)); } }
     */
    private void notifyAdd(int i, int j) {
        Iterator iter = dataListeners.iterator();
        while (iter.hasNext()) {
            ListDataListener listener = (ListDataListener) iter.next();
            listener.intervalAdded(new ListDataEvent(this,
                    ListDataEvent.INTERVAL_ADDED, i, j));
        }
    }

    public Object clone() {
        // TODO: do not clone
        PProgramMemory ppm = new PProgramMemory();
        ppm.pc = this.pc;
        // ppm.memoryContent = (List)((Vector)this.memoryContent).clone();
        ppm.memoryContent = this.memoryContent;
        // ppm.labelToAddress = (Map)((HashMap)this.labelToAddress).clone();
        ppm.labelToAddress = this.labelToAddress;
        // ppm.dataListeners = (List)((Vector)this.dataListeners).clone();
        ppm.dataListeners = this.dataListeners;
        return ppm;
    }
}
