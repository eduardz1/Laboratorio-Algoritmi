#include "headers/skip_list.h"

void insert_skip_list(struct _skip_list *list, void *elem)
{
  struct _node *new = create_node(elem, random_level());
  if(new->size > list->max_level) list->max_level = new->size;

  struct _node *x = list->head;
  for(int k = list->max_level; k>0; k--)
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