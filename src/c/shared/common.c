#include "common.h"

int compare_int(int a, int b)
{
  if(a<b) return -1;
  if(a>b) return 1;
  return 0;
}

int compare_long(long a, long b)
{
  if(a<b) return -1;
  if(a>b) return 1;
  return 0;
}

int compare_float(float a, float b)
{
  if(a<b) return -1;
  if(a>b) return 1;
  return 0;
}

int compare_double(double a, double b)
{
  if(a<b) return -1;
  if(a>b) return 1;
  return 0;
}

int compare_char(char a, char b)
{
  if(a<b) return -1;
  if(a>b) return 1;
  return 0; 
}

int compare_string(char *a, char *b)
{
  int res=strcmp(a, b);
  if(res<0) return -1;
  if(res>0) return 1;
  return 0; 
}