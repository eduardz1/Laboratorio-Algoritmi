#include "headers/skip_list.h"
#include "../shared/common.h"
#include "../shared/record.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

void load_dictionary(const char* file_name, struct SkipList *list)
{
  FILE *fp = fopen(file_name, "r");
  if(fp == NULL)
  {
    printf("Error opening file\n");
    exit(EXIT_FAILURE);
  }

  printf("Loading dictionary from file \033[1m%s\033[22m \0337\033[5m...\n", file_name);
  char buffer[128];
  char buffer2[64] = {0};
  for (int i = 0; fgets(buffer, sizeof(buffer), fp) != NULL; i++)
  {
    sscanf(buffer, "%63[^ ]", buffer2 + i * 64);
    insert_skip_list(list, buffer2 + i * 64);
  }
  printf("\033[25m\0338\033[32mdone\033[0m\n");
  
  fclose(fp);
}

void load_array(const char* file_name, char *arr)
{
  FILE *fp = fopen(file_name, "r");
  if(fp == NULL)
  {
    printf("Error opening file\n");
    exit(EXIT_FAILURE);
  }

  printf("Loading array from file \033[1m%s\033[22m \0337\033[5m...\n", file_name);
  char buffer[64];
  for (int i = 0; fgets(buffer, sizeof(buffer), fp) != NULL; i++)
  {
    sscanf(buffer, "%63[^ ]", arr + i * 64);
  }
  printf("\033[25m\0338\033[32mdone\033[0m\n");
  
  fclose(fp);
}

int main(int argc, char const *argv[])
{
  if(argc < 3) 
  {
    printf("Usage: ordered_array_main <dictionary> <text>\n");
    exit(EXIT_FAILURE);
  }

  srand(time(NULL));

  struct SkipList *list = create_skip_list(compare_string, 64);
  char arr[64 * 256];
  load_dictionary(argv[1], list);
  load_array(argv[2], arr);
  
  for(int i = 0; i < 256; i++)
  {
    if(search_skip_list(list, arr + i * 64) == NULL)
      printf("\033[31mNot found:\033[0m %64s\n", arr + i * 64);
  }

  return (EXIT_SUCCESS);
}
