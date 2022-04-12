#include "headers/skip_list.h"

void insert_skip_list(struct _skip_list *list, void *elem)
{
  struct _node *new = create_node(elem, random_level());
  if(new->size > list->max_level) list->max_level = new->size;

  struct _node *x = list->head;
  for(int k = list->max_level; k > 1; k--)
  { 
    if(x->size < k) continue;

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

struct _skip_list *create_skip_list(int (*comp)(void*, void*))
{
  struct _skip_list *new = malloc(sizeof(struct _skip_list));
  new->comp = comp;
  new->max_level = 1;
  new->head = NULL;
  new->tail = NULL;
  return new;
}

void delete_skip_list(struct _skip_list* list)
{
  struct _node *tmp;
  while(list->head != NULL) 
  { 
    tmp = list->head->next[0];

    for(int i = 0; i < list->head->size; i++)
      free(list->head->next[i]);

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