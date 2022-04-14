#pragma once
#include "../../shared/common.h"

#define MAX_HEIGHT 1000 // max number of pointers possible in a _node

/**
 * @struct node of skip list
 * 
 * @param elem generic element of Node
 * @param next array of pointers to the next and a certain number of other elements in the list
 * @param size current size of array #next of pointers to Node
 */
struct Node
{
  void *elem;
  struct Node **next;
  uint32_t size;
};

/**
 * @brief probabilistic list of ordered elements
 * 
 * @param head pointer to the first element of the list
 * @param comp function comparable relative to the type of the elements
 * @param max_level current max value of Node::size in the list
 * @param type size_t in bytes of each element of he list
 */
struct SkipList
{
  struct Node **head;
  struct Node *tail;
  int (*comp)(void*, void*);
  uint32_t max_level;
  size_t type; // idk all th elements are void* anyway, I don't really know if strings are deallocated correctly though, all the other types should be fine
};

/**
 * @brief Create a node object
 * 
 * @param elem element of the node
 * @param level number of pointers to other nodes
 * @param size specifies size of byte to allocate for the elem
 * @return pointer to the new node
 */
struct Node *create_node(void *elem, uint32_t level, size_t size);

/**
 * @brief insert an element into the list
 * 
 * @param list pointer to a list of generic elements
 * @param elem element to insert
 */
void insert_skip_list(struct SkipList *list, void *elem);

/**
 * @brief determines max number of pointer to include in a new Node
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
void *search_skip_list(struct SkipList *list, void *elem);

/**
 * @brief initializes a new empty skip list
 *
 * @param comp pointer to the compare function desired for a type
 * @param type specifies the type by size
 */
struct SkipList *create_skip_list(int (*comp)(void*, void*), size_t type);

/**
 * @brief deallocates every element of a list
 *
 */
void delete_skip_list(struct SkipList* list); // maybe serve anche passargli una size