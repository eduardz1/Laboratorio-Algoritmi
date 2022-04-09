#include "headers/binary_insert_sort.h"
#include "headers/quick_sort.h"
#include "../shared/common.h"
#include "../shared/record.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void load_array(const char* file_name, struct _record *array, int size)
{
  FILE *fp = fopen(file_name, "r");
  if(fp == NULL) {
    printf("Error opening file\n");
    exit(EXIT_FAILURE);
  }

  char buffer[100];
  for (int i = 0; fgets(buffer, sizeof(buffer), fp) != NULL; i++)
  {
    array[i].field1 = malloc(64);
    sscanf(buffer, "%d,%s,%d,%lf", &array[i].id, array[i].field1, &array[i].field2, &array[i].field3);
  }
  
  fclose(fp);
}

int main(int argc, char const *argv[])
{
  if(argc < 3) {
    printf("Usage: ordered_array_main <file_name> <num_records>\n");
    exit(EXIT_FAILURE);
  }   
  
  struct _record *arr = malloc(sizeof(struct _record) * atoi(argv[2]));
  printf("Loading array from file %s\n", argv[1]);
  load_array(argv[1], arr, atoi(argv[2]));
  printf("Unsorted array:\n");
  for(int i = 0; i < atoi(argv[2]); i++)
    printf("%d, %s, %d, %lf\n", arr[i].id, arr[i].field1, arr[i].field2, arr[i].field3);
  printf("\n");

  printf("Choose a sorting algorithm: [qsort]/[binssort]\n");
  char input[10];
  scanf("%s", input);
  if(strcmp(input, "qsort") == 0) 
  {
    quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) - 1, compare_records);
  } 
  else if(strcmp(input, "binssort") == 0) 
  {
    binary_insert_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) - 1, compare_records);
  } 
  else 
  {
    printf("Invalid input\n");
    exit(EXIT_FAILURE);
  }


  printf("\nSorted array:\n");
  for (int i = 0; i < atoi(argv[2]); i++)
    printf("%d,%s,%d,%lf\n", arr[i].id, arr[i].field1, arr[i].field2, arr[i].field3);
  
  return (EXIT_SUCCESS);
}
