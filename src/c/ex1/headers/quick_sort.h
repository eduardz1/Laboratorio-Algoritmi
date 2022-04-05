#pragma once

#include "../../shared/common.h"

void quick_sort(void* v, int size, int left, int right, int (*comp)(void*, void*));
