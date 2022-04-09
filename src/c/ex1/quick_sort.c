#include "headers/quick_sort.h"

void quick_sort(void *array, size_t size, int p, int r, int (*comp)(void *, void *))
{
  if (p >= r)
    return;

  int q = partition(array, size, p, r, comp);
  quick_sort(array, size, p, q - 1, comp);
  quick_sort(array, size, q + 1, r, comp);
}

int partition(void *array, size_t size, int p, int r, int (*comp)(void *, void *))
{
  void *pivot = array + r * size; // alternative &array[RAND(p, r)]
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
