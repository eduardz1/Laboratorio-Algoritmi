#include "headers/skip_list.h"
#include <stdlib.h>

void insert_skip_list(struct _skip_list *list, void *elem)
{

  // TODO: Da rivedere, non sono convinto che vogliamo mettere il caso base di primo
  // inserimento come logica nell'insert
  struct _node *new = create_node(elem, random_level(), list->type);
  if(new->size > list->max_level) 
  {
    if(list->head != NULL) 
    {
      list->head = realloc(list->head, sizeof(struct _node *) * new->size);
      for (int i = new->size-1; i > list->max_level-1; i--)
        list->head[i] = NULL;
    }
    list->max_level = new->size;
  }

  struct _node **x = list->head;

  if(list->head == NULL) 
  {
    list->head = malloc(sizeof(struct _node *) * list->max_level);
    for (int i = 0; i < list->max_level; i++)
      list->head[i] = new;
  }

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

struct _skip_list *create_skip_list(int (*comp)(void*, void*), size_t type)
{
  struct _skip_list *new = malloc(sizeof(struct _skip_list));
  new->comp = comp;
  new->max_level = 1;
  new->head = NULL;
  new->tail = NULL;
  new->type = type;
  return new;
}

// FIXME: Changing the head to an array of pointers makes the following implementation nonsense
void delete_skip_list(struct _skip_list* list)
{
  return;
  struct _node *tmp;
  while(list->head != NULL) 
  { 
    tmp = list->head[0]->next[0];

    for(int i = 0; i < list->head[0]->size; i++)
      free(list->head[0]->next[i]);

    free(list->head);
    list->head = &tmp;
  }
}

void *search_skip_list(struct _skip_list *list, void *elem)
{
  struct _node **x = list->head;
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

struct _node *create_node(void *elem, uint32_t level, size_t size)
{
  struct _node *new = malloc(sizeof(struct _node));
  new->elem = malloc(size);
  
  for(int i = 0; i<size; i++)
    *(char*)(new->elem + i) = *(char*)(elem + i);
  
  new->next = NULL;
  new->size = level;

  return new;
}