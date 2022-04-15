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