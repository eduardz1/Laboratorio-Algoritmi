#include "unity/unity.h"
#include "../ex2/headers/skip_list.h"
#include "../shared/record.h"

// TODO: Implement tests for create_skip_list() and create_node(), otherwise it's 
// pointless to test insert_skip_list(), right now the tests cause segfaults, 
// could be caused by the for loop not working anymore now that the head is an 
// array of pointers or by the create_skip_list()

#pragma region /// TEST #insert_skip_list()
void test_insert_char_skip_list()
{
  struct _skip_list *l = create_skip_list(compare_char, sizeof(char));
  char actual[6] = { 'l', 'f', 'a', 'b', '0', 'w'};

  insert_skip_list(l, actual + 0);
  insert_skip_list(l, actual + 1);
  insert_skip_list(l, actual + 2);
  insert_skip_list(l, actual + 3);
  insert_skip_list(l, actual + 4);
  insert_skip_list(l, actual + 5);

  char expected[6] = { 'a', 'b', 'f', 'l', 'o', 'w'};

  struct _skip_list *tmp = l;
  for(int i = 0; tmp->head[0] != NULL; tmp->head[0] = tmp->head[0]->next[0])
    actual[i] = *(char*)l->head[0]->elem;
    
  TEST_ASSERT_EQUAL_INT8_ARRAY(expected, actual, 6);
  delete_skip_list(l);
}

void test_insert_int_skip_list()
{
  struct _skip_list *l = create_skip_list(compare_int, sizeof(int));
  int actual[6] = { 3, 7, 1, 11, 89, 0};

  insert_skip_list(l, actual + 0);
  insert_skip_list(l, actual + 1);
  insert_skip_list(l, actual + 2);
  insert_skip_list(l, actual + 3);
  insert_skip_list(l, actual + 4);
  insert_skip_list(l, actual + 5); 

  int expected[6] = {0, 1, 3, 7, 11, 89};

  struct _skip_list *tmp = l;
  for(int i = 0; tmp->head[0] != NULL; tmp->head[0] = tmp->head[0]->next[0])
    actual[i] = *(int*)l->head[0]->elem;

  TEST_ASSERT_EQUAL_INT_ARRAY(expected, actual, 6);
  delete_skip_list(l);
}

void test_insert_double_skip_list()
{
  struct _skip_list *l = create_skip_list(compare_double, sizeof(double));
  double actual[6] = { 0.900000003, 34343.8989328, 0.0, 45.9999, 7.0, 78.89};

  insert_skip_list(l, actual + 0);
  insert_skip_list(l, actual + 1);
  insert_skip_list(l, actual + 2);
  insert_skip_list(l, actual + 3);
  insert_skip_list(l, actual + 4);
  insert_skip_list(l, actual + 5);

  double expected[6];

  struct _skip_list *tmp = l;
  for(int i = 0; tmp->head[0] != NULL; tmp->head[0] = tmp->head[0]->next[0])
    actual[i] = *(double*)l->head[0]->elem;

  TEST_ASSERT_EQUAL_DOUBLE_ARRAY(expected, actual, 6);
  delete_skip_list(l);
}

void test_insert_long_skip_list()
{
  struct _skip_list *l = create_skip_list(compare_long, sizeof(long));
  long actual[6] = { INT_MAX + 1l, INT_MIN - 1l, 0l, 99l, INT_MIN * 2l, LONG_MAX};

  insert_skip_list(l, actual + 0);
  insert_skip_list(l, actual + 1);
  insert_skip_list(l, actual + 2);
  insert_skip_list(l, actual + 3);
  insert_skip_list(l, actual + 4);
  insert_skip_list(l, actual + 5);

  long expected[6] = {INT_MIN * 2l, INT_MIN - 1l, 0l, 99l, INT_MAX + 1l, LONG_MAX};

  struct _skip_list *tmp = l;
  for(int i = 0; tmp->head[0] != NULL; tmp->head[0] = tmp->head[0]->next[0])
    actual[i] = *(long*)l->head[0]->elem;

  TEST_ASSERT_EQUAL_INT64_ARRAY(expected, actual, 6);
  delete_skip_list(l);
}

void test_insert_float_skip_list()
{
  struct _skip_list *l = create_skip_list(compare_char, sizeof(float));
  float actual[6] = { 4.0f, 4.999f, 1.0f, 0.0f, 59595.1f, -1.8f};

  insert_skip_list(l, actual + 0);
  insert_skip_list(l, actual + 1);
  insert_skip_list(l, actual + 2);
  insert_skip_list(l, actual + 3);
  insert_skip_list(l, actual + 4);
  insert_skip_list(l, actual + 5);

  float expected[6] = { -1.8f, 0.0f, 1.0f, 4.0f, 4.999f, 59595.1f};

  struct _skip_list *tmp = l;
  for(int i = 0; tmp->head[0] != NULL; tmp->head[0] = tmp->head[0]->next[0])
    actual[i] = *(float*)l->head[0]->elem;

  TEST_ASSERT_EQUAL_FLOAT_ARRAY(expected, actual, 6);
  delete_skip_list(l);
}

void test_insert_string_skip_list()
{
  struct _skip_list *l = create_skip_list(compare_char, (sizeof(char*)));
  char *actual[6] = {"aaaa", "sdsad", "bbb.,", ",", "away", "4"};

  insert_skip_list(l, actual + 0);
  insert_skip_list(l, actual + 1);
  insert_skip_list(l, actual + 2);
  insert_skip_list(l, actual + 3);
  insert_skip_list(l, actual + 4);
  insert_skip_list(l, actual + 5);

  char *expected[6] = {",", "4", "aaaa", "away", "bbb.,", "sdsad"};

  struct _skip_list *tmp = l;
  for(int i = 0; tmp->head[0] != NULL; tmp->head[0] = tmp->head[0]->next[0])
    actual[i] = *(char**)l->head[0]->elem;

  TEST_ASSERT_EQUAL_STRING_ARRAY(expected, actual, 6);
  delete_skip_list(l);
}

void test_insert_record_skip_list()
{
  struct _skip_list *l = create_skip_list(compare_char, sizeof(struct _record));
  struct _record actual[8] = {
      {0, "a\0", 1, 0.0001f},
      {1, "c\0", 0, 1.0001f},
      {2, "a\0", 0, 0.0211f},
      {3, "b\0", 24, 0.0001f},
      {4, "b\0", 15, 0.0001f},
      {5, "c\0", 0, 0.0001f},
      {6, "d\0", 0, 0.0001f},
      {7, "a\0", 0, 0.0001f}
    };
  
  insert_skip_list(l, actual + 0);
  insert_skip_list(l, actual + 1);
  insert_skip_list(l, actual + 2);
  insert_skip_list(l, actual + 3);
  insert_skip_list(l, actual + 4);
  insert_skip_list(l, actual + 5);

  struct _record expected[8] = {
      {7, "a\0", 0, 0.0001f},
      {2, "a\0", 0, 0.0211f},
      {0, "a\0", 1, 0.0001f},
      {4, "b\0", 15, 0.0001f},
      {3, "b\0", 24, 0.0001f},
      {5, "c\0", 0, 0.0001f},
      {1, "c\0", 0, 1.0001f},
      {6, "d\0", 0, 0.0001f},
  };

  struct _skip_list *tmp = l;
  for(int i = 0; tmp->head[0] != NULL; tmp->head[0] = tmp->head[0]->next[0])
    actual[i] = *(struct _record*)l->head[0]->elem;

  for (int i = 0; i < sizeof(actual) / sizeof(actual[0]); i++)
  {
    TEST_ASSERT_EQUAL_STRING(expected[i].field1, actual[i].field1);
    TEST_ASSERT_EQUAL_INT(expected[i].field2, actual[i].field2);
    TEST_ASSERT_EQUAL_FLOAT(expected[i].field3, actual[i].field3);
  }
  delete_skip_list(l);
}
#pragma endregion

int main(int argc, char const *argv[])
{
  UNITY_BEGIN();

  RUN_TEST(test_insert_char_skip_list);
  RUN_TEST(test_insert_double_skip_list);
  RUN_TEST(test_insert_float_skip_list);
  RUN_TEST(test_insert_int_skip_list);
  RUN_TEST(test_insert_long_skip_list);
  RUN_TEST(test_insert_record_skip_list);
  RUN_TEST(test_insert_string_skip_list);

  return UNITY_END();
}
