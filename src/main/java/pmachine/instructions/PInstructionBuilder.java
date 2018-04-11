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
 * This class is an instruction builder.  It implements an algorithm
 * for reading String descriptions of PInstructions and builds PInstructions
 * from them.
 */

package pmachine.instructions;

import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import pmachine.ExecutionEnvironment;
import pmachine.Messages;
import pmachine.PMachine;
import pmachine.exceptions.InvalidPInstructionException;

public class PInstructionBuilder implements IPInstructionFactory {

	// hto : useless ?
	// private final static boolean DEBUG = true;

	/**
	 * @param typeDescription
	 *            the String to be parsed
	 * @param acceptableTypes
	 *            a String of type descriptions containing all correct types
	 * @param instructionName
	 *            the name of the instruction
	 * @return an integer representing the type described by typeDescription
	 * @throws InvalidPInstructionException
	 *             if typeDescription does not belong to acceptableTypes.
	 */
	protected int parseType(String typeDescription, String acceptableTypes,
			String instructionName) throws InvalidPInstructionException {
		if (acceptableTypes.indexOf(typeDescription) < 0) {
			if (!ExecutionEnvironment.isGuiEnabled()) {
				// Command line environment, error message + exit
				System.err.println(Messages
						.getString("PInstruction.TypeModifierInvalid1")
						+ typeDescription
						+ Messages
								.getString("PInstruction.TypeModifierInvalid2")
						+ instructionName
						+ Messages
								.getString("PInstruction.TypeModifierInvalid3")
						+ acceptableTypes
						+ Messages.getString("PStack.TextOutput12"));
				PMachine.exitInstructionError(instructionName);
			}
			// GUI environment, just send Exception
			else
				throw new InvalidPInstructionException(Messages
						.getString("PInstruction.TypeModifierInvalid1")
						+ typeDescription
						+ Messages
								.getString("PInstruction.TypeModifierInvalid2")
						+ instructionName
						+ Messages
								.getString("PInstruction.TypeModifierInvalid3")
						+ acceptableTypes
						+ Messages.getString("PStack.TextOutput12"));
		}
		return parseType(typeDescription);
	}

	/**
	 * @param typeDescription
	 *            a String possibly representing a PType specified (i.e. one of
	 *            i,b,a).
	 * @return A constant (from PTypedInstruction
	 * @throws InvalidPInstructionException
	 *             if typeDescription does not represent a valid type.
	 * @see PTypedInstruction.NO_TYPE
	 * @see PTypedInstruction.TYPE_INT
	 * @see PTypedInstruction.TYPE_BOOL
	 * @see PTypedInstruction.TYPE_ADDR
	 */
	protected int parseType(String typeDescription)
			throws InvalidPInstructionException {
		int type = PTypedInstruction.NO_TYPE;
		if (typeDescription.equals("i")) {
			type = PTypedInstruction.TYPE_INT;
		} else if (typeDescription.equals("b")) {
			type = PTypedInstruction.TYPE_BOOL;
		} else if (typeDescription.equals("a")) {
			type = PTypedInstruction.TYPE_ADDR;
		} else if (typeDescription.equals("")) {
			type = PTypedInstruction.NO_TYPE;
		} else {
			throw new InvalidPInstructionException(Messages
					.getString("PInstruction.BadTypeSpecifier")
					+ typeDescription);
		}
		return type;
	}

	/**
	 * Reads text and builds a List of all the words appearing in text. A word
	 * is a sequence of non-whitespace characters.
	 * 
	 * @param text
	 *            a String.
	 * @return a List (l1,..,ln) containing all words appearing in text,
	 *         conserving the order in which they occur.
	 */
	protected List parseParameters(String text) {
		List structuredDescription = new Vector();
		StringTokenizer tokenizer = new StringTokenizer(text);

		while (tokenizer.hasMoreTokens()) {
			structuredDescription.add(tokenizer.nextToken());
		}

		return structuredDescription;
	}

	// Just gathering a set of this class errors in one place
	private static String errorMessage(int errorCode) {
		if (errorCode == 0)
			return Messages.getString("PInstruction.WrongParametersNumber");
		if (errorCode == 1)
			return Messages.getString("PInstruction.IntWronglyFormatted");
		if (errorCode == 2)
			return Messages.getString("PInstruction.WrongNumberFormat");
		// Only for debuging needs
		return "Wrong ErrorCode";
	}

	/**
	 * @see pmachine.instructions.IPInstructionFactory#createInstruction(java.lang.String)
	 */
	public PInstruction createInstruction(String textualDescription)
			throws InvalidPInstructionException {

		textualDescription.trim();
		if (textualDescription.startsWith(Messages
				.getString("PInstruction.CommentDelimiter"))) {
			return new PInstructionNull(textualDescription);
		} else {
			List structuredDescription = parseParameters(textualDescription);
			if (structuredDescription.size() == 0) {
				return new PInstructionNull(textualDescription);
			}
			if (structuredDescription.get(0).equals("add")) {
				if (structuredDescription.size() == 2) {
					return new PInstructionADD(
							parseType((String) structuredDescription.get(1),
									"i a", "add"));
				} else
					throw new InvalidPInstructionException("add "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("sub")) {
				if (structuredDescription.size() == 2) {
					return new PInstructionSUB(
							parseType((String) structuredDescription.get(1),
									"i a", "sub"));
				} else
					throw new InvalidPInstructionException("sub "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("mul")) {
				if (structuredDescription.size() == 2)
					return new PInstructionMUL(
							parseType((String) structuredDescription.get(1),
									"i a", "mul"));
				else
					throw new InvalidPInstructionException("mul "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("div")) {
				if (structuredDescription.size() == 2)
					return new PInstructionDIV(
							parseType((String) structuredDescription.get(1),
									"i a", "div"));
				else
					throw new InvalidPInstructionException("div "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("neg")) {
				if (structuredDescription.size() == 2)
					return new PInstructionNEG(
							parseType((String) structuredDescription.get(1),
									"i a", "neg"));
				else
					throw new InvalidPInstructionException("neg "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("not")) {
				if (structuredDescription.size() == 2)
					return new PInstructionNOT(parseType(
							(String) structuredDescription.get(1), "b", "not"));
				else
					throw new InvalidPInstructionException("not "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("and")) {
				if (structuredDescription.size() == 2)
					return new PInstructionAND(parseType(
							(String) structuredDescription.get(1), "b", "and"));
				else
					throw new InvalidPInstructionException("and "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("or")) {
				if (structuredDescription.size() == 2)
					return new PInstructionOR(parseType(
							(String) structuredDescription.get(1), "b", "or"));
				else
					throw new InvalidPInstructionException("or "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("equ")) {
				if (structuredDescription.size() == 2) {
					return new PInstructionEQU(
							parseType((String) structuredDescription.get(1),
									"i b", "equ"));
				} else
					throw new InvalidPInstructionException("equ "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("geq")) {
				if (structuredDescription.size() == 2) {
					return new PInstructionGEQ(
							parseType((String) structuredDescription.get(1),
									"i b", "geq"));
				} else
					throw new InvalidPInstructionException("geq "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("leq")) {
				if (structuredDescription.size() == 2) {
					return new PInstructionLEQ(
							parseType((String) structuredDescription.get(1),
									"i b", "leq"));
				} else
					throw new InvalidPInstructionException("leq "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("les")) {
				if (structuredDescription.size() == 2) {
					return new PInstructionLES(
							parseType((String) structuredDescription.get(1),
									"i b", "les"));
				} else
					throw new InvalidPInstructionException("les "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("grt")) {
				if (structuredDescription.size() == 2) {
					return new PInstructionGRT(
							parseType((String) structuredDescription.get(1),
									"i b", "grt"));
				} else
					throw new InvalidPInstructionException("grt "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("neq")) {
				if (structuredDescription.size() == 2) {
					return new PInstructionNEQ(
							parseType((String) structuredDescription.get(1),
									"i b", "neq"));
				} else
					throw new InvalidPInstructionException("neq "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("ldo")) {
				if (structuredDescription.size() == 3) {
					try {
						return new PInstructionLDO(parseType(
								(String) structuredDescription.get(1), "i a b",
								"ldo"),
								Integer.parseInt((String) structuredDescription
										.get(2)));
					} catch (NumberFormatException e) {
						throw new InvalidPInstructionException(errorMessage(1)
								+ "ldo");
					}
				} else
					throw new InvalidPInstructionException("ldo "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("ldc")) {
				if (structuredDescription.size() == 3) {
					try {
						return new PInstructionLDC(parseType(
								(String) structuredDescription.get(1), "i a b",
								"ldc"),
								Integer.parseInt((String) structuredDescription
										.get(2)));
					} catch (NumberFormatException e) {
						throw new InvalidPInstructionException(errorMessage(1)
								+ "ldc");
					}
				} else
					throw new InvalidPInstructionException("ldc "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("ind")) {
				if (structuredDescription.size() == 2) {
					return new PInstructionIND(parseType(
							(String) structuredDescription.get(1), "i a b",
							"ind"));
				} else
					throw new InvalidPInstructionException("ind "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("chk")) {
				if (structuredDescription.size() == 3) {
					try {
						int bmin = Integer
								.parseInt((String) structuredDescription.get(1));
						int bmax = Integer
								.parseInt((String) structuredDescription.get(2));
						return new PInstructionCHK(bmin, bmax);
					} catch (NumberFormatException e) {
						throw new InvalidPInstructionException(errorMessage(1)
								+ "chk");
					}
				} else
					throw new InvalidPInstructionException(Messages
							.getString("PMachine.CHK_MissingParameterError"));
			} else if (structuredDescription.get(0).equals("sro")) {
				if (structuredDescription.size() == 3) {
					try {
						return new PInstructionSRO(parseType(
								(String) structuredDescription.get(1), "i a b",
								"sro"),
								Integer.parseInt((String) structuredDescription
										.get(2)));
					} catch (NumberFormatException e) {
						throw new InvalidPInstructionException(errorMessage(1)
								+ "sro");
					}
				} else
					throw new InvalidPInstructionException("sro "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("sto")) {
				if (structuredDescription.size() == 2) {
					return new PInstructionSTO(parseType(
							(String) structuredDescription.get(1), "i a b",
							"sto"));
				} else
					throw new InvalidPInstructionException("sto "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("ujp")) {
				if (structuredDescription.size() == 2) {
					return new PInstructionUJP((String) structuredDescription
							.get(1));
				} else
					throw new InvalidPInstructionException("ujp "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("fjp")) {
				if (structuredDescription.size() == 2) {
					return new PInstructionFJP((String) structuredDescription
							.get(1));
				} else
					throw new InvalidPInstructionException("fjp "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("ixa")) {
				if (structuredDescription.size() == 2) {
					try {
						return new PInstructionIXA(
								Integer.parseInt((String) structuredDescription
										.get(1)));
					} catch (NumberFormatException e) {
						throw new InvalidPInstructionException(errorMessage(2)
								+ structuredDescription.get(1) + " in ixa");
					}
				} else
					throw new InvalidPInstructionException("ixa "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("ixj")) {
				if (structuredDescription.size() == 2) {
					try {
						return new PInstructionIXJ(
								(String) structuredDescription.get(1));
					} catch (NumberFormatException e) {
						throw new InvalidPInstructionException(errorMessage(2)
								+ structuredDescription.get(1) + " in ixj");
					}
				} else
					throw new InvalidPInstructionException("ixj "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("inc")) {
				if (structuredDescription.size() == 3) {
					try {
						return new PInstructionINC(parseType(
								(String) structuredDescription.get(1), "i a",
								"inc"),
								Integer.parseInt((String) structuredDescription
										.get(2)));
					} catch (NumberFormatException e) {
						throw new InvalidPInstructionException(errorMessage(2)
								+ structuredDescription.get(1) + " in inc");
					}
				} else
					throw new InvalidPInstructionException("inc"
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("dec")) {
				if (structuredDescription.size() == 3) {
					try {
						return new PInstructionDEC(parseType(
								(String) structuredDescription.get(1), "i a",
								"dec"),
								Integer.parseInt((String) structuredDescription
										.get(2)));
					} catch (NumberFormatException e) {
						throw new InvalidPInstructionException(errorMessage(2)
								+ structuredDescription.get(1) + " in dec");
					}
				} else
					throw new InvalidPInstructionException("dec"
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("dpl")) {
				if (structuredDescription.size() == 2) {
					return new PInstructionDPL(
							parseType((String) structuredDescription.get(1)));
				} else
					throw new InvalidPInstructionException("dpl "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("ldd")) {
				if (structuredDescription.size() == 2) {
					try {
						return new PInstructionLDD(
								Integer.parseInt((String) structuredDescription
										.get(1)));
					} catch (NumberFormatException e) {
						throw new InvalidPInstructionException(errorMessage(2)
								+ structuredDescription.get(1) + " in ldd");
					}
				} else
					throw new InvalidPInstructionException("ldd "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("sli")) {
				if (structuredDescription.size() == 2) {
					return new PInstructionSLI(parseType(
							(String) structuredDescription.get(1), "i a b",
							"sli"));
				} else
					throw new InvalidPInstructionException("sli "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("lod")) {
				if (structuredDescription.size() == 4) {
					try {
						return new PInstructionLOD(parseType(
								(String) structuredDescription.get(1), "i a b",
								"lod"),
								Integer.parseInt((String) structuredDescription
										.get(2)),
								Integer.parseInt((String) structuredDescription
										.get(3)));
					} catch (NumberFormatException e) {
						InvalidPInstructionException ex = new InvalidPInstructionException(
								errorMessage(2) + "in ldd (" + e.getMessage()
										+ ")");
						ex.setStackTrace(e.getStackTrace());
						throw ex;
					}
				} else
					throw new InvalidPInstructionException("lod "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("lda")) {
				if (structuredDescription.size() == 4) {
					try {
						return new PInstructionLDA(parseType(
								(String) structuredDescription.get(1), "i a b",
								"lda"),
								Integer.parseInt((String) structuredDescription
										.get(2)),
								Integer.parseInt((String) structuredDescription
										.get(3)));
					} catch (NumberFormatException e) {
						InvalidPInstructionException ex = new InvalidPInstructionException(
								errorMessage(2) + "in lda (" + e.getMessage()
										+ ")");
						ex.setStackTrace(e.getStackTrace());
						throw ex;
					}
				} else
					throw new InvalidPInstructionException("lda "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("str")) {
				if (structuredDescription.size() == 4) {
					try {
						return new PInstructionSTR(parseType(
								(String) structuredDescription.get(1), "a i b",
								"str"),
								Integer.parseInt((String) structuredDescription
										.get(2)),
								Integer.parseInt((String) structuredDescription
										.get(3)));
					} catch (NumberFormatException e) {
						InvalidPInstructionException ex = new InvalidPInstructionException(
								errorMessage(2) + "in str (" + e.getMessage()
										+ ")");
						ex.setStackTrace(e.getStackTrace());
						throw ex;
					}
				} else
					throw new InvalidPInstructionException("str "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("mst")) {
				if (structuredDescription.size() == 2) {
					try {
						return new PInstructionMST(
								Integer.parseInt((String) structuredDescription
										.get(1)));
					} catch (NumberFormatException e) {
						InvalidPInstructionException ex = new InvalidPInstructionException(
								errorMessage(2) + "in mst (" + e.getMessage()
										+ ")");
						ex.setStackTrace(e.getStackTrace());
						throw ex;
					}
				} else
					throw new InvalidPInstructionException("mst "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("cup")) {
				if (structuredDescription.size() == 3) {
					try {
						return new PInstructionCUP(
								Integer.parseInt((String) structuredDescription
										.get(1)),
								(String) structuredDescription.get(2));
					} catch (NumberFormatException e) {
						InvalidPInstructionException ex = new InvalidPInstructionException(
								errorMessage(2) + "in cup (" + e.getMessage()
										+ ")");
						ex.setStackTrace(e.getStackTrace());
						throw ex;
					}
				} else
					throw new InvalidPInstructionException("cup "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("ssp")) {
				if (structuredDescription.size() == 2) {
					try {
						return new PInstructionSSP(
								Integer.parseInt((String) structuredDescription
										.get(1)));
					} catch (NumberFormatException e) {
						InvalidPInstructionException ex = new InvalidPInstructionException(
								errorMessage(2) + "in ssp (" + e.getMessage()
										+ ")");
						ex.setStackTrace(e.getStackTrace());
						throw ex;
					}
				} else
					throw new InvalidPInstructionException("ssp "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("retf")) {
				if (structuredDescription.size() == 1)
					return new PInstructionRETF();
				else
					throw new InvalidPInstructionException("retf "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("retp")) {
				if (structuredDescription.size() == 1)
					return new PInstructionRETP();
				else
					throw new InvalidPInstructionException("retp "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("prin")) {
				if (structuredDescription.size() == 1)
					return new PInstructionPRIN();
				else
					throw new InvalidPInstructionException("prin "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("read")) {
				if (structuredDescription.size() == 1)
					return new PInstructionREAD();
				else
					throw new InvalidPInstructionException("read "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("stp")) {
				if (structuredDescription.size() == 1)
					return new PInstructionSTP();
				else
					throw new InvalidPInstructionException("stp "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("pop")) {
				if (structuredDescription.size() == 1)
					return new PInstructionPOP();
				else
					throw new InvalidPInstructionException("pop "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("new")) {
				if (structuredDescription.size() == 1)
					return new PInstructionNEW();
				else
					throw new InvalidPInstructionException("new "
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("movs")) {
				if (structuredDescription.size() == 2) {
					try {
						return new PInstructionMOVS(
								Integer.parseInt((String) structuredDescription
										.get(1)));
					} catch (NumberFormatException e) {
						throw new InvalidPInstructionException(errorMessage(2)
								+ structuredDescription.get(1) + " in movs");
					}
				} else
					throw new InvalidPInstructionException("movs"
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("movd")) {
				if (structuredDescription.size() == 2) {
					try {
						return new PInstructionMOVD(
								Integer.parseInt((String) structuredDescription
										.get(1)));
					} catch (NumberFormatException e) {
						throw new InvalidPInstructionException(errorMessage(2)
								+ structuredDescription.get(1) + " in movd");
					}
				} else
					throw new InvalidPInstructionException("movd"
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("smp")) {
				if (structuredDescription.size() == 2) {
					try {
						return new PInstructionSMP(
								Integer.parseInt((String) structuredDescription
										.get(1)));
					} catch (NumberFormatException e) {
						throw new InvalidPInstructionException(errorMessage(2)
								+ structuredDescription.get(1) + " in smp");
					}
				} else
					throw new InvalidPInstructionException("smp"
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("cupi")) {
				if (structuredDescription.size() == 3) {
					try {
						return new PInstructionCUPI(
								Integer.parseInt((String) structuredDescription
										.get(1)),
								Integer.parseInt((String) structuredDescription
										.get(2)));
					} catch (NumberFormatException e) {
						InvalidPInstructionException ex = new InvalidPInstructionException(
								errorMessage(2) + "in cupi (" + e.getMessage()
										+ ")");
						ex.setStackTrace(e.getStackTrace());
						throw ex;
					}
				} else
					throw new InvalidPInstructionException("cupi"
							+ errorMessage(0));
			} else if (structuredDescription.get(0).equals("mstf")) {
				if (structuredDescription.size() == 3) {
					try {
						return new PInstructionMSTF(
								Integer.parseInt((String) structuredDescription
										.get(1)),
								Integer.parseInt((String) structuredDescription
										.get(2)));
					} catch (NumberFormatException e) {
						InvalidPInstructionException ex = new InvalidPInstructionException(
								errorMessage(2) + "in mstf (" + e.getMessage()
										+ ")");
						ex.setStackTrace(e.getStackTrace());
						throw ex;
					}
				} else
					throw new InvalidPInstructionException("mstf"
							+ errorMessage(0));
			}

			else if (structuredDescription.get(0).equals(
					Messages.getString("PMachine.DefinInstruction"))) {
				if (structuredDescription.size() == 2)
					return new PInstructionDEFINE(
							(String) structuredDescription.get(1));
				else
					throw new InvalidPInstructionException("define "
							+ errorMessage(0));
			} else
				throw new InvalidPInstructionException(Messages
						.getString("PInstruction.UnknownInstruction")
						+ structuredDescription);
		}
	}

}
