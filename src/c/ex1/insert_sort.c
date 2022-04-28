#include "headers/insert_sort.h"

void insert_sort(void* array, size_t size, int length, int (*comp)(const void*, const void*))
{
  for(int j=1; j<length; ++j)
  {
    char key[size]; 
    memcpy(key, array + j * size, size);
    int pos = j - 1;
    while(pos >= 0 && comp(key, array + pos * size) < 0)
    {
      memmove(array + (pos + 1) * size, array + pos * size, size);
      pos--;
    }

    // Shifts all the elements greater than array[j] right by 1 unit
    memmove(array + (pos + 1) * size, key, size);
  }
}