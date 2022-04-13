#include "headers/skip_list.h"
#include <stdlib.h>

void insert_skip_list(struct _skip_list *list, void *elem)
{

  // TODO: Da rivedere, non sono convinto che vogliamo mettere il caso base di primo
  // inserimento come logica nell'insert
  struct _node *new = create_node(elem, random_level());
  if(new->size > list->max_level) {
    if (list->head != NULL) {
      realloc(list->head, sizeof(struct _node) * new->size);
      for (int i = new->size-1; i > list->max_level-1; i--)
        list->head[i] = NULL;
    }
    list->max_level = new->size;
  }

  struct _node *x = list->head;

  if (list->head == NULL) {
    list->head = malloc(sizeof(struct _node) * list->max_level);
    for (int i = 0; i < list->max_level; i++)
      list->head[i] = &new;
  }

  for(int k = list->max_level-1; k > 0; k--)
  { 
    if((x->next[k] == NULL) || (comp(elem, x->next[k]->elem) < 0))
    {
      if(k < new->size)
      {
        new->next[k] = x->next[k];
        x->next[k] = new;
      }
    }
    else
    {
      x = x->next[k];
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

void delete_skip_list(struct _skip_list* list)
{
  struct _node *tmp;
  while(list->head != NULL) 
  { 
    tmp = list->head[0]->next[0];

    for(int i = 0; i < list->head[0]->size; i++)
      free(list->head[0]->next[i]);

    free(list->head);
    list->head = tmp;
  }
}

void *search_skip_list(struct _skip_list *list, void *elem)
{
  struct _node *x = list->head;
  int i = list->max_level;

  /// @invariant x->elem < elem
  for(; i > 1; i--)
  {
    while(comp(x->next[i]->elem, elem) < 0)
      x = x->next[i];
  }

  x = x->next[i];
  if(comp(x->elem, elem) == 0)
    return x->elem;
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
  
  new->elem = elem;
  new->next = NULL;
  new->size = level;
}