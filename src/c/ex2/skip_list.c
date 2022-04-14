#include "headers/skip_list.h"
#include <stdlib.h>

void insert_skip_list(struct SkipList *list, void *elem)
{

  // TODO: Da rivedere, non sono convinto che vogliamo mettere il caso base di primo
  // inserimento come logica nell'insert
  struct Node *new = create_node(elem, random_level(), list->type);
  if(new->size > list->max_level) 
    list->max_level = new->size;
  
    /* Allocando inizialmente MAX_HEIGHT puntatori NULL ad head non servono più malloc e realloc a gogo
    if(list->head != NULL) 
    {
      list->head = realloc(list->head, sizeof(struct Node *) * new->size);
      for (int i = new->size-1; i > list->max_level-1; i--)
        list->head[i] = NULL;
    }
    */

  struct Node **x = list->head;

  /*
  if(list->head == NULL) 
  {
    list->head = malloc(sizeof(struct Node *) * list->max_level);
    for (int i = 0; i < list->max_level; i++)
      list->head[i] = new;
  }
  */

  for(int k = list->max_level-1; k > 0;) // k = 0
  { 
    if((x[k]->next[k] == NULL) || (list->comp(elem, x[k]->next[k]->elem) < 0))
    {
      if(k < new->size)
      {
        new->next[k] = x[k]->next[k];
        x[k]->next[k] = new;
      }
      k--;
    }
    else
    {
      x[k] = x[k]->next[k];
    }
  }
}

struct SkipList *create_skip_list(int (*comp)(void*, void*), size_t type)
{
  struct SkipList *new = malloc(sizeof(struct SkipList));
  new->comp = comp;
  new->max_level = 1;
  BZERO(new->head, MAX_HEIGHT * (sizeof(struct _node *)));

  new->tail = NULL;
  new->type = type;
  return new;
}

// FIXME: Changing the head to an array of pointers makes the following implementation nonsense
void delete_skip_list(struct SkipList* list)
{
  return;
  struct Node *tmp;
  while(list->head[0] != NULL) 
  { 
    tmp = list->head[0]->next[0];

    for(int i = 0; i < list->head[0]->size; i++)
      free(list->head[0]->next[i]);

    free(list->head);
    list->head[0] = tmp;
  }
}

void *search_skip_list(struct SkipList *list, void *elem)
{
  struct Node **x = list->head;
  int i = list->max_level;

  /// @invariant x->elem < elem
  for(; i > 1; i--)
  {
    while(list->comp(x[i]->next[i]->elem, elem) < 0)
      x[i] = x[i]->next[i];
  }

  x[i] = x[i]->next[i];
  if(list->comp(x[i]->elem, elem) == 0)
    return x[i]->elem;
  else
    return NULL;
}

// TODO: spiegare il vatnaggio di questo algoritmo nella relazione (specificato
// nei requisiti che il numero di puntatori deve essere determinato da questo algoritmo)
uint32_t random_level() {
  int lvl = 1;
  while(rand() < 0.5 && lvl < MAX_HEIGHT)
    lvl++;
  return lvl;
}

struct Node *create_node(void *elem, uint32_t level, size_t size)
{
  struct Node *new = malloc(sizeof(struct Node));
  new->elem = malloc(size);
  
  for(int i = 0; i<size; i++)
    *(char*)(new->elem + i) = *(char*)(elem + i);
  
  new->next = NULL;
  new->size = level;

  return new;
}