#include "record.h"

int compare_records(void *a,void*b)
{
  struct Record *r1 = (struct Record *)a;
  struct Record *r2 = (struct Record *)b;
  int res = compare_string(&r1->field1, &r2->field1);
  if (res) return res;
  int res2 = compare_int(&r1->field2, &r2->field2);
  return res2 ? res2 : compare_double(&r1->field3, &r2->field3);
}

void print_records(struct Record *array, int size)
{
  for (int i = 0; i < size; i++)
    printf("%d,%s,%d,%lf\n", array[i].id, array[i].field1, array[i].field2, array[i].field3);
}

void free_record(void *a) 
{
    struct Record *r1 = (struct Record *)a;
    free_string(r1->field1);
}