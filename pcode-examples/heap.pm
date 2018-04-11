; Allocates three new cells in the heap
; Saves the address in cell 0
ssp 1

ldc a 0
ldc i 3
new
; Stores an integer in that pointer !
ldo a 0
read
sto i 
ldo a 0
ind i
prin
stp