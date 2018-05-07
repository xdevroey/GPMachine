# GPMachine: a virtual machine interpreting P-Code

[![Build Status](https://travis-ci.com/xdevroey/GPMachine.svg?branch=master)](https://travis-ci.com/xdevroey/GPMachine)

## GPMachine description

GPMachine is a PCode interpreter (see [Reinard Wilhelm and Dieter Maurer, Compiler Design](http://www.cs.ioc.ee/yik/lib/2/Wilhelm1.html)). It takes as input a file containing PCode and interprets it, asking interactively the user when user input is required. The only allowed input are natural numbers. This machine is based on a previous machine by Yiti group but has largely been rewritten to perform type checking on-the-fly, thus ensuring that erroneous compiled code can be detected sooner. It has a fancy Graphical User Interface (see `-gui` option, below) that will help students in understanding the inner workings of this machine.

The PCode syntax and semantics is deeply explained in Wilhelm and Maurer. However, we slightly modified this language, as explained below (Input files).

## Running GPMachine

```
java -jar gpmachine.jar [options] filename.pcode
```

Typical use: 
- Running io.pm, with GUI, stack size 150, no license: `java -jar gpmachine.jar -gui -S:150 --nolicense io.pm`
- Running io.pm, using stdin/stdout, with verbose output: `java -jar gpmachine.jar -v io.pm`

## Input Files Format

The input PCode must be stored in a file. This input PCode obeys Wilhelm and Maurer's definition. Have a look at the list below (section Requirements) to check the exact syntax of the instructions. See [GPMachine : Guide d’utilisation](reference-manual-fr/gpmachine-reference.pdf) for a french version of the user guide.

### Additions:

The specification has been modified as follows (below, `char*` and `char+` stand for (respectively) 0-n characters and 1-n characters):
- `read` : asks the user to input an integer. When completed, pushes this integer on top of stack.
- `prin` : pops an integer from the stack and displays it on the output.
- `@define (char+)` : `define foo` defines a label foo. Any jumping command (like `ujp` or `fjp`) referring to foo will make PC jumps to this line of PCode. When executed, this instruction has no effect on the Stack. Note that we replace Whilelm and 
Maurer's notation: `lab: statement` by
```
define lab
statement
```
- `;(char)*`: defines a comment (this line will be ignored). The semicolon must be the first character of the line.
- `pop`: pops the element on top of stack and throw it away.


### PCode examples 

Available in [pcode-examples](pcode-examples):
- io.pm: showing how input/output is handled, reads an integer and write it back
- subcalls.pm: illustrating how subcalls are handled in the GPMachine (use the -gui option to view the stack!)
- logics.pm: a bit of boolean logics
- arith.pm: some arithmetic operations

### Available instructions 

- `add i`
- `sub i`
- `div i`
- `mul i`
- `neg i`
- `and b`
- `or b`
- `not b`
- `equ {i,b}`
- `geq {i,b}`
- `leq {i,b}`
- `les {i,b}`
- `grt {i,b}`
- `neq {i,b}`
- `ldo {a,b,i}`
- `ldc {a,b,i}`
- `ind {a,i,b}`
- `sro {a,i,b}`
- `stp {a,i,b}`
- `ujp`
- `fjp`
- `ixj`
- `ixa`
- `inc {a,i}`
- `dec {a,i}`
- `chk`
- `dpl {a,b,i}`
- `ldd`
- `sli`
- `new {a,b,i}`
- `lod {a,b,i}`
- `lda {a,b,i}`
- `str {a,b,i}`
- `mst`
- `cup`
- `ssp`
- `sep`
- `retf`
- `retp`
- `movs`
- `movd`
- `smp`
- `cupi`
- `mstf`

