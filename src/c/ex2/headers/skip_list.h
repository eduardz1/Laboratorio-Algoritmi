#pragma once
#include "../../shared/common.h"

#define MAX_HEIGHT 19 // ~log_2(NUM_WORDS=661561)

/**
 * @struct node of skip list
 * 
 * @param elem generic element of Node
 * @param next array of pointers to the next and a certain number of other elements in the list
 * @param level current level of array #next of pointers to Node
 * @param size size_t in bytes of #elem
 */
struct Node
{
  void *elem;
  struct Node **next;
  uint8_t level;
  size_t size;
};

/**
 * @brief probabilistic list of ordered elements
 * 
 * @param head pointer to the first element of the list
 * @param comp function comparable relative to the type of the elements
 * @param max_level current max value of Node::level in the list
 * @param elem_size size_t in bytes of #elem
 * @param free function used to free #elem
 */
struct SkipList
{
  struct Node *head;
  int (*comp)(void*, void*);
  uint8_t max_level;
  size_t elem_size;
  void (*free)(void *);
};

/**
 * @brief Create a node object
 * 
 * @param elem element of the node
 * @param level number of pointers to other nodes
 * @param size specifies size of byte to allocate for the elem
 * @return pointer to the new node or NULL if an error occurred
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
 * @param free function used to free #elem
 * @param elem_size specifies the type by size
 * @return pointer to the new list or NULL if an error occurred
 */
struct SkipList *create_skip_list(int (*comp)(void*, void*), void (*free)(void *), size_t elem_size);

/**
 * @brief deallocates every element of a list
 *
 */
void delete_skip_list(struct SkipList* list);

/**
 * @brief prints skip list formatted vertically
 *
 *  output when redirected needs to be converted with "col -bxp <inputfile.txt >outputfile.txt"
 *  to render carriage returns properly
 */
void print_skip_list(struct SkipList *list, enum Type type);