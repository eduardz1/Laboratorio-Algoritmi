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
    char *word = malloc(30); // esofagodermatodigiunoplastica is the longest word in the italian dictionary :p
    BZERO(word, 30);
    sscanf(buffer, "%s", word);
    insert_skip_list(list, &word);
  }
  printf("\033[25m\0338\033[32mdone\033[0m\n");
  
  fclose(fp);
}

// this doesn't work, FIXME: read from correctme.txt words separated by spaces or
// newlines or punctuation
// TODO: convert words to lowercase and remove punctuation
void load_array(const char* file_name, char *arr[256])
{
  FILE *fp = fopen(file_name, "r");
  if(fp == NULL)
  {
    printf("Error opening file\n");
    exit(EXIT_FAILURE);
  }

  printf("Loading array from file \033[1m%s\033[22m \0337\033[5m...\n", file_name);
  char buffer[128];
  // fgets reads until newline, we want to read until whitespace or punctuation or newline or EOF
  for(int i = 0; fgets(buffer, sizeof(buffer), fp) != NULL && i < 256; i++)
  {
    arr[i] = malloc(30);
    sscanf(buffer, "%s", arr[i]);
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

  struct SkipList *list = create_skip_list(compare_string, free_string, sizeof(char *));
  char *words_to_correct[256];
  load_dictionary(argv[1], list);
  load_array(argv[2], words_to_correct);
  //print_skip_list(list, TYPE_STRING);
  //return 0;
  for(int i = 0; words_to_correct[i] != NULL && i < 256; i++)
  {
    if(search_skip_list(list, &words_to_correct[i]) == NULL)
      printf("\033[31mNot found:\033[0m %30s\n", words_to_correct[i]);
    //else
    //  printf("\033[32mFound:\033[0m %30s\n", words_to_correct[i]);
  }

  return (EXIT_SUCCESS);
}
