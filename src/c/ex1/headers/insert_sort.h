#pragma once
#include "../../shared/common.h"

void insert_sort(void *const array, const size_t size, const int lenght, int (*comp)(const void*, const void*));
