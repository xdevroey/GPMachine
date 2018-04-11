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

/*
 * This class implements a stack for a pmachine.
 * It embeds a java.util.Stack but performs size checks,
 * as prescribed by Whilelm and Maurer.  
 * The stack can only contain StackElem instances.
 */

package pmachine;

import java.util.List;
import java.util.Stack;
import java.util.Vector;

import pmachine.exceptions.StackException;

public class PMemory implements IPMemory, Cloneable {

	private IListDisplayServer stackPresenter;
	private IListDisplayServer heapPresenter;

	public int getMaxSize() {
		return maxSize;
	}

	public void reset() {
		setMP(0);
		// int oldSize = stack.size();
		stack.removeAllElements();
		heap.clear();
		// TODO delete it
		/*
		 * setEP(ExecutionEnvironment.getStackSize()); if (oldSize > 0)
		 * stackPresenter.notifyRemoval(oldSize,0,oldSize - 1);
		 */
	}

	public IListDisplayServer getStackPresentation() {
		return stackPresenter;
	}

	private Stack stack;

	public Stack getStack() {
		return stack;
	}

	private List heap;

	public List getHeap() {
		return heap;
	}

	private int maxSize;

	/**
	 * The EP registry, used to detect collision between stack and heap This
	 * registry is NOT used in the current implementation, because we didn't
	 * implement the dynamic allocation operations.
	 */
	// TODO
	// private int EP;
	/**
	 * The MP registry, used to compute relative addresses. Its value is the
	 * first address of the current local memory space.
	 */
	private int MP;

	/**
	 * Creates a new stack of size (size - 1).
	 */
	public PMemory(int size) {
		maxSize = size;
		stack = new Stack();
		heap = new Vector();
		stackPresenter = new StackDisplayServer(stack, this);
		heapPresenter = new HeapDisplayServer(heap, this);
		MP = 0;
	}

	/**
	 * Pushes an element on top of the stack.
	 * 
	 * @throws StackException
	 *             if the stack overflows (i.e. SP >= EP).
	 */
	public void push(StackElem n) throws StackException {
		if (getSP() < getHP()) {
			stack.push(n);
			// System.out.println("PUSH on "+contents());
			stackPresenter.notifyAdd(stack.size() - 1, stack.size() - 1);
		} else {
			throw new StackException(Messages
					.getString("PStack.StackOverflowError"));
		}

	}

	/**
	 * Pops an element from the stack.
	 * 
	 * @throws StackException
	 *             if the stack is empty
	 */
	public StackElem pop() throws StackException {
		// System.out.println("POP on "+contents());
		if (getSP() >= 0) {
			StackElem res = (StackElem) stack.pop();
			stackPresenter.notifyRemoval(stack.size() + 1, stack.size(), stack
					.size());
			return res;
		} else {
			throw new StackException(Messages
					.getString("PStack.PopUnderflowError"));
		}

	}

	/**
	 * Returns the element stored at position idx in the stack. Note that
	 * position 0 refers to the first element in the stack.
	 * 
	 * @throws StackException
	 *             if idx refers to an index outside the stack bounds
	 *             [0,getSP()].
	 * 
	 */
	public StackElem elemAt(StackElemAddr idx) throws StackException {
		return elemAt((int) idx.intValue());
	}

	/**
	 * Returns the element stored at position idx in the stack. Note that
	 * position 0 refers to the first element in the stack.
	 * 
	 * @throws StackException
	 *             if idx refers to an index outside the stack bounds
	 *             [0,getSP()].
	 * 
	 */
	public StackElem elemAt(int idx) throws StackException {
		if (idx >= 0 && idx <= (getSP())) {
			return (StackElem) (stack.get(idx));
		} else if (idx <= maxSize && idx > getHP()) {
			return (StackElem) heap.get(maxSize - idx);
		} else {
			throw new StackException(Messages
					.getString("PStack.ElementAtAccessOutOfStack"));
		}
	}

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
	public void storeAt(StackElemAddr idx, StackElem value)
			throws StackException {
		storeAt((int) idx.intValue(), value);
	}

	/**
	 * Stores value at position idx in the stack. Note that position 0 refers to
	 * the first element in the stack.
	 * 
	 * If there is already an element at position idx, it will be overwritten.
	 * If idx refers to an index > getSP(), StackElemNull will be pushed on the
	 * stack to fill the empty gap between getSP()+1 and idx - 1.
	 * 
	 * Throws StackException if idx refers to an index outside the stack bounds
	 * [0,getSP()].
	 * 
	 */
	public void storeAt(int idx, StackElem value) throws StackException {
		if (idx >= 0 && idx <= getSP()) {
			if (((StackElem) stack.elementAt(idx)).isCallCell()) {
				value.setCallCell(true);
			}
			stack.set(idx, value);
			stackPresenter.notifyChange(idx, idx);
		} else if (idx <= maxSize && idx > getHP()) {
			heap.set(maxSize - idx, value);
			heapPresenter.notifyChange(maxSize - idx, maxSize - idx);
		} else {
			throw new StackException(Messages
					.getString("PStack.StoreAtAccessOutOfStack"));
		}
	}

	public int getHP() {
		return maxSize - heap.size();
	}

	private void setEP(int newAddress) {
		if (newAddress > getHP()) {
			int oldEP = getHP();
			// TODO : delete it
			// int i = oldEP + 1;
			// while (i <= newAddress){
			heap.clear();
			// i++;
			// }
			heapPresenter.notifyRemoval(oldEP, newAddress, oldEP + 1);
		} else {
			int oldEP = getHP();
			while (getHP() > newAddress) {
				heap.add(new StackElemNull());
			}
			heapPresenter.notifyAdd(oldEP, newAddress - 1);
		}
	}

	public int getSP() {
		return stack.size() - 1;
	}

	public int getMP() {
		return MP;
	}

	public void setMP(int val) {
		MP = val;
	}

	/**
	 * Sets the value of SP to val. If val > SP, the empty gap between the old
	 * value of SP + 1 and its new value will be filled in by StackElemNull
	 * objects.
	 */
	public void setSP(int val) throws StackException {
		// System.out.println("val = "+val);
		// System.out.println("actual stack size = "+StackLong.length);
		// System.out.println("stack size register = "+EP);
		if (val > getSP()) {
			for (int i = getSP(); i < val; i++) {
				push(new StackElemNull());
			}
		} else {
			for (int i = getSP(); i > val; i--) {
				pop();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.IPMemory#alloc(pmachine.StackElemInt)
	 */
	public StackElemAddr alloc(StackElemInt size) throws StackException {
		int nbCells = size.longValue();
		if (getHP() - nbCells >= getSP()) {
			setEP(getHP() - nbCells);
			return new StackElemAddr(getHP() + 1);
		} else
			throw new StackException(Messages
					.getString("PMachine.StackHeapCollision"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.IPMemory#getHeapPresentation()
	 */
	public IListDisplayServer getHeapPresentation() {
		return heapPresenter;
	}

	public Object clone() {
		PMemory pmemo = new PMemory(this.maxSize);
		pmemo.stack = (Stack) ((Stack) this.stack).clone();
		pmemo.heap = (List) ((Vector) this.heap).clone();
		pmemo.stackPresenter = new StackDisplayServer(pmemo.stack, pmemo);
		pmemo.heapPresenter = new HeapDisplayServer(pmemo.heap, pmemo);
		pmemo.MP = this.MP;
		return pmemo;
	}
}
