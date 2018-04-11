ssp 8
ujp @1

;Main program : start of instructions ------------------------------- 
define @1
lda b 0 7
ldc b 1
sto b
lda b 0 7
ind b 
fjp @3 
ldc i 1
prin 
ujp @2 
define @3 
ldc i 0
prin 
define @2 
lda i 0 5
ldc i 35004
sto i 
lda b 0 7
lda b 0 7
ind b 
fjp @4
lda i 0 5
ind i 
ldc i 0
grt i 
not b
fjp @6
lda i 0 5
ind i 
lda i 6 0 
ldc i 2
chk 1 1 
ixa 1 
dec a 1 
ind i 
les i 
ujp @7
define @6
ldc b 1
define @7
ujp @5
define @4
ldc b 0
define @5
sto b
lda b 0 7
ind b 
fjp @9 
ldc i 2
prin 
ujp @8 
define @9 
ldc i 0
prin 
define @8 
stp 
;Main program : end of instructions ------------------------------- 
