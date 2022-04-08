#pragma once
#include <string.h>

/**

 * @brief compare two integers
 * 
 * @param a first element of comparison  
 * @param b second element of comparison
 * @return -1 if a<b, 0 if a=b, 1 if a>b
 */
int compare_int(int a, int b);

/**
 * @brief compare two longs
 * 
 * @param a first element of comparison  
 * @param b second element of comparison
 * @return -1 if a<b, 0 if a=b, 1 if a>b
 */
int compare_long(long a, long b);

/**
 * @brief compare two floats
 * 
 * @param a first element of comparison  
 * @param b second element of comparison
 * @return -1 if a<b, 0 if a=b, 1 if a>b
 */
int compare_float(float a, float b);

/**
 * @brief compare two doubles
 * 
 * @param a first element of comparison  
 * @param b second element of comparison
 * @return -1 if a<b, 0 if a=b, 1 if a>b
 */
int compare_double(double a, double b);
/**
 * @brief compare two chars
 * 
 * @param a first element of comparison  
 * @param b second element of comparison
 * @return -1 if a<b, 0 if a=b, 1 if a>b
 */
int compare_char(char a, char b);

/**
 * @brief compare two strings
 * 
 * @param a first element of comparison  
 * @param b second element of comparison
 * @return -1 if a<b, 0 if a=b, 1 if a>b
 */
int compare_string(char *a, char *b);
