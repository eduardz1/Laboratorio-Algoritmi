#pragma once
#include "../../shared/common.h"

#define MAX_HEIGHT 1000 // max number of pointers possible in a _node

/**
 * @struct node of skip list
 * 
 * @param elem generic element of _node
 * @param next array of pointers to the next and a certain number of other elements in the list
 * @param size current size of array #next of pointers to _node
 */
struct _node
{
  void *elem;
  struct _node **next;
  uint32_t size;
};

/**
 * @brief probabilistic list of ordered elements
 * 
 * @param head pointer to the first element of the list
 * @param comp function comparable relative to the type of the elements
 * @param max_level current max value of _node::size in the list
 */
struct _skip_list
{
  struct _node **head;
  struct _node *tail;
  int (*comp)(void*, void*);
  uint32_t max_level;
  uint32_t size;
};

/**
 * @brief Create a node object
 * 
 * @param elem element of the node
 * @param level number of pointers to other nodes
 * @return pointer to the new node
 */
struct _node *create_node(void *elem, uint32_t level);

/**
 * @brief insert an element into the list
 * 
 * @param list pointer to a list of generic elements
 * @param elem element to insert
 */
void insert_skip_list(struct _skip_list *list, void *elem);

/**
 * @brief determines max number of pointer to include in a new _node
 * 
 */
uint32_t random_level();

/**
 * @brief verifies if an element is present in the list
 * 
 * @param list list of generic elements
 * @param elem elements to search
 * @return the element if found or NULL otherwise
 */
void *search_skip_list(struct _skip_list *list, void *elem);

/**
 * @brief initializes a new empty skip list
 *
 * @param comp pointer to the compare function desired for a type
 */
struct _skip_list *create_skip_list(int (*comp)(void*, void*), uint32_t size);

/**
 * @brief deallocates every element of a list
 *
 */
void delete_skip_list(struct _skip_list* list); // maybe serve anche passargli una size