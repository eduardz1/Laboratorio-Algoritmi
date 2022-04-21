#include "headers/binary_insert_sort.h"
#include "headers/quick_sort.h"
#include "../shared/common.h"
#include "../shared/record.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

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
    if(array[i].field1 == NULL)
    {
      printf("Error allocating memory\n");
      exit(EXIT_FAILURE);
    }
    sscanf(buffer, "%d,%63[^,],%d,%lf", &array[i].id, array[i].field1, &array[i].field2, &array[i].field3);
  }
  printf("\033[25m\0338\033[32mdone\033[0m\n");
  
  fclose(fp);
}

void dispose_string_in_array(struct Record * a, int length) {
  for(int i = 0; i < length; i++)
      free(a[i].field1);
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
  (void)!scanf("%s", input); // scanf is not really safe, I'm casting the return into the void, we can use a better function
  
  struct Record *arr = calloc(atoi(argv[2]), sizeof(struct Record));
  if(arr == NULL && atoi(argv[2]) > 0)
  {
    printf("Error allocating memory\n");
    exit(EXIT_FAILURE);
  }

  #ifdef PRINT_RECORDS
    printf("\nUnsorted records:\n");
    print_records(arr, atoi(argv[2]));
    printf("\n");
  #endif

  if(strcmp(input, "qsort") == 0) 
  {
    double time[5] = {};
    enum PivotSelector enums[5] = {MEDIAN3, RANDOM, FIRST, MIDDLE, LAST};

    FILE *fp = fopen("time_log_qsort.csv", "a+");
    
    // Check fis file is empty
    int c = fgetc(fp);
    if (c == EOF)
      fprintf(fp, "MEDIAN3; RANDOM; FIRST; MIDDLE; LAST\n");

    for (size_t i = 0; i < NUMBER_OF_TEST_TO_DO; i++)
    {
      for (int i = 0; i < 5; i++)
      {
        load_array(argv[1], arr, atoi(argv[2]));
        clock_t start = clock();
        quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) - 1, compare_records, enums[i]);
        clock_t end = clock();
        dispose_string_in_array(arr, atoi(argv[2]));
        time[i] = (double)(end-start)/CLOCKS_PER_SEC;
      };

      // Prepare log
      char * buf = calloc(100, sizeof(char));
      if(buf == NULL)
      {
        printf("Error allocating memory\n");
        exit(EXIT_FAILURE);
      }
      for (int i = 0; i < 5; i++)
        sprintf(buf,"%s%f%s",buf,time[i], i == 4 ? "" : ";");

      // Write log
      fprintf(fp, "%s", buf);
      fprintf(fp, "\n");
      free(buf);
      fflush(fp);

    }

    fclose(fp);
    // printf("\nStarting test the first <size/100> sorted elements of the input array to see the difference between MEDIAN3, RANDOM and LAST as pivot\n\n");
    // TIMING(quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) / 100 - 1, compare_records, RANDOM));
    // TIMING(quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) / 100 - 1, compare_records, MEDIAN3));
    // TIMING(quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) / 100 - 1, compare_records, LAST));
  } 
  else if(strcmp(input, "binssort") == 0) 
  {

    FILE *fp = fopen("time_log_insertsort.csv", "a+");
    
    double time;
    for (size_t i = 0; i < NUMBER_OF_TEST_TO_DO; i++) {
      load_array(argv[1], arr, atoi(argv[2]));
      clock_t start = clock();
      binary_insert_sort(arr, sizeof(arr[0]), atoi(argv[2]), compare_records);
      clock_t end = clock();
      dispose_string_in_array(arr, atoi(argv[2]));
      time = (double)(end-start)/CLOCKS_PER_SEC;

      // Prepare log
      char * buf = calloc(30, sizeof(char));
      if(buf == NULL)
      {
        printf("Error allocating memory\n");
        exit(EXIT_FAILURE);
      }
      sprintf(buf,"%f",time);

      // Write log
      fprintf(fp, "%s", buf);
      fprintf(fp, "\n");
      free(buf);
      fflush(fp);
  
    }


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

  // dispose_string_in_array(arr, atoi(argv[2]));
  free(arr);
  return (EXIT_SUCCESS);
}
