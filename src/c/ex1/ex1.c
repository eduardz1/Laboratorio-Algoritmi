#include "headers/binary_insert_sort.h"
#include "headers/quick_sort.h"
#include "../shared/common.h"
#include "../shared/record.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define TIMING(a) \
  do { \
    clock_t start = clock(); \
    a; \
    clock_t end = clock(); \
    printf("%s: %f sec\n", #a, (double)(end-start)/CLOCKS_PER_SEC); \
  } while(0)

void load_array(const char* file_name, struct Record *array, int size)
{
  FILE *fp = fopen(file_name, "r");
  if(fp == NULL)
  {
    printf("Error opening file\n");
    exit(EXIT_FAILURE);
  }

  printf("Loading array from file \033[1m%s\033[22m \0337\033[5m...\n", file_name);
  char buffer[128];
  for (int i = 0; fgets(buffer, sizeof(buffer), fp) != NULL && i < size; i++)
  {
    array[i].field1 = malloc(64);
    sscanf(buffer, "%d,%63[^,],%d,%lf", &array[i].id, array[i].field1, &array[i].field2, &array[i].field3);
  }
  printf("\033[25m\0338\033[32mdone\033[0m\n");
  
  fclose(fp);
}

void print_records(struct Record *array, int size)
{
  for (int i = 0; i < size; i++)
    printf("%d,%s,%d,%lf\n", array[i].id, array[i].field1, array[i].field2, array[i].field3);
}

int main(int argc, char const *argv[])
{
  if(argc < 3) 
  {
    printf("Usage: ordered_array_main <file_name> <num_records>\n");
    exit(EXIT_FAILURE);
  }

  srand(time(NULL));

  printf("Choose a sorting algorithm: [qsort]/[binssort] ");
  char input[10];
  scanf("%s", input);
  
  struct Record *arr = malloc(sizeof(struct Record) * atoi(argv[2])); 
  load_array(argv[1], arr, atoi(argv[2]));

#ifdef PRINT_RECORDS
  printf("\nUnsorted records:\n");
  print_records(arr, atoi(argv[2]));
#endif

  if(strcmp(input, "qsort") == 0) 
  {
    TIMING(quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) - 1, compare_records, MEDIAN3));
    
    for(int i = 0; i < atoi(argv[2]); i++)
      free(arr[i].field1);
    load_array(argv[1], arr, atoi(argv[2]));

    TIMING(quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) - 1, compare_records, RANDOM));
    
    for(int i = 0; i < atoi(argv[2]); i++)
      free(arr[i].field1);
    load_array(argv[1], arr, atoi(argv[2]));

    TIMING(quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) - 1, compare_records, FIRST));
    
    for(int i = 0; i < atoi(argv[2]); i++)
      free(arr[i].field1);
    load_array(argv[1], arr, atoi(argv[2]));

    TIMING(quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) - 1, compare_records, MIDDLE));

    for(int i = 0; i < atoi(argv[2]); i++)
      free(arr[i].field1);
    load_array(argv[1], arr, atoi(argv[2]));

    TIMING(quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) - 1, compare_records, LAST));
    
    printf("Starting test the first <size/100> sorted elements of the input array \
    to see the difference between MEDIAN3 and LAST as pivot\n");
    TIMING(quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) / 100 - 1, compare_records, MEDIAN3));
    TIMING(quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) / 100 - 1, compare_records, LAST));
  } 
  else if(strcmp(input, "binssort") == 0) 
  {
    TIMING(binary_insert_sort(arr, sizeof(arr[0]), atoi(argv[2]), compare_records));
  } 
  else 
  {
    printf("Invalid input\n");
    exit(EXIT_FAILURE);
  }

#ifdef PRINT_RECORDS
  printf("\nSorted records:\n");
  print_records(arr, atoi(argv[2]));
#endif
  
  for(int i = 0; i < atoi(argv[2]); i++)
    free(arr[i].field1);
  free(arr);
  return (EXIT_SUCCESS);
}
