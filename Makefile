CC=clang
CFLAGS=-Wall -ggdb3 -O0 -DUNITY_INCLUDE_DOUBLE #-DFALLBACK_BIS

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
	$(CC) $(CFLAGS) -o $(BIN)/ex1 $(SHARED) src/c/ex1/ex1.c src/c/ex1/quick_sort.c src/c/ex1/binary_insert_sort.c

ex1p: #src/c/ex1/ex1.c $(QSORT) $(BINSORT) $(SHARED)
	$(CC) $(CFLAGS) -DPRINT_RECORDS -o $(BIN)/ex1 $(SHARED) src/c/ex1/ex1.c src/c/ex1/quick_sort.c src/c/ex1/binary_insert_sort.c

ex2: # skip_list.c
	$(CC) $(CFLAGS) -o $(BIN)/ex2 $(SHARED) src/c/ex2/skip_list.c src/c/ex2/ex2.c

# TODO: fix OBJECTS dependency
$(OBJ)/%.o : src/c/shared/%.c 
	$(CC) $(CFLAGS) -c $< -o $@

testall: testshd testqs testbis testsklist

testshd: #$(OBJECTS) src/c/shared/*
	$(CC) $(CFLAGS) -o $(BIN)/testshd $(SHARED) $(UNITY) src/c/test/shared_test.c 

testqs: #$(OBJECTS) $(QSORT) src/c/test/quick_sort_test.c
	$(CC) $(CFLAGS) -o $(BIN)/testqs  $(SHARED) $(UNITY) src/c/test/quick_sort_test.c src/c/ex1/quick_sort.c src/c/ex1/binary_insert_sort.c

testbis: #$(OBJECTS) $(BINSORT) src/c/test/binary_insert_sort_test.c
	$(CC) $(CFLAGS) -o $(BIN)/testbis $(SHARED) $(UNITY) src/c/test/binary_insert_sort_test.c src/c/ex1/binary_insert_sort.c 

testsklist: #$(OBJECTS) src/c/test/skip_list_test.c
	$(CC) $(CFLAGS) -o $(BIN)/testsklist $(SHARED) $(UNITY) src/c/test/skip_list_test.c src/c/ex2/skip_list.c

clean:
	rm -f $(BINDIR)/* $(OBJDIR)/* *~