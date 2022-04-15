#include "headers/skip_list.h"
#include "../shared/record.h"
#include <stdlib.h>

// TODO: metodo di stampa sensato

void insert_skip_list(struct SkipList *list, void *elem)
{

  // TODO: Da rivedere, non sono convinto che vogliamo mettere il caso base di primo
  // inserimento come logica nell'insert
  struct Node *new = create_node(elem, random_level(), list->type);
  if(new->size > list->max_level) 
    list->max_level = new->size;
  
  struct Node *x = list->head;
  
  for(int k = list->max_level; k >= 0;)
  {
    if((x->next[k] == NULL) || (list->comp(elem, x->next[k]->elem) < 0))
    {
      if(k < new->size)
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

struct SkipList *create_skip_list(int (*comp)(void*, void*), size_t type)
{
  struct SkipList *new = malloc(sizeof(struct SkipList));

  // following implementation does not convince me in the slightest, we can surely 
  // initialize it with create_note somehow, the problems are all the various
  // behaviours of the system function with NULL pointers as elements
  new->head = malloc(sizeof(struct Node)); // sentinel
  new->head->next = malloc(sizeof(struct Node *)*MAX_HEIGHT);
  BZERO(new->head->next, MAX_HEIGHT);
  new->head->size = MAX_HEIGHT;
  new->head->elem = NULL;

  new->comp = comp;
  new->max_level = 1;
  new->type = type;
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

    for(int i = 0; i < list->head->size; i++)
      free(list->head->next[i]);

    free(list->head);
    list->head = tmp;
  }
}

void *search_skip_list(struct SkipList *list, void *elem)
{
  struct Node *x = list->head;

  /// @invariant x->elem < elem
  for(int i = list->max_level - 1; i > 0; i--)
  {
    int rimani = 1;
    while(x->next[i] != NULL && list->comp(x->next[i]->elem, elem) < 0)
      x = x->next[i];
  }

  x = x->next[0];
  if(list->comp(x->elem, elem) == 0)
    return x->elem;
  else
    return NULL;
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
  new->size = level;
  new->next = malloc(sizeof(void*) * level);
  return new;
}

// This function is O(bscene) but it works and it's only a print sooooooo
void print_skip_list(struct SkipList *list, enum Type type)
{
  printf("\n");
  // prints list of levels in line
  struct Node *x = list->head;
  for(int i = 0; i <= list->max_level; i++)
  {
    printf("[LEVEL %03d] ", i);
  }
  printf("\n\0337");

  // fills every line with a link
  while(x != NULL)
  {
    
    for(int i = 0; i <= 3; i++)
    {
      for(int i = 0; i<= list->max_level; i++)
      {
        printf("     |      ");
      }
      printf("\n");
    } 
    x = x->next[0];
  }

  printf("\0338");
  x = list->head;
  do
  {
    x = x->next[0];
    printf("\n");

    printf("\033[1C");
    for(int i = 0; i<= x->size; i++)
      printf("------------");
    printf("\n");

    printf("\0337");
    for(int i = 0; i<= x->size; i++)
      printf("            ");
    printf(" |");

    switch(type)
    {
    case TYPE_CHAR:
      printf("\0338| %c", *(char*)x->elem);
      break;
    case TYPE_INT:
      printf("\0338| %d", *(int*)x->elem);
      break;
    case TYPE_FLOAT:
      printf("\0338| %f", *(float*)x->elem);
      break;
    case TYPE_DOUBLE: 
      printf("\0338| %lf", *(double*)x->elem);
      break;
    case TYPE_STRING:
      printf("\0338| %s", (char*)x->elem);
      break;
    case TYPE_RECORD:
      printf("\0338| <%d/%s/%d/%lf>", ((struct Record *)x->elem)->id, ((struct Record *)x->elem)->field1, ((struct Record *)x->elem)->field2, ((struct Record *)x->elem)->field3);
      break;
    case TYPE_POINTER: default:
      printf("\0338| %p", x->elem);
      break;
    }

    printf("\n");
    printf("\033[1C");
    for(int i = 0; i<= x->size; i++)
      printf("------------");
    printf("\n");
       
  }while(x->next[0] != NULL);

  printf("\n");
  printf("\033[1C");
  for(int i = 0; i <= list->max_level; i++)
    printf("------------");
  printf("\n");
  for(int i = 0; i <= list->max_level; i++)
    printf("            ");
  printf(" |");
  printf("\033[0G| NIL\n");
  printf("\033[1C");
  for(int i = 0; i <= list->max_level; i++)
    printf("------------");
  printf("\n");
  printf("\n");

  printf("\033[0m");
}