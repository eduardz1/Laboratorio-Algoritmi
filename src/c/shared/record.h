#pragma once
#include "common.h"

struct _record
{
  int id;
  char *field1;
  int  field2;
  double field3;
};

/**
 * @brief compare two records based on their fields
 * @details 
 *  calls #compare_string on the first field, if the two strings are equal, 
 *  calls #compare_int on the second field, if the two integers are equal,
 *  calls #compare_double on the third field
 * @param a first element of comparison  
 * @param b second element of comparison
 * @return -1 if a<b, 0 if a=b, 1 if a>b
 * 
 */
int compare_records(struct _record a,struct _record b);