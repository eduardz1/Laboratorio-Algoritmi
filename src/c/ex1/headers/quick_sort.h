#pragma once

#include "../../shared/common.h"

/**
 * @brief quick sort of generic array
 * 
 * @param array array of generic elements
 * @param size is the size of single element of the array
 * @param p index of the first element in #partition range
 * @param r index of the last element in #partition range 
 * @param comp pointer to the compare function desired for a type 
 */
void quick_sort(void* v, size_t size, int left, int right, int (*comp)(void*, void*));

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
 * @brief value of two generic variables
 * 
 */
void swap(void* i, void*j, size_t size);
