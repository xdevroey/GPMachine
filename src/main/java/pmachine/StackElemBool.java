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

import pmachine.exceptions.WrongTypeException;

/**
 * @author ybo
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates. To enable and disable the creation of type
 * comments go to Window>Preferences>Java>Code Generation.
 */
public class StackElemBool extends StackElem {

    public String printType() {
        return Messages.getString("StackElemBool.TypeName");
    }

    protected long compareValues(StackElem el) throws WrongTypeException {
        if (!(el instanceof StackElemBool)) {
            throw new WrongTypeException(Messages
                    .getString("StackElemBool.TypeComparisonError"));
        }
        StackElemBool bl = (StackElemBool) el;

        if ((bl.booleanValue() && this.booleanValue())
                || ((!bl.booleanValue()) && (!this.booleanValue()))) {
            return 0;
        } else if (bl.booleanValue() && !this.booleanValue()) {
            return 1;
        } else {
            return -1;
        }

    }

    public StackElemBool(int i) throws WrongTypeException {
        if (i == 0) {
            value = new Boolean(false);
        } else if (i == 1) {
            value = new Boolean(true);
        } else {
            throw new WrongTypeException(Messages
                    .getString("StackElemBool.BoolIsNot0or1"));
        }
    }

    public StackElemBool(boolean b) {
        value = new Boolean(b);
    }

    public Boolean getBoolean() {
        return new Boolean(booleanValue());
    }

    public boolean booleanValue() {

        return ((Boolean) this.value).booleanValue();
    }

    private final static int AND = 1;
    private final static int OR = 2;
    private final static int NOT = 3;
    private final static StackElemBool dummyStackElemBool = new StackElemBool(
            false);

    private static StackElemBool binOp(StackElem s1, StackElem s2, int op)
            throws WrongTypeException {
        if (s1 instanceof StackElemBool && s2 instanceof StackElemBool) {
            boolean val1 = ((Boolean) ((StackElemBool) s1).value)
                    .booleanValue();
            boolean val2 = ((Boolean) ((StackElemBool) s2).value)
                    .booleanValue();
            if (op == AND) {
                return (new StackElemBool(val1 && val2));
            } else if (op == OR) {
                return (new StackElemBool(val1 || val2));
            } else if (op == NOT) {
                return (new StackElemBool(!val1));
            } else {
                return null; /* should never be here */
            }
        } else {
            throw new WrongTypeException(Messages
                    .getString("StackElemBool.BadOperandsType"));
        }
    }

    public static StackElem and(StackElem s1, StackElem s2)
            throws WrongTypeException {
        return binOp(s1, s2, AND);
    }

    public static StackElem or(StackElem s1, StackElem s2)
            throws WrongTypeException {
        return binOp(s1, s2, OR);
    }

    public static StackElem not(StackElem s1) throws WrongTypeException {
        return binOp(s1, dummyStackElemBool, NOT);
    }

    public static StackElemBool eq(StackElem s1, StackElem s2)
            throws WrongTypeException {
        return compare(s1, s2, EQ);
    }

    public static StackElemBool neq(StackElem s1, StackElem s2)
            throws WrongTypeException {
        return compare(s1, s2, NEQ);
    }

    public static StackElemBool leq(StackElem s1, StackElem s2)
            throws WrongTypeException {
        return compare(s1, s2, LEQ);
    }

    public static StackElemBool geq(StackElem s1, StackElem s2)
            throws WrongTypeException {
        return compare(s1, s2, GEQ);
    }

    public static StackElemBool grt(StackElem s1, StackElem s2)
            throws WrongTypeException {
        return compare(s1, s2, GRT);
    }

    public static StackElemBool lst(StackElem s1, StackElem s2)
            throws WrongTypeException {
        return compare(s1, s2, LST);
    }

}
