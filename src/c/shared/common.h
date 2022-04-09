#pragma once
#include <string.h>

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

int is_null(void *a, void *b, int *res);
