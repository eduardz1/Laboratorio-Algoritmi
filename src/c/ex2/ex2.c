#include "headers/skip_list.h"
#include "../shared/common.h"
#include "../shared/record.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

// this works
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
  
  // the only way we have to save strings inside the node is to pass the pointer
  // to a malloc'd string, it would be nice if each node contained VARSIZE bytes
  // so that we can save the string inside the node, I've tried, didn't manage
  // to get it to work and that's why the skip_list.h is a bit different, if we decide
  // that node must only store the pointer to a string we can declare size in bytes
  // inside of list again instead of passing it to create_note()
  for (int i = 0; fgets(buffer, sizeof(buffer), fp) != NULL; i++)
  { 
    char *word = malloc(64); // have fun deallocating this
    BZERO(word, 64);
    sscanf(buffer, "%s", word);
    insert_skip_list(list, &word, sizeof(char*));
  }
  printf("\033[25m\0338\033[32mdone\033[0m\n");
  
  fclose(fp);
}

// this doesn't work, FIXME: read from correctme.txt words separated by spaces or
// newlines or punctuation
void load_array(const char* file_name, char *arr)
{
  FILE *fp = fopen(file_name, "r");
  if(fp == NULL)
  {
    printf("Error opening file\n");
    exit(EXIT_FAILURE);
  }

  printf("Loading array from file \033[1m%s\033[22m \0337\033[5m...\n", file_name);
  char buffer[128];
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

  struct SkipList *list = create_skip_list(compare_string);
  char arr[64 * 256] = {0}; // 256 words of 64 chars each
  load_dictionary(argv[1], list);
  load_array(argv[2], arr);
  
  print_skip_list(list, TYPE_STRING);
  return 0;
  for(int i = 0; i < 256; i++)
  {
    if(search_skip_list(list, arr + i * sizeof(char*)) == NULL)
      printf("\033[31mNot found:\033[0m %64s\n", arr + i * sizeof(char*));
  }

  return (EXIT_SUCCESS);
}
