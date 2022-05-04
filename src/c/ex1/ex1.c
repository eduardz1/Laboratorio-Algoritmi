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

static void shuffle(void *array, size_t n, size_t size) 
{
  char tmp[size];
  char *arr = array;
  size_t stride = size * sizeof(char);

  if (n > 1)
  {
    size_t i;
    for (i = 0; i < n - 1; ++i)
    {
      size_t rnd = (size_t) rand();
      size_t j = i + rnd / (RAND_MAX / (n - i) + 1);

      memcpy(tmp, arr + j * stride, size);
      memcpy(arr + j * stride, arr + i * stride, size);
      memcpy(arr + i * stride, tmp, size);
    }
  }
}

void dispose_string_in_array(struct Record * a, int length) 
{
  for(int i = 0; i < length; i++) 
  {
    free(a[i].field1);
  }
}

void checksum(struct Record * a, int length, Comp comp) 
{
  bool flag = true;
  for(int i = 0; i < length - 1 && flag; i++)
  {
    if(comp(&a[i], &a[i+1]) > 0) flag = false;
  }

  if(flag)
    printf("\033[1m\033[32mchecksum passed\033[0m\n");
  else
    printf("\033[1m\033[31mchecksum failed\033[0m\n");
}

int main(int argc, char const *argv[])
{
  if(argc < 3) 
  {
    printf("Usage: ordered_array_main <file_name> <num_records>\n");
    exit(EXIT_FAILURE);
  }

  srand(time(NULL));

  // Select algorithm
  printf("Choose a sorting algorithm: [qsort]/[binssort] ");
  char input[10];
  ISCANF("%s", input);
  if(strcmp(input, "qsort") != 0 && strcmp(input, "binssort") != 0)
  {
    printf("Invalid input\n");
    exit(EXIT_FAILURE);
  }

  // Load array
  struct Record *const arr = calloc(atoi(argv[2]), sizeof(struct Record));
  if(arr == NULL && atoi(argv[2]) > 0)
  {
    printf("Error allocating memory\n");
    exit(EXIT_FAILURE);
  }

  if(strcmp(input, "qsort") == 0) 
  {
    printf("Input 1 to run on fixed size, 2 to run on increasing size: ");
    char input[10];
    (void)!scanf("%s", input);
    
    double time_sort[15] = {};
    enum Pivot enums[5] = {MEDIAN3, RANDOM, FIRST, MIDDLE, LAST};

    FILE *fp = fopen("time_log_qsort.csv", "a+");
    
    // Check if file is empty
    int c = fgetc(fp);
    if (c == EOF) fprintf(fp, "time;size;pivot;compare;file\n");

    load_array(argv[1], arr, atoi(argv[2]));

    if(strcmp(input, "1") == 0)
    {
      for (size_t i = 0; i < (NUMBER_OF_TEST_TO_DO / 15); i++)
      {
        int i = 0;
        for (; i < 5; i++)
        {
          shuffle(arr, atoi(argv[2]), sizeof(struct Record));
          clock_t start = clock();
          quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) - 1, compare_records_string, enums[i]);
          clock_t end = clock();
          time_sort[i] = (double)(end-start)/CLOCKS_PER_SEC;
        }

        for (; i < 10; i++)
        {
          shuffle(arr, atoi(argv[2]), sizeof(struct Record));
          clock_t start = clock();
          quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) - 1, compare_records_int, enums[i - 5]);
          clock_t end = clock();
          time_sort[i] = (double)(end-start)/CLOCKS_PER_SEC;
        }

        for (; i < 15; i++)
        {
          shuffle(arr, atoi(argv[2]), sizeof(struct Record));
          clock_t start = clock();
          quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) - 1, compare_records_double, enums[i - 10]);
          clock_t end = clock();
          time_sort[i] = (double)(end-start)/CLOCKS_PER_SEC;
        }

        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[0], atoi(argv[2]), "MEDIAN3", "records_string", argv[1]);
        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[1], atoi(argv[2]), "RANDOM",  "records_string", argv[1]);
        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[2], atoi(argv[2]), "FIRST",   "records_string", argv[1]);
        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[3], atoi(argv[2]), "MIDDLE",  "records_string", argv[1]);
        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[4], atoi(argv[2]), "LAST",    "records_string", argv[1]);

        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[5], atoi(argv[2]), "MEDIAN3", "records_int", argv[1]);
        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[6], atoi(argv[2]), "RANDOM",  "records_int", argv[1]);
        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[7], atoi(argv[2]), "FIRST",   "records_int", argv[1]);
        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[8], atoi(argv[2]), "MIDDLE",  "records_int", argv[1]);
        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[9], atoi(argv[2]), "LAST",    "records_int", argv[1]);

        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[10], atoi(argv[2]), "MEDIAN3", "records_double", argv[1]);
        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[11], atoi(argv[2]), "RANDOM",  "records_double", argv[1]);
        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[12], atoi(argv[2]), "FIRST",   "records_double", argv[1]);
        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[13], atoi(argv[2]), "MIDDLE",  "records_double", argv[1]);
        fprintf(fp, "%lf;%d;%s;%s;%s\n", time_sort[14], atoi(argv[2]), "LAST",    "records_double", argv[1]);

        fflush(fp);
      }
    }
    else
    {
      for (size_t size = 0; size < (size_t)atoi(argv[2]); size+=10)
      {
        int i = 0;
        for (; i < 5; i++)
        {
          shuffle(arr, atoi(argv[2]), sizeof(struct Record));
          clock_t start = clock();
          quick_sort(arr, sizeof(arr[0]), 0, size - 1, compare_records_string, enums[i]);
          clock_t end = clock();
          time_sort[i] = (double)(end-start)/CLOCKS_PER_SEC;
        }

        for (; i < 10; i++)
        {
          shuffle(arr, atoi(argv[2]), sizeof(struct Record));
          clock_t start = clock();
          quick_sort(arr, sizeof(arr[0]), 0, size - 1, compare_records_int, enums[i - 5]);
          clock_t end = clock();
          time_sort[i] = (double)(end-start)/CLOCKS_PER_SEC;
        }

        for (; i < 15; i++)
        {
          shuffle(arr, atoi(argv[2]), sizeof(struct Record));
          clock_t start = clock();
          quick_sort(arr, sizeof(arr[0]), 0, size - 1, compare_records_double, enums[i - 10]);
          clock_t end = clock();
          time_sort[i] = (double)(end-start)/CLOCKS_PER_SEC;
        }

        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[0], size, "MEDIAN3", "records_string", argv[1]);
        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[1], size, "RANDOM",  "records_string", argv[1]);
        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[2], size, "FIRST",   "records_string", argv[1]);
        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[3], size, "MIDDLE",  "records_string", argv[1]);
        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[4], size, "LAST",    "records_string", argv[1]);

        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[5], size, "MEDIAN3", "records_int", argv[1]);
        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[6], size, "RANDOM",  "records_int", argv[1]);
        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[7], size, "FIRST",   "records_int", argv[1]);
        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[8], size, "MIDDLE",  "records_int", argv[1]);
        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[9], size, "LAST",    "records_int", argv[1]);

        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[10], size, "MEDIAN3", "records_double", argv[1]);
        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[11], size, "RANDOM",  "records_double", argv[1]);
        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[12], size, "FIRST",   "records_double", argv[1]);
        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[13], size, "MIDDLE",  "records_double", argv[1]);
        fprintf(fp, "%lf;%zu;%s;%s;%s\n", time_sort[14], size, "LAST",    "records_double", argv[1]);

        fflush(fp);
      } 
    }
    
    fclose(fp);
  } 
  else if(strcmp(input, "binssort") == 0) 
  {
    printf("Input 1 to run on fixed size, 2 to run on increasing size: ");
    char input[10];
    (void)!scanf("%s", input);
    
    FILE *fp = fopen("time_log_insertsort.csv", "a+");
    // Check if file is empty
    int c = fgetc(fp);
    if (c == EOF) fprintf(fp, "time;size;compare;file\n");

    load_array(argv[1], arr, atoi(argv[2]));
    double time_sort;

    if(strcmp(input, "1") == 0)
    {
      for (size_t i = 0; i < (NUMBER_OF_TEST_TO_DO); i++)
      {
        shuffle(arr, atoi(argv[2]), sizeof(struct Record));
        clock_t start = clock();
        binary_insert_sort(arr, sizeof(arr[0]), atoi(argv[2]), compare_records_string);
        clock_t end = clock();
        time_sort = (double)(end-start)/CLOCKS_PER_SEC;

        fprintf(fp, "%lf;%d;%s;%s\n", time_sort, atoi(argv[2]), "records_string", argv[1]);

        shuffle(arr, atoi(argv[2]), sizeof(struct Record));
        start = clock();
        binary_insert_sort(arr, sizeof(arr[0]), atoi(argv[2]), compare_records_string);
        end = clock();
        time_sort = (double)(end-start)/CLOCKS_PER_SEC;

        fprintf(fp, "%lf;%d;%s;%s\n", time_sort, atoi(argv[2]), "records_int", argv[1]);

        shuffle(arr, atoi(argv[2]), sizeof(struct Record));
        start = clock();
        binary_insert_sort(arr, sizeof(arr[0]), atoi(argv[2]), compare_records_string);
        end = clock();
        time_sort = (double)(end-start)/CLOCKS_PER_SEC;

        fprintf(fp, "%lf;%d;%s;%s\n", time_sort, atoi(argv[2]), "records_double", argv[1]);

        fflush(fp);
      }
    }
    else
    {
      for (size_t size = 0; size < (size_t)atoi(argv[2]); size+=10)
      {
        shuffle(arr, atoi(argv[2]), sizeof(struct Record));
        clock_t start = clock();
        binary_insert_sort(arr, sizeof(arr[0]), size, compare_records_string);
        clock_t end = clock();
        time_sort = (double)(end-start)/CLOCKS_PER_SEC;

        fprintf(fp, "%lf;%zu;%s;%s\n", time_sort, size, "records_string", argv[1]);

        shuffle(arr, atoi(argv[2]), sizeof(struct Record));
        start = clock();
        binary_insert_sort(arr, sizeof(arr[0]), size, compare_records_int);
        end = clock();
        time_sort = (double)(end-start)/CLOCKS_PER_SEC;

        fprintf(fp, "%lf;%zu;%s;%s\n", time_sort, size, "records_int", argv[1]);

        shuffle(arr, atoi(argv[2]), sizeof(struct Record));
        start = clock();
        binary_insert_sort(arr, sizeof(arr[0]), size, compare_records_double);
        end = clock();
        time_sort = (double)(end-start)/CLOCKS_PER_SEC;

        fprintf(fp, "%lf;%zu;%s;%s\n", time_sort, size, "records_double", argv[1]);

        fflush(fp);
      } 
    }
    
    fclose(fp);
  } 
  else 
  {
    printf("Invalid input\n");
    exit(EXIT_FAILURE);
  }

  dispose_string_in_array(arr, atoi(argv[2]));
  free(arr);
  return (EXIT_SUCCESS);
}