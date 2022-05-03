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

void checksum(struct Record * a, int length, int (*comp)(const void *, const void *)) {
  bool flag = true;
  for(int i = 0; i < length - 1; i++)
  {
    if(comp(&a[i], &a[i+1]) > 0)
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
  if(strcmp(input, "qsort") != 0 && strcmp(input, "binssort") != 0)
  {
    printf("Invalid input\n");
    exit(EXIT_FAILURE);
  }

  struct Record *const arr = calloc(atoi(argv[2]), sizeof(struct Record));
  if(arr == NULL && atoi(argv[2]) > 0)
  {
    printf("Error allocating memory\n");
    exit(EXIT_FAILURE);
  }
  load_array(argv[1], arr, atoi(argv[2]));
  
  char input2[10];
  printf("\nChoose which field you wish to prioritize: \n0: [first]\n1: [second]\n2: [third]\n");
  (void)!scanf("%s", input2);

  Comp comp = atoi(input2) == 0 ? compare_records_string :
              atoi(input2) == 1 ? compare_records_int :
              atoi(input2) == 2 ? compare_records_double :
              NULL;

  if(comp == NULL)
  {
    printf("Invalid input\n");
    exit(EXIT_FAILURE);
  }

  if(strcmp(input, "qsort") == 0) 
  {
    printf("\nChoose pivot strategy: \n0: [random]\n1: [first]\n2: [middle]\n3: [last]\n4: [median of three]\n");
    (void)!scanf("%s", input); // if input is different form 0/1/2/3/4 the number cycle anyway

    TIMING(quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) - 1, comp, atoi(input)));
  } 
  else if(strcmp(input, "binssort") == 0) 
  {
    TIMING(quick_sort(arr, sizeof(arr[0]), 0, atoi(argv[2]) - 1, comp, atoi(input)));
  }

  checksum(arr, atoi(argv[2]), comp);
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