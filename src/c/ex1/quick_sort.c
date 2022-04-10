#include "headers/quick_sort.h"

#define RAND(min, max) ((rand() % (max - min + 1)) + min)

void quick_sort_pivot_selection(void* array, size_t size, int p, int r, int (*comp)(void*, void*), enum pivot_selector selector) 
{
  _qsort(array, size, p, r, comp, selector);
}

void quick_sort(void *array, size_t size, int p, int r, int (*comp)(void *, void *))
{
  _qsort(array, size, p, r, comp, MEDIAN3);
}

void _qsort( void* array, size_t size, int p, int r, int (*comp)(void*, void*), enum pivot_selector selector) 
{
  if (p >= r) return;

  int q = _part(array, size, p, r, comp, selector);
  _qsort(array, size, p, q - 1, comp, selector);
  _qsort(array, size, q + 1, r, comp, selector);
}

int _part(void *array, size_t size, int p, int r, int (*comp)(void *, void *), enum pivot_selector selector)
{
  void *pivot;
  switch(selector)
  {
  case RANDOM: pivot = array + RAND(p, r) * size; break;
  case FIRST:  pivot = array + p * size; break;
  case MIDDLE: pivot = array + ((p + r) / 2) * size ; break;
  case LAST:   pivot = array + r * size; break;
  case MEDIAN3: default: 
      {
        int a = RAND(p, r) * size;
        int b = RAND(p, r) * size;
        int c = RAND(p, r) * size;
        if(comp(array + a, array + b) > 0)
          pivot = comp(array + b, array + c) > 0 ? array + b : array + c;
        else
          pivot = comp(array + a, array + c) > 0 ? array + a : array + c;
      }
  }
  swap(pivot, array + r * size, size);
  return partition(array, size, p, r, comp, selector);
}

int partition(void *array, size_t size, int p, int r, int (*comp)(void *, void *), enum pivot_selector selector)
{
  void *pivot = array + r * size;
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
