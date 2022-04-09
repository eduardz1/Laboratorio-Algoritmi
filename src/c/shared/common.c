#include "common.h"

int compare_int(void *a, void *b)
{
  int *v1 = (int*)a;
  int *v2 = (int*)b;
  if((*v1)<(*v2)) return -1;
  if((*v1)>(*v2)) return 1;
  return 0;
}

int compare_long(void *a, void *b)
{
  long *v1 = (long*)a;
  long *v2 = (long*)b;
  if((*v1)<(*v2)) return -1;
  if((*v1)>(*v2)) return 1;
  return 0;
}

int compare_float(void *a, void *b)
{
  float *v1 = (float*)a;
  float *v2 = (float*)b;
  if((*v1)<(*v2)) return -1;
  if((*v1)>(*v2)) return 1;
  return 0;
}

int compare_double(void *a, void *b)
{
  double *v1 = (double*)a;
  double *v2 = (double*)b;
  if((*v1)<(*v2)) return -1;
  if((*v1)>(*v2)) return 1;
  return 0;
}

int compare_char(void *a, void *b)
{
  char *v1 = (char*)a;
  char *v2 = (char*)b;
  if((*v1)<(*v2)) return -1;
  if((*v1)>(*v2)) return 1;
  return 0; 
}

int compare_string(void *a, void *b)
{
  char *v1 = *(char**)a;
  char *v2 = *(char**)b;
  int res = strcmp(v1, v2);
  if(res<0) return -1;
  if(res>0) return 1;
  return 0; 
}