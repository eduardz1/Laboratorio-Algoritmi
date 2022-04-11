#pragma once

#include "../../shared/common.h"
#include <stdio.h>
#include <stdlib.h>

enum pivot_selector {
  RANDOM,
  FIRST,
  MIDDLE,
  LAST,
  MEDIAN3
};

/**
 * @brief Allows selection of pivot for #partition()
 */
int _part(void *array, size_t size, int p, int r, int (*comp)(void *, void *), enum pivot_selector selector);

/**
 * @brief quick sort of generic array
 * 
 * @param array array of generic elements
 * @param size is the size of single element of the array
 * @param p index of the first element in #partition range
 * @param r index of the last element in #partition range 
 * @param comp pointer to the compare function desired for a type
 * @param selector allows to choose a pivot between { FIRST, LAST, MIDDLE, RANDOM, MEDIAN3 }, MEDIAN3 is the sggested one
 */
void quick_sort(void* v, size_t size, int left, int right, int (*comp)(void*, void*), enum pivot_selector selector);

/**
 * @brief partions the array in the specified range [p, r] using r as pivot
 *  
 * @param array array of generic elements
 * @param size is the size of single element of the array
 * @param p index of the first element in #partition range
 * @param r index of the last element in #partition range 
 * @param comp pointer to the compare function desired for a type
 */
int partition(void* array, size_t size, int p, int r, int (*comp)(void*, void*));

/**
 * @brief swaps value of two generic variables
 * 
 */
void swap(void* i, void*j, size_t size);
