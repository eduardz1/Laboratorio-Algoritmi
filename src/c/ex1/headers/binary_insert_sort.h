#pragma once
#include "../../shared/common.h"

// FIXME: write descriptive comment
void binary_insert_sort(void* array, size_t size, int lenght, int (*comp)(void*, void*));
// TODO: write descriptive comment 
int binary_search(void* array, size_t size, int left, int right, int (*comp)(void*, void*), void* key);