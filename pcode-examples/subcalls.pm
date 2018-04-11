ssp 7
ujp @1

; Procedure p1 
define @2
ssp 7 
ujp @3

; Procedure p11 
define @4
ssp 7 
ujp @5

; Procedure p111 
define @6
ssp 6 
ujp @7

;Procedure p111 : start of instructions ----------------------
define @7
lda i 0 5
lda i 1 5
ind i 
ldc i 1
add i
sto i 
lda i 3 6
lda i 0 5
ind i 
sto i 
mst 3
lda i 3 6
cup 1 @2 
retp 
;Procedure p111 : end of instructions ----------------------

;Procedure p11 : start of instructions ----------------------
define @5
lda b 2 5
ldc b 0
sto b
lda i 0 5
ldc i 1
sto i 
mst 0
cup 0 @6 
lda i 0 5
ind i 
prin 
retp 
;Procedure p11 : end of instructions ----------------------

;Procedure p1 : start of instructions ----------------------
define @3
lda i 0 6
ldc i 3
sto i 
lda b 1 5
ind b 
fjp @9 
mst 0
lda i 0 6
ind i 
cup 1 @4 
ujp @8 
define @9 
define @8 
retp 
;Procedure p1 : end of instructions ----------------------

;Main program : start of instructions ------------------------------- 
define @1
lda i 0 6
ldc i 2
sto i 
lda b 0 5
ldc i 0
ldc i 0
equ i 
sto b
mst 0
lda i 0 6
cup 1 @2 
stp 
;Main program : end of instructions ------------------------------- 
