#include "headers/skip_list.h"
#include "../shared/common.h"
#include "../shared/record.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#define MAX_WORDS 256
#define LONGEST_WORD 30 // esofagodermatodigiunoplastica is the longest word in the italian dictionary :p

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
  
  for (int i = 0; fgets(buffer, sizeof(buffer), fp) != NULL; i++)
  { 
    char *word = malloc(LONGEST_WORD * sizeof(char));
    if(word == NULL)
    {
      printf("Error allocating memory\n");
      exit(EXIT_FAILURE);
    }
    BZERO(word, LONGEST_WORD * sizeof(char));
    sscanf(buffer, "%s", word);
    insert_skip_list(list, &word);
  }
  printf("\033[25m\0338\033[32mdone\033[0m\n");
  
  fclose(fp);
}

int load_array(const char* file_name, char *arr[256])
{
  FILE *fp = fopen(file_name, "r");
  if(fp == NULL)
  {
    printf("Error opening file\n");
    exit(EXIT_FAILURE);
  }

  printf("Loading array from file \033[1m%s\033[22m \0337\033[5m...\n", file_name);

  bool ugly_flag = false;
  char c;
  int words = 0, chars = 0;

  arr[words] = malloc(LONGEST_WORD * sizeof(char)); // first word needs to be manually alloc'd
  if(arr[words] == NULL)
  {
    printf("Error allocating memory\n");
    exit(EXIT_FAILURE);
  }
  BZERO(arr[words], LONGEST_WORD * sizeof(char));
  while(((c = fgetc(fp)) != EOF) && words < MAX_WORDS)
  {
    if(c < 'A' || (c > 'Z' && c < 'a') || c > 'z')
    {
      if(!ugly_flag) continue; // makes fgetc loop until it finds a letter

      arr[words][chars] = '\0';
      chars = 0;
      arr[++words] = malloc(LONGEST_WORD * sizeof(char));
      if(arr[words] == NULL)
      {
        printf("Error allocating memory\n");
        exit(EXIT_FAILURE);
      }
      BZERO(arr[words], LONGEST_WORD * sizeof(char));
      ugly_flag = false;
    }
    else
    {
      if(c >= 'A' && c <= 'Z') c += 32; // lowercase
      arr[words][chars++] = c;
      ugly_flag = true;
    }
  }
  printf("\033[25m\0338\033[32mdone\033[0m\n");

  if(!ugly_flag) free(arr[words--]); // fixes case in which while loop thinks there's a word to be read but there's only random chars
  
  fclose(fp);
  return words;
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
  if(list == NULL)
  {
    printf("Error creating skip list\n");
    exit(EXIT_FAILURE);
  }

  char *words_to_correct[MAX_WORDS] = {0};
  load_dictionary(argv[1], list);
  int n_words = load_array(argv[2], words_to_correct);

  for(int i = 0; i <= n_words; i++)
  {
    if(search_skip_list(list, &words_to_correct[i]) == NULL)
      printf("\033[31mNot found:\033[0m %30s\n", words_to_correct[i]);
  }

  for(int i = 0; i <= n_words; i++) free(words_to_correct[i]);
  delete_skip_list(list);
  return (EXIT_SUCCESS);
}
