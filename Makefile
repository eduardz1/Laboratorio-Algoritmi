CC=gcc
CFLAGS=-Wall -g -O2 -DUNITY_INCLUDE_DOUBLE

# Dependencies
SHARED=src/c/shared/*.c
UNITY=src/c/test/unity/unity.c
OBJS:=$(SHARED:.c=.o)

# Directories
BINDIR=bin
SRCDIR=src
OBJDIR=obj

# TODO: ex1 build

ex2: # skip_list.c
	$(CC) $(CFLAGS) src/c/ex2/skip_list.c -o src/c/ex2/build/main_ex2

testall: test-quick-sort test-binary-insert-sort test-shared

test-shared: $(BINDIR)/$(OBJS) 
	$(CC) $(CFLAGS) $(SHARED) $(UNITY) src/c/test/shared_test.c -o $(BINDIR)/testshd

test-quick-sort: $(BINDIR)/$(OBJS)
	$(CC) $(CFLAGS) $(SHARED) $(UNITY) src/c/test/quick_sort_test.c src/c/ex1/quick_sort.c -o $(BINDIR)/testqs

test-binary-insert-sort: $(BINDIR)/$(OBJS)
	$(CC) $(CFLAGS) $(SHARED) $(UNITY) src/c/test/binary_insert_sort_test.c -o $(BINDIR)/testbis



clean:
	rm -f $(BINDIR)/* $(OBJDIR)/* *~