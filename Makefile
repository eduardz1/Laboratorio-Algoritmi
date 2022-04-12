CC=gcc
CFLAGS=-Wall -O2 -DUNITY_INCLUDE_DOUBLE -DFALLBACK_BIS

# Dependencies
SHARED  := src/c/shared/*.c
UNITY   := src/c/test/unity/unity.c
OBJECTS := $(src/c/shared/:.c=.o)

QSORT   := src/c/ex1/quick_sort.c src/c/ex1/headers/quick_sort.h
BINSORT := src/c/ex1/binary_insert_sort.c src/c/headers/binary_insert_sort.h

# Directories
BIN := bin
SRC := src
OBJ := obj

ex1: #src/c/ex1/ex1.c $(QSORT) $(BINSORT) $(SHARED)
	$(CC) $(UNITY) -o $(BIN)/ex1 $(SHARED) src/c/ex1/ex1.c src/c/ex1/quick_sort.c src/c/ex1/binary_insert_sort.c

# ex2: # skip_list.c
#	$(CC) $(CFLAGS) src/c/ex2/skip_list.c -o src/c/ex2/build/main_ex2

# TODO: fix OBJECTS dependency
$(OBJ)/%.o : src/c/shared/%.c 
	$(CC) $(CFLAGS) -c $< -o $@

testall: testshd testqs testbis

testshd: #$(OBJECTS) src/c/shared/*
	$(CC) $(CFLAGS) -o $(BIN)/testshd $(SHARED) $(UNITY) src/c/test/shared_test.c 

testqs: #$(OBJECTS) $(QSORT) src/c/test/quick_sort_test.c
	$(CC) $(CFLAGS) -o $(BIN)/testqs  $(SHARED) $(UNITY) src/c/test/quick_sort_test.c src/c/ex1/quick_sort.c src/c/ex1/binary_insert_sort.c

testbis: #$(OBJECTS) $(BINSORT) src/c/test/binary_insert_sort_test.c
	$(CC) $(CFLAGS) -o $(BIN)/testbis $(SHARED) $(UNITY) src/c/test/binary_insert_sort_test.c src/c/ex1/binary_insert_sort.c 

clean:
	rm -f $(BINDIR)/* $(OBJDIR)/* *~