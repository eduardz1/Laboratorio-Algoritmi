#include "record.h"

int compare_records(struct _record a,struct _record b)
{
  int res = compare_string(a.field1, b.field1);
  if (res) return res;
  int res2 = compare_int(a.field2, b.field2);
  return res2 ? res2 : compare_double(a.field3, b.field3);
}