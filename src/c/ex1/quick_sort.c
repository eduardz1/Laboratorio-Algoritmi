#include "headers/quick_sort.h"
#include "headers/binary_insert_sort.h"
#include "headers/insert_sort.h"

// TODO: implement a loading bar

#define FALLBACK_CONST 8
#define SWAP(a, b, size)                                                      \
  do                                                                              \
    {                                                                              \
      size_t __size = (size);                                                      \
      char *__a = (a), *__b = (b);                                              \
      do                                                                      \
        {                                                                      \
          char __tmp = *__a;                                                      \
          *__a++ = *__b;                                                      \
          *__b++ = __tmp;                                                      \
        } while (--__size > 0);                                                      \
    } while (0)

// TODO: add assertion to methods to check for example that array is not null, size is greater than 0 ecc...
void quick_sort( void* array, size_t size, int p, int r, int (*comp)(const void*, const void*), enum PivotSelector selector) 
{
  // removing one _qsort call improves constant time complexity, calling the 
  // function recursively only on the smaller sub-array reduces the call stack
  // depth in the worst case to log(n)
  while (p < r)
  {
    int q = _part2(array, size, p, r, comp, selector);
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

int _part2(void *array, size_t size, int p, int r, int (*comp)(const void *, const void *), enum PivotSelector selector)
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
  return partition5(array, size, p, r, comp);
}

int _part3(void *array, size_t size, int p, int r, int (*comp)(const void *, const void *), enum PivotSelector selector)
{
  int first  = p * size;
  int second = ((p + ((p + (r - p) / 2) - p) / 2)) * size;
  int middle = (p + (r - p) / 2) * size;
  int third  = (((p + (r - p) / 2) + (r - (p + (r - p) / 2)) / 2)) * size;
  int last   = r * size;
  if(comp(array + middle, array + first) < 0)
    SWAP(array + first, array + middle, size);
  if(comp(array + third, array + second) < 0)
    SWAP(array + third, array + second, size);
  if(comp(array + third, array + middle) < 0)
  {
    SWAP(array + middle, array + third, size);
    SWAP(array + first, array + second, size);
  }
  if(comp(array + last, array + second) < 0)
    SWAP(array + last, array + second, size);
  if(comp(array + last, array + middle) < 0)
  {
    SWAP(array + last, array + middle, size);
    if(comp(array + middle, array + first) < 0)
      SWAP(array + middle, array + first, size);
  }
  else
  {
    if(comp(array + middle, array + second) < 0)
      SWAP(array + middle, array + second, size);
  }
  SWAP(array + middle, array + last, size);
  return partition5(array, size, p, r, comp);
}

int _part(void *array, size_t size, int p, int r, int (*comp)(const void *, const void *), enum PivotSelector selector)
{
  void *pivot;
  switch(selector)
  {
  case RANDOM:
    pivot = array + RAND(p, r) * size;
    swap(pivot, array + r * size, size);
    break;
  case FIRST:
    pivot = array + p * size;
    swap(pivot, array + r * size, size);
    break;
  case MIDDLE:
    pivot = array + (p + (r - p) / 2) * size;
    swap(pivot, array + r * size, size);
    break;
  case LAST:
    pivot = array + r * size;
    swap(pivot, array + r * size, size);
    break;
  case MEDIAN3: default: 
    {
      int first  = p * size;
      int middle = (p + (r - p) / 2) * size;
      int last   = r * size;
      if(comp(array + middle, array + first) < 0)
        swap(array + first, array + middle, size);
      if(comp(array + last, array + first) < 0)
        swap(array + first, array + last, size);
      if(comp(array + middle, array + last) < 0)
        swap(array + middle, array + last, size);
    }
  }
  return partition5(array, size, p, r, comp);
}

int partition5(void *array, size_t size, int p, int r, int (*comp)(const void *, const void *))
{
  void *pivot = array + (r) * size;
  while(p <= r)
  {
    while(comp(array + p * size, pivot) < 0)
      p++;
    while(comp(array + r * size, pivot) > 0)
      r--;
    if(p <= r)
    {
      SWAP(array + p * size, array + r * size, size);
      p++;
      r--;
    }
  }
  SWAP(array + p * size, pivot, size);
  return p;
}

// TODO: not really necessary but 3-way partition might improve performance and
//       dual pivot might be faster
int partition(void *array, size_t size, int p, int r, int (*comp)(const void *, const void *))
{
  int i = p - 1;

  /**
   * @invariant
   *  if p <= k <0 i, then array[k] <0 pivot
   *  if i + 1 <0 k <= j - 1, then array[k] > pivot
   *  if k = r, then array[k] = pivot
   */
  for (int j = p; j <= r; j++)
  {
    if (comp(array + j * size, array + r * size) <= 0)
    {
      i++;
      SWAP(array + i * size, array + j * size, size);
    }
  }
  return i;
}

int partition2(void *array, size_t size, int p, int r, int (*comp)(void *, void *))
{
  void *pivot = array + r * size;
  int i = p - 1;
  
  char tmp[size];
  for (int j = p; j <= r; j++)
  {
    memcpy(tmp, array + j * size, size);
    if (comp(array + j * size, pivot) <= 0)
    {
      i += 1;
      memcpy(array + j * size, array + i * size, size);
      memcpy(array + i * size, tmp, size);
    }
    else
    {
      i += 0;
      memcpy(array + j * size, tmp, size);
      memcpy(array + i * size, array + i * size, size);
    }
    /*
    int cond = -(int)(comp(tmp, pivot) < 0); // if true -> s == 0xFFFFFFFF, if false -> s == 0x00000000
    int delta = cond & (j - i);
    printf("cond: %x\n", cond);

    j -= cond; // increment when cond true

    memcpy(array + (j - delta) * size, array + j * size, size);
    memcpy(array + (i + delta) * size, tmp, size);*/
  }
  // swap(array + (i + 1) * size, array + r * size, size);
  return i;
}

int partition3(void *array, size_t size, int p, int r, int (*comp)(void *, void *))
{
  void *pivot = array + r * size;
  int i = p;

  /**
   * @invariant
   *  if p <= k <0 i, then array[k] <0 pivot
   *  if i + 1 <0 k <= j - 1, then array[k] > pivot
   *  if k = r, then array[k] = pivot
   */
  for (int j = p; j <= r; j++)
  {
    i += swap_cond(comp(array + j * size, pivot) <= 0, array + i * size, array + j * size, size);
  }
  return i - 1;
}

inline void swap(void *i, void *j, size_t size)
{
  char tmp[size];
  memmove(tmp, i, size);
  memmove(i, j, size);
  memmove(j, tmp, size);
}

//fail
inline bool swap_cond(bool cond, void *i, void *j, size_t size)
{
  char tmp[2][size];
  memcpy(tmp[cond], i, size);
  memcpy(tmp[1 - cond], j, size);
  // void *v[2] = {tmp, j};

  memcpy(i, tmp[0], size);
  memcpy(j, tmp[1], size);
  return cond;
}

/*
inline bool swap_cond(bool cond, void *i, void *j, size_t size)
{
  if(cond)
  {
    char tmp[size];
    memcpy(tmp, i, size);
    memcpy(i, j, size);
    memcpy(j, tmp, size);
  }
  return cond;
}*/

/*
void swap_cond2(bool cond, void *i, void *j, size_t size)
{
  char tmp[2][size];
  memcpy(tmp[0], i, size);
  memcpy(tmp[1], j, size);

  memcpy(i, tmp[cond], size);
  memcpy(j, tmp[1 - cond], size);
}*/