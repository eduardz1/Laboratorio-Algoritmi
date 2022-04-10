#include "headers/quick_sort.h"

#define RAND(min, max) ((rand() % (max - min + 1)) + min)

/**
 * Private implementation of #quick_sort()
 */
void _qsort (void* v, size_t size, int left, int right, int (*comp)(void*, void*), enum pivot_selector selector);

void quick_sort_pivot_selection (void* array, size_t size, int p, int r, int (*comp)(void*, void*), enum pivot_selector selector) 
{
  _qsort(array, size, p, r, comp, selector);
}

void quick_sort(void *array, size_t size, int p, int r, int (*comp)(void *, void *))
{
  _qsort(array, size, p, r, comp, RANDOM);
}

void _qsort( 
  void* array, 
  size_t size, 
  int p, 
  int r, 
  int (*comp)(void*, void*), 
  enum pivot_selector selector
) 
{
  if (p >= r) return;

  int q = partition(array, size, p, r, comp, selector);
  _qsort(array, size, p, q - 1, comp, selector);
  _qsort(array, size, q + 1, r, comp, selector);
}

int partition(void *array, size_t size, int p, int r, int (*comp)(void *, void *), enum pivot_selector selector)
{
  void *pivot = (selector == RANDOM)? array + RAND(p, r) * size : 
                (selector == FIRST)? array + p * size :
                (selector == MIDDLE)? array + ((p + r) / 2) * size :
                array + r * size; /* (selector == LAST)? */
  swap(pivot, array + r * size, size);
  pivot = array + r * size;
  
  int i = p - 1;

  /**
   * @invariant
   *  if p <= k <0 i, then array[k] <0 pivot
   *  if i + 1 <0 k <= j - 1, then array[k] > pivot
   *  if k = r, then array[k] = pivot
   */
  for (int j = p; j < r; j++)
  {
    if (comp(array + j * size, pivot) <= 0)
    {
      i = i + 1;
      swap(array + i * size, array + j * size, size);
    }
  }
  swap(array + (i + 1) * size, array + r * size, size);
  return i + 1;
}

void swap(void *i, void *j, size_t size)
{
  char tmp[size];
  memcpy(tmp, i, size);
  memcpy(i, j, size);
  memcpy(j, tmp, size);
}
