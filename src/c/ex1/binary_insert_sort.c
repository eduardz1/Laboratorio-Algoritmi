#include "headers/binary_insert_sort.h"

void binary_insert_sort(void* array, size_t size, int length, int (*comp)(void*, void*))
{
  for(int j=1; j<length; ++j)
  {
    char key[size]; 
    memcpy(key, array + j * size, size);
    int pos = binary_search(array, size, 0, j - 1, comp, key);

    // Shifts all the elements greater than array[j] right by 1 unit
    memmove(array + (pos + 1) * size, array + pos * size, (j - pos) * size);
    memmove(array + pos * size, key, size);
  }
}

int binary_search(void* array, size_t size, int left, int right, int (*comp)(void*, void*), void* key)
{
  while(left <= right)
  {
    int mid = left + (right-left)/2;
    int c = comp(key, array + mid * size);
    if(c == 0)
      return mid + 1;
    else if(c > 0)
      left = mid + 1;
    else
      right = mid - 1;
  }
  return left;
}
