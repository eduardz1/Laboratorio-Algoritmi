#include "headers/skip_list.h"
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
  return NULL;
  struct Node *x = list->head;
  int i = list->max_level;

  /// @invariant x->elem < elem
  for(; i > 1; i--)
  {
    while(list->comp(x->next[i]->elem, elem) < 0)
      x = x->next[i];
  }

  x = x->next[i];
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