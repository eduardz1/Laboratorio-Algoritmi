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

void checksum(struct Record * a, int length) {
  bool flag = true;
  for(int i = 0; i < length - 1; i++)
  {
    if(compare_records(&a[i], &a[i+1]) > 0)
    {
      flag = false;
      break;
    }
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

  printf("Choose a sorting algorithm: [qsort]/[binssort] ");
  char input[10];
  (void)!scanf("%s", input); // scanf is not really safe, I'm casting the return into the void, we can use a better function
  
  struct Record *const arr = calloc(atoi(argv[2]), sizeof(struct Record));
  if(arr == NULL && atoi(argv[2]) > 0)
  {
    printf("Error allocating memory\n");
    exit(EXIT_FAILURE);
  }
  load_array(argv[1], arr, atoi(argv[2]));

  if(strcmp(input, "qsort") == 0) 
  {
    printf("\nChoose pivot strategy: \n0: [random]\n1: [first]\n2: [middle]\n3: [last]\n4: [median of three]\n");
    (void)!scanf("%s", input); // if input is different form 0/1/2/3/4 the number cycle anyway

    TIMING(quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) - 1, compare_records, atoi(input)));
    // TIMING(qsort(arr, atoi(argv[2]), sizeof(arr[0]), compare_records));
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

  checksum(arr, atoi(argv[2]));
  printf("\nSave sorted array to file \033[1msorted.csv\033[22m? [Y/n] ");
  (void)!scanf("%s", input);
  if(strcmp(input, "n") != 0)
  {
    FILE *fp = fopen("sorted.csv", "w");
    if(fp == NULL)
    {
      printf("Error opening file\n");
      exit(EXIT_FAILURE);
    }

    for (int i = 0; i < atoi(argv[2]); i++)
    {
      fprintf(fp, "%d,%s,%d,%lf\n", arr[i].id, arr[i].field1, arr[i].field2, arr[i].field3);
    }
    fclose(fp);
  }

  dispose_string_in_array(arr, atoi(argv[2]));
  free(arr);
  return (EXIT_SUCCESS);
}