#include "headers/skip_list.h"
#include "../shared/record.h"
#include <stdlib.h>

// TODO: metodo di stampa sensato

void insert_skip_list(struct SkipList *list, void *elem, size_t size)
{

  // TODO: Da rivedere, non sono convinto che vogliamo mettere il caso base di primo
  // inserimento come logica nell'insert
  struct Node *new = create_node(elem, random_level(), size);
  if(new->level > list->max_level) 
    list->max_level = new->level;
  
  struct Node *x = list->head;
  
  for(int k = list->max_level; k >= 0;)
  {
    if((x->next[k] == NULL) || (list->comp(elem, x->next[k]->elem) < 0))
    {
      if(k < new->level)
      {
        new->next[k] = x->next[k];
        x->next[k] = new;
      }
      k--;
    }
    else
    {
      x = x->next[k];
    }
  }
}

struct SkipList *create_skip_list(int (*comp)(void*, void*))
{
  struct SkipList *new = malloc(sizeof(struct SkipList));

  // following implementation does not convince me in the slightest, we can surely 
  // initialize it with create_note somehow, the problems are all the various
  // behaviours of the system function with NULL pointers as elements
  new->head = malloc(sizeof(struct Node)); // sentinel
  new->head->next = malloc(sizeof(struct Node *)*MAX_HEIGHT);
  BZERO(new->head->next, MAX_HEIGHT);
  new->head->level = MAX_HEIGHT;
  new->head->elem = NULL;

  new->comp = comp;
  new->max_level = 1;
  return new;
}

// FIXME: Probably does not work, haven't tested it in any way shape or form, quite possibly nonsense
void delete_skip_list(struct SkipList* list)
{
  return;
  struct Node *tmp;
  while(list->head->next[0] != NULL) 
  { 
    tmp = list->head->next[0];

    for(int i = 0; i < list->head->level; i++)
      free(list->head->next[i]);

    free(list->head);
    list->head = tmp;
  }
}

void *search_skip_list(struct SkipList *list, void *elem)
{
  struct Node *x = list->head;

  /// @invariant x->elem < elem
  for(int i = list->max_level - 1; i >= 0; i--)
  {
    while(x->next[i] != NULL && list->comp(x->next[i]->elem, elem) < 0)
      x = x->next[i];
  }

  x = x->next[0];
  if(x == NULL || list->comp(x->elem, elem) != 0)
    return NULL;
  else
    return x->elem;
}

// TODO: spiegare il vatnaggio di questo algoritmo nella relazione (specificato
// nei requisiti che il numero di puntatori deve essere determinato da questo algoritmo)
uint32_t random_level() {
  int lvl = 1;
  while(rand() % 2 && lvl < MAX_HEIGHT)
    lvl++;
  return lvl;
}

struct Node *create_node(void *elem, uint32_t level, size_t size)
{
  struct Node *new = malloc(sizeof(struct Node));
  new->elem = malloc(size);
  memcpy(new->elem, elem, size);
  new->level = level;
  new->next = malloc(sizeof(void*) * level);
  BZERO(new->next, sizeof(void*) * level);
  new->size = size;
  return new;
}

// Redirection doesn't look too good because of the carriage returns, if we find
// a way to write the elem at the start of the line without them it will look good
// even when redirected, for now we can convert it with "col -bxp <inputfile.txt >outputfile.txt" (it takes a while)
void print_skip_list(struct SkipList *list, enum Type type)
{
  printf("\n");
  struct Node *x = list->head;
  printf("\n");
  printf("-- HEAD (Sentinel) --\n\n");
  for(int i = 0; i < list->max_level; i++)
    printf("[LEVEL %03d] ", i);
  printf("\n");

  x = list->head;
  do
  {
    x = x->next[0];
    for(int i = 0; i < list->max_level; i++)
      printf("     |      ");
    printf("\n");

    printf(" ");
    for(int i = 0; i < x->level; i++)
      printf("----V-------");
    for(int i = x->level; i < list->max_level; i++)
      printf("    |       ");
    printf("\n");
    for(int i = 0; i < x->level; i++)
      printf("            ");
    printf(" |");
    for(int i = x->level; i < list->max_level; i++)
      printf("   |        ");
    switch(type)
    {
    case TYPE_CHAR:
      printf("\r| %c", *(char*)x->elem);
      break;
    case TYPE_INT:
      printf("\r| %d", *(int*)x->elem);
      break;
    case TYPE_FLOAT:
      printf("\r| %f", *(float*)x->elem);
      break;
    case TYPE_DOUBLE: 
      printf("\r| %lf", *(double*)x->elem);
      break;
    case TYPE_STRING:
      printf("\r| %s", *(char**)x->elem);
      break;
    case TYPE_RECORD:
      printf("\r| <%d/%s/%d/%lf>", ((struct Record *)x->elem)->id, ((struct Record *)x->elem)->field1, ((struct Record *)x->elem)->field2, ((struct Record *)x->elem)->field3);
      break;
    case TYPE_POINTER: default:
      printf("\r| %p", x->elem);
      break;
    }

    printf("\n");
    printf(" ");
    for(int i = 0; i < x->level; i++)
      printf("------------");
    for(int i = x->level; i < list->max_level; i++)
      printf("    |       ");
    printf("\n");
       
  } while(x->next[0] != NULL);

  for(int i = 0; i < list->max_level; i++)
    printf("     |      ");
  printf("\n");
  printf(" ");
  for(int i = 0; i < list->max_level; i++)
    printf("----V-------");
  printf("\n");
  for(int i = 0; i < list->max_level; i++)
    printf("            ");
  printf(" |");
  printf("\r| NIL\n");
  printf(" ");
  for(int i = 0; i < list->max_level; i++)
    printf("------------");
  printf("\n");
  printf("\n");
}