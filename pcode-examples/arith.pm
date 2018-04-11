ssp 8
ujp @1

;Main program : start of instructions ------------------------------- 
define @1
lda i 0 7
ldc i 3
sto i 
lda i 0 7
ind i 
prin 
lda i 0 6
ldc i 0
sto i 
lda i 0 6
ind i 
prin 
lda i 0 7
lda i 0 7
ind i 
lda i 0 6
ind i 
add i
sto i 
lda i 0 7
ind i 
prin 
lda i 0 5
lda i 0 7
ind i 
lda i 0 6
ind i 
ldc i 1
add i
mul i
sto i 
lda i 0 5
ind i 
prin 
lda i 0 5
lda i 0 7
ind i 
lda i 0 6
ind i 
mul i
ldc i 1
add i
sto i 
lda i 0 5
ind i 
prin 
lda i 0 5
lda i 0 5
ind i 
lda i 0 7
ind i 
sub i
ldc i 1
add i
sto i 
lda i 0 5
ind i 
prin 
stp 
;Main program : end of instructions ------------------------------- 
