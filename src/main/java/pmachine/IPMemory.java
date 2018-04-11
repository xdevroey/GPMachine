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

import java.util.List;
import java.util.Stack;

import pmachine.exceptions.StackException;

/**
 * \brief The store of a PMachine.
 * 
 * This interface groups all operations that must be implemented by a PMachine
 * store to ensure that it operates correctly.
 * 
 * A PMachine memory is made of a space organized in two zones: the stack
 * (addresses 0 .. SP-1) and the heap (HP+1 .. maxAddr). During an execution, it
 * should always be the case that HP > SP. Otherwise, there is a collision
 * (stack overflow or heap underflow) and the machine execution might stop
 * abruptly.
 * 
 * Using an interface instead of an actual class is a matter of design: this
 * breaks the dependency between PMachine and PMemory.
 */
public interface IPMemory {

	/**
	 * @return stack pointer
	 */
	public Stack getStack();

	/**
	 * @return heap pointer
	 */
	public List getHeap();

	/**
	 * @return maxAddr, the largest allocatable address in this memory.
	 */
	public int getMaxSize();

	/**
	 * Call this method to obtain an object representing the stack that can be
	 * displayed in a JList in the UI.
	 * 
	 * @return a IListDisplayServer presenting the content of the memory Stack.
	 */
	public IListDisplayServer getStackPresentation();

	/**
	 * Call this method to obtain an object representing the heap that can be
	 * displayed in a JList in the UI.
	 * 
	 * @return
	 */
	public IListDisplayServer getHeapPresentation();

	/**
	 * Empties this memory and resets all registers to their initial values.
	 * Initial values are \li \c SP = 0 \li \c HP = maxAddr \li \c MP = 0
	 * 
	 */
	public abstract void reset();

	/**
	 * Allocates a series of "size" consecutive cells. Thus, from some address a
	 * to address a+size, all cells are allocated and this method returns the
	 * address a. It is guaranteed that these cells will not be reallocated in
	 * the future.
	 * 
	 * @param size
	 * @return the first address of the newly allocated cells.
	 * @throws StackException
	 */
	public abstract StackElemAddr alloc(StackElemInt size)
			throws StackException;

	/**
	 * Pushes an element on top of the stack.
	 * 
	 * @throws StackException
	 *             if the stack overflows (i.e. SP >= HP).
	 */
	public abstract void push(StackElem n) throws StackException;

	/**
	 * Pops an element from the stack.
	 * 
	 * @throws StackException
	 *             if the stack is empty
	 */
	public abstract StackElem pop() throws StackException;

	/**
	 * Returns the element stored at position idx in the stack. Note that
	 * position 0 refers to the first element in the stack.
	 * 
	 * @throws StackException
	 *             if idx refers to an index outside the stack bounds
	 *             [0,getSP()].
	 * 
	 */
	public abstract StackElem elemAt(StackElemAddr idx) throws StackException;

	/**
	 * Returns the element stored at position idx in the stack. Note that
	 * position 0 refers to the first element in the stack.
	 * 
	 * @throws StackException
	 *             if idx refers to an index outside the stack bounds
	 *             [0,getSP()].
	 * 
	 */
	public abstract StackElem elemAt(int idx) throws StackException;

	/**
	 * Stores value at position idx in the stack. Note that position 0 refers to
	 * the first element in the stack.
	 * 
	 * If there is already an element at position idx, it will be overwritten.
	 * If idx refers to an index > getSP(), StackElemNull will be pushed on the
	 * stack to fill the empty gap between getSP()+1 and idx - 1.
	 * 
	 * @param idx
	 *            the index at which value should be stored.
	 * @param value
	 *            the value to be stored at idx.
	 * 
	 * @throws StackException
	 *             if idx refers to an index outside the stack bounds
	 *             [0,getSP()].
	 * 
	 */
	public abstract void storeAt(StackElemAddr idx, StackElem value)
			throws StackException;

	/**
	 * Stores value at position idx in the stack. Note that position 0 refers to
	 * the first element in the stack.
	 * 
	 * If there is already an element at position idx, it will be overwritten.
	 * If idx refers to an index > getSP(), StackElemNull will be pushed on the
	 * stack to fill the empty gap between getSP()+1 and idx - 1.
	 * 
	 * @throws StackException
	 *             if idx refers to an index outside the stack bounds
	 *             [0,getSP()].
	 * 
	 */
	public abstract void storeAt(int idx, StackElem value)
			throws StackException;

	/**
	 * @return the value of the HP register.
	 */
	public abstract int getHP();

	/**
	 * @return the value of the SP (stack pointer) register
	 */
	public abstract int getSP();

	/**
	 * @return the value of the MP register
	 */
	public abstract int getMP();

	public abstract void setMP(int val);

	/**
	 * \post \li Sets the value of SP to val. \li If val > SP, the empty gap
	 * between the old value of SP + 1 and its new value will be filled in by
	 * StackElemNull objects.
	 */
	public abstract void setSP(int val) throws StackException;

}
