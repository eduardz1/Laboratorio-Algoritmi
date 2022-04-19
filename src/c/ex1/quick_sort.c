#include "headers/quick_sort.h"
#include "headers/binary_insert_sort.h"

// TODO: implement a loading bar

#define FALLBACK_CONST 8

void quick_sort( void* array, size_t size, int p, int r, int (*comp)(void*, void*), enum PivotSelector selector) 
{
  // removing one _qsort call improves constant time complexity, calling the 
  // function recursively only on the smaller sub-array reduces the call stack
  // depth in the worst case to log(n)
  while (p < r)
  {
    int q = _part(array, size, p, r, comp, selector);
  #ifndef FALLBACK_BIS
    if(q - p < r - q)
    {
      quick_sort(array, size, p, q - 1, comp, selector);
      p = q + 1;
    }
    else
    {
      quick_sort(array, size, q + 1, r, comp, selector);
      r = q - 1;
    }
  #endif
  #ifdef FALLBACK_BIS
    if(q - p < r - q)
    {
      if(q - p > FALLBACK_CONST)
        quick_sort(array, size, p, q - 1, comp, selector);
      else
        binary_insert_sort(array + p * size, size, q - p, comp);
      p = q + 1;
    }
    else
    {
      if(r - q > FALLBACK_CONST)
        quick_sort(array, size, q + 1, r, comp, selector);
      else
        binary_insert_sort(array + (q + 1) * size, size, r - q, comp);
      r = q - 1;
    }
  #endif
  }
}

int _part(void *array, size_t size, int p, int r, int (*comp)(void *, void *), enum PivotSelector selector)
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
      int first  = p * size;
      int middle = ((p + r) / 2) * size;
      int last   = r * size;
      if(comp(array + first, array + middle) > 0)
        pivot = comp(array + middle, array + last) > 0 ? array + middle : array + last;
      else
        pivot = comp(array + first, array + last) > 0 ? array + first : array + last;
    }
  }
  swap(pivot, array + r * size, size);
  return partition(array, size, p, r, comp);
}

int partition(void *array, size_t size, int p, int r, int (*comp)(void *, void *))
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
