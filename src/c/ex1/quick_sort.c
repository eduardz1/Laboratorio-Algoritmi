#include "headers/quick_sort.h"
#include "headers/binary_insert_sort.h"
#include "headers/insert_sort.h"

// TODO: implement a loading bar

#define FALLBACK_CONST 8
#define SWAP(a, b, size)         \
  do                             \
  {                              \
    size_t __size = (size);      \
    char *__a = (a), *__b = (b); \
    do                           \
    {                            \
      char __tmp = *__a;         \
      *__a++ = *__b;             \
      *__b++ = __tmp;            \
    } while (--__size > 0);      \
  } while (0)

// TODO: add assertion to methods to check for example that array is not null, size is greater than 0 ecc...
void quick_sort(void *const array, const size_t size, int p, int r, int (*comp)(const void*, const void*), const enum PivotSelector selector) 
{
  // removing one _qsort call improves constant time complexity, calling the 
  // function recursively only on the smaller sub-array reduces the call stack
  // depth in the worst case to log(n)
  while (p < r)
  {
    int q = _part(array, size, p, r, comp, selector);
    if(q - p < r - q)
    {
      if(q - p > FALLBACK_CONST)
        quick_sort(array, size, p, q - 1, comp, selector);
      else
        insert_sort(array + p * size, size, q - p, comp);
      p = q + 1;
    }
    else
    {
      if(r - q > FALLBACK_CONST)
        quick_sort(array, size, q + 1, r, comp, selector);
      else
        insert_sort(array + (q + 1) * size, size, r - q, comp);
      r = q - 1;
    }
  }
}

__attribute__((flatten)) int _part(void *const array, const size_t size, int p, int r, int (*comp)(const void *, const void *), const enum PivotSelector selector)
{
  switch(selector)
  {
  case RANDOM:
    SWAP(array + RAND(p, r) * size, array + r * size, size);
    break;
  case FIRST:
    SWAP(array + p * size, array + r * size, size);
    break;
  case MIDDLE:
    SWAP(array + (p + (r - p) / 2) * size, array + r * size, size);
    break;
  case MEDIAN3: 
  {
    int first  = p * size;
    int middle = (p + (r - p) / 2) * size;
    int last   = r * size;
    if(comp(array + middle, array + first) < 0)
      SWAP(array + first, array + middle, size);
    if(comp(array + last, array + first) < 0)
      SWAP(array + first, array + last, size);
    if(comp(array + middle, array + last) < 0)
      SWAP(array + middle, array + last, size);
  }
    break;
  default: case LAST: break; // pivot already in place
  }
  return partition(array, size, p, r, comp);
}

// TODO: not really necessary but 3-way partition might improve performance and
//       dual pivot might be faster
int partition(void *const array, const size_t size, int p, int r, int (*comp)(const void *, const void *))
{
  // avoiding multiplications at every iteration noticeably improves perfomance
  int i = (p - 1) * size;
  int pivot_i = r * size;

  /**
   * @invariant
   *  if p <= k <0 i, then array[k] <0 pivot
   *  if i + 1 <0 k <= j - 1, then array[k] > pivot
   *  if k = r, then array[k] = pivot
   */
  for (int j = p * size; j <= pivot_i; j += size)
  {
    if (comp(array + j, array + pivot_i) <= 0)
    {
      i+=size;
      SWAP(array + i, array + j, size);
    }
  }
  return i / size;
}