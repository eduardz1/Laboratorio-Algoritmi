#pragma once
#include <string.h>
#include <stdio.h>
#include <stddef.h>
#include <stdint.h>
#include <stdint.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>
#include <assert.h>

#define RAND(min, max) ((rand() % (max - min + 1)) + min)
#define ARRAY_SIZE(x) (sizeof(x) / sizeof((x)[0]))
#define BZERO(x, x_size) memset(x, 0, x_size)
#define TIMING(a)                                                       \
  do                                                                    \
  {                                                                     \
    clock_t start = clock();                                            \
    a;                                                                  \
    clock_t end = clock();                                              \
    printf("%s: %f sec\n", #a, (double)(end - start) / CLOCKS_PER_SEC); \
  } while (0)

typedef int (*Comp)(const void *, const void *);

enum Type
{
  TYPE_INT,
  TYPE_DOUBLE,
  TYPE_STRING,
  TYPE_CHAR,
  TYPE_LONG,
  TYPE_FLOAT,
  TYPE_POINTER,
  TYPE_RECORD
};