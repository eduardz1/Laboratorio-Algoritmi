#pragma once
#include <string.h>
#include <stdio.h>
#include <stddef.h>
#include <stdint.h>
#include <stdint.h>
#include <stdlib.h>
#include <stdbool.h>
#include <time.h>

#define RAND(min, max) ((rand() % (max - min + 1)) + min)
#define ARRAY_SIZE(x) (sizeof(x) / sizeof((x)[0]))
#define BZERO(x, x_size) memset(x, 0, x_size)
#define TIMING(a) \
  do { \
    clock_t start = clock(); \
    a; \
    clock_t end = clock(); \
    printf("%s: %f sec\n", #a, (double)(end-start)/CLOCKS_PER_SEC); \
  } while(0)


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

/**
 * @brief compare two integers
 * 
 * @param a first element of comparison  
 * @param b second element of comparison
 * @return -1 if a<b, 0 if a=b, 1 if a>b
 */
int compare_int(void *a, void *b);

/**
 * @brief compare two longs
 * 
 * @param a first element of comparison  
 * @param b second element of comparison
 * @return -1 if a<b, 0 if a=b, 1 if a>b
 */
int compare_long(void *a, void *b);

/**
 * @brief compare two floats
 * 
 * @param a first element of comparison  
 * @param b second element of comparison
 * @return -1 if a<b, 0 if a=b, 1 if a>b
 */
int compare_float(void *a, void *b);

/**
 * @brief compare two doubles
 * 
 * @param a first element of comparison  
 * @param b second element of comparison
 * @return -1 if a<b, 0 if a=b, 1 if a>b
 */
int compare_double(void *a, void *b);

/**
 * @brief compare two chars
 * 
 * @param a first element of comparison  
 * @param b second element of comparison
 * @return -1 if a<b, 0 if a=b, 1 if a>b
 */
int compare_char(void *a, void *b);

/**
 * @brief compare two strings
 * 
 * @param a first element of comparison  
 * @param b second element of comparison
 * @return -1 if a<b, 0 if a=b, 1 if a>b
 */
int compare_string(void *a, void *b);

/**
 * @brief checks if two pointer have value null and sets res accordingly
 * 
 * @param a first element of comparison 
 * @param b second element of comparison
 * @param res set to -1 if a is null, 1 if b is null, 0 if both are null
 * @return 1 if a or b are null, 0 otherwise 
 */
int is_null(void *a, void *b, int *res);

/**
 * @brief dispose string memory
 * 
 * @param a char array (string) to dispose
 */
void free_string(void *a);
