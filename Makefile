CC=gcc
CFLAGS=-Wall -g -O2

# Dependencies
OBJECTS= src/c/shared/common.c src/c/shared/record.c

# TODO: ex1 build

ex2: # skip_list.c
	$(CC) $(CFLAGS) src/c/ex2/skip_list.c -o src/c/ex2/build/main_ex2

testall: test-quick-sort test-binary-insert-sort test-shared

test-shared:
	$(CC) $(CFLAGS) $(OBJECTS) src/c/test/shared_test.c src/c/test/unity/unity.c -o bin/testshd

test-quick-sort:
	$(CC) $(CFLAGS) src/c/test/quick_sort_test.c src/c/test/unity/unity.c -o bin/testqs

test-binary-insert-sort:
	$(CC) $(CFLAGS) src/c/test/binary_insert_sort_test.c src/c/test/unity/unity.c -o bin/testbis



clean:
	rm -f $(OBJECTS) *.o *~