/*
 * Created on 26-juil.-2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package pmachine.instructions;

import pmachine.PMachine;
import pmachine.exceptions.RuntimeException;

/**
 * @author ybo
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class PInstructionUJP extends PInstructionAbstract {

	private String label;

	/**
	 * Makes the pmachine jump to this instruction's label.
	 * 
	 * @throws RuntimeException
	 */
	public void execute(PMachine pm) throws RuntimeException {
		pm.jumpToLabel(getLabel());
	}

	public PInstructionUJP(String label) {
		setLabel(label);
	}

	/**
	 * @return Returns the label.
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 *            The label to set.
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.instructions.PInstructionAbstract#getName()
	 */
	public String getName() {
		return "ujp";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pmachine.instructions.PInstructionAbstract#getArgs()
	 */
	public String getArgs() {
		return label;
	}
}
