CC=gcc
CFLAGS=-Wall -g -O2

# Dependencies
OBJECTS=

# TODO: ex1 build

ex2: # skip_list.c
	$(CC) $(CFLAGS) src/c/ex2/skip_list.c -o src/c/ex2/build/main_ex2

testall: test-quick_sort test-binary-insert-sort

test-quick-sort:
	$(CC) $(CFLAGS) src/c/test/quick_sort_test.c src/c/test/unity/unity.c -o testqs

test-binary-insert-sort:
	$(CC) $(CFLAGS) src/c/test/binary_insert_sort_test.c src/c/test/unity/unity.c -o testbis

clean:
	rm -f $(OBJECTS) *.o *~