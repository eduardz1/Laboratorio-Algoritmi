#include "record.h"

int compare_records(struct _record a,struct _record b)
{
  int res1 = compare_string(a.field1, b.field1);
  int res2 = compare_int(a.field2, b.field2);
  int res3 = compare_double(a.field3, b.field3);

  if(res1==0)
  {
    if(res2==0) return res3;
    else return res2;
  }
  return res1;
}