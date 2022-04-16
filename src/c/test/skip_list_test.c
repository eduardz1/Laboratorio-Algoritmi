#include "unity/unity.h"
#include "../ex2/headers/skip_list.h"
#include "../shared/record.h"

#define UNITY_OUTPUT_COLOR

// TODO: Implement tests for search_skip_list()

void setUp(void)
{
  srand(time(NULL));
}

void test_search_int_skip_list() {
  int aa = 1;
  int bb = 3;
  int cc = 5;
  int dd = 7;

  struct Node * a = malloc(sizeof(struct Node));
  a->elem= &aa;
  a->next=malloc(sizeof(struct Node) * 3);
  a->level = 3;
  a->size = sizeof(int);

  struct Node * b = malloc(sizeof(struct Node));
  b->elem= &bb;
  b->next=malloc(sizeof(struct Node));
  b->level = 1;
  b->size = sizeof(int);

  struct Node * c = malloc(sizeof(struct Node));
  c->elem= &cc;
  c->next=malloc(sizeof(struct Node) * 2);
  c->level = 2;
  c->size = sizeof(int);

  struct Node * d = malloc(sizeof(struct Node));
  d->elem= &dd;
  d->next=malloc(sizeof(struct Node) * 3);
  d->level = 3;
  d->size = sizeof(int);

  a->next[2] = d;
  a->next[1] = c;
  a->next[0] = b;
  
  b->next[0] = c;
  
  c->next[1] = d;
  c->next[0] = d;

  d->next[2] = NULL;
  d->next[1] = NULL;
  d->next[0] = NULL;

  struct Node * dummy = malloc(sizeof(struct Node));
  dummy->next = malloc(sizeof(struct Node *) * 3);
  dummy->next[0] = a;
  dummy->next[1] = a;
  dummy->next[2] = a;
  dummy->elem = NULL;
  dummy->level = 3;

  struct SkipList * list = malloc(sizeof(struct SkipList));
  list->comp = compare_int;
  list->max_level = 3;
  list->head = dummy;

  int blabla = 1;
  TEST_ASSERT_EQUAL_INT(0, compare_int(&blabla, search_skip_list(list, &blabla)));
}

void test_search_char_skip_list()
{
  struct SkipList *l = create_skip_list(compare_char);
  char actual[6] = { 'l', 'f', 'a', 'b', '0', 'w'};

  insert_skip_list(l, actual + 0, sizeof(char));
  insert_skip_list(l, actual + 1, sizeof(char));
  insert_skip_list(l, actual + 2, sizeof(char));
  insert_skip_list(l, actual + 3, sizeof(char));
  insert_skip_list(l, actual + 4, sizeof(char));
  insert_skip_list(l, actual + 5, sizeof(char));

  char to_search[6] = { 'l', 'z', 'a', 'k', '0', 'x'};

  print_skip_list(l, TYPE_CHAR);
  TEST_ASSERT_EQUAL_INT(0, compare_char(search_skip_list(l, to_search + 0), actual + 0));
  TEST_ASSERT_NULL(search_skip_list(l, to_search + 1));
  TEST_ASSERT_EQUAL_INT(0, compare_char(search_skip_list(l, to_search + 2), actual + 2));
  TEST_ASSERT_NULL(search_skip_list(l, to_search + 3));
  TEST_ASSERT_EQUAL_INT(0, compare_char(search_skip_list(l, to_search + 4), actual + 4));
  TEST_ASSERT_NULL(search_skip_list(l, to_search + 5));
  delete_skip_list(l);
}

void test_search_skip_list()
{
  struct SkipList *l = create_skip_list(compare_records);
  struct Record actual[8] = {
      {0, "a\0", 1, 0.0001f},
      {1, "c\0", 0, 1.0001f},
      {2, "a\0", 0, 0.0211f},
      {3, "b\0", 24, 0.0001f},
      {4, "b\0", 15, 0.0001f},
      {5, "c\0", 0, 0.0001f},
      {6, "d\0", 0, 0.0001f},
      {7, "a\0", 0, 0.0001f}
    };
  
  insert_skip_list(l, actual + 0, sizeof(struct Record));
  insert_skip_list(l, actual + 1, sizeof(struct Record));
  insert_skip_list(l, actual + 2, sizeof(struct Record));
  insert_skip_list(l, actual + 3, sizeof(struct Record));
  insert_skip_list(l, actual + 4, sizeof(struct Record));
  insert_skip_list(l, actual + 5, sizeof(struct Record));
  insert_skip_list(l, actual + 6, sizeof(struct Record));
  insert_skip_list(l, actual + 7, sizeof(struct Record));
  
  struct Record to_search[8] = {
      {0, "a\0", 1, 0.0001f},
      {1, "c\0", 90, 1.0001f},
      {2, "a\0", 0, 0.0211f},
      {3, "b\0", 24, 0.0901f},
      {4, "b\0", 15, 0.0001f},
      {5, "i\0", 0, 0.0001f},
      {6, "d\0", 0, 0.0001f},
      {7, "a\0", 0, -0.0001f}
    };

  TEST_ASSERT_EQUAL_INT(0, compare_records(search_skip_list(l, to_search + 0), actual + 0));
  TEST_ASSERT_NULL(search_skip_list(l, to_search + 1));
  TEST_ASSERT_EQUAL_INT(0, compare_records(search_skip_list(l, to_search + 2), actual + 2));
  TEST_ASSERT_NULL(search_skip_list(l, to_search + 3));
  TEST_ASSERT_EQUAL_INT(0, compare_records(search_skip_list(l, to_search + 4), actual + 4));
  TEST_ASSERT_NULL(search_skip_list(l, to_search + 5));
  TEST_ASSERT_EQUAL_INT(0, compare_records(search_skip_list(l, to_search + 6), actual + 6));
  TEST_ASSERT_NULL(search_skip_list(l, to_search + 7));
  delete_skip_list(l);
}

#pragma region /// TEST #insert_skip_list()
void test_insert_char_skip_list()
{
  struct SkipList *l = create_skip_list(compare_char);
  char actual[6] = { 'l', 'f', 'a', 'b', '0', 'w'};

  insert_skip_list(l, actual + 0, sizeof(char));
  insert_skip_list(l, actual + 1, sizeof(char));
  insert_skip_list(l, actual + 2, sizeof(char));
  insert_skip_list(l, actual + 3, sizeof(char));
  insert_skip_list(l, actual + 4, sizeof(char));
  insert_skip_list(l, actual + 5, sizeof(char));

  char expected[6] = { '0', 'a', 'b', 'f', 'l', 'w'};

  struct Node *tmp = l->head->next[0];
  for(int i = 0; tmp != NULL; tmp = tmp->next[0])
  {
    actual[i] = *(char*)tmp->elem;
    i++;
  }

  TEST_ASSERT_EQUAL_INT8_ARRAY(expected, actual, 6);
  delete_skip_list(l);
}

void test_insert_int_skip_list()
{
  struct SkipList *l = create_skip_list(compare_int);
  int actual[6] = { 3, 7, 1, 11, 89, 0};

  insert_skip_list(l, actual + 0, sizeof(int));
  insert_skip_list(l, actual + 1, sizeof(int));
  insert_skip_list(l, actual + 2, sizeof(int));
  insert_skip_list(l, actual + 3, sizeof(int));
  insert_skip_list(l, actual + 4, sizeof(int));
  insert_skip_list(l, actual + 5, sizeof(int)); 

  int expected[6] = {0, 1, 3, 7, 11, 89};

  struct Node *tmp = l->head->next[0];
  for(int i = 0; tmp != NULL; tmp = tmp->next[0])
  {
    actual[i] = *(int*)tmp->elem;
    i++;
  }

  TEST_ASSERT_EQUAL_INT_ARRAY(expected, actual, 6);
  delete_skip_list(l);
}

void test_insert_double_skip_list()
{
  struct SkipList *l = create_skip_list(compare_double);
  double actual[6] = { 0.900000003, 34343.8989328, 0.0, 45.9999, 7.0, 78.89};

  insert_skip_list(l, actual + 0, sizeof(double));
  insert_skip_list(l, actual + 1, sizeof(double));
  insert_skip_list(l, actual + 2, sizeof(double));
  insert_skip_list(l, actual + 3, sizeof(double));
  insert_skip_list(l, actual + 4, sizeof(double));
  insert_skip_list(l, actual + 5, sizeof(double));

  double expected[6] = {0.0, 0.900000003, 7.0, 45.9999, 78.89, 34343.8989328};

  struct Node *tmp = l->head->next[0];
  for(int i = 0; tmp != NULL; tmp = tmp->next[0])
  {
    actual[i] = *(double*)tmp->elem;
    i++;
  }

  TEST_ASSERT_EQUAL_DOUBLE_ARRAY(expected, actual, 6);
  delete_skip_list(l);
}

void test_insert_long_skip_list()
{
  struct SkipList *l = create_skip_list(compare_long);
  long actual[6] = { INT_MAX + 1l, INT_MIN - 1l, 0l, 99l, INT_MIN * 2l, LONG_MAX};

  insert_skip_list(l, actual + 0, sizeof(long));
  insert_skip_list(l, actual + 1, sizeof(long));
  insert_skip_list(l, actual + 2, sizeof(long));
  insert_skip_list(l, actual + 3, sizeof(long));
  insert_skip_list(l, actual + 4, sizeof(long));
  insert_skip_list(l, actual + 5, sizeof(long));

  long expected[6] = {INT_MIN * 2l, INT_MIN - 1l, 0l, 99l, INT_MAX + 1l, LONG_MAX};

  struct Node *tmp = l->head->next[0];
  for(int i = 0; tmp != NULL; tmp = tmp->next[0])
  {
    actual[i] = *(long*)tmp->elem;
    i++;
  }

  TEST_ASSERT_EQUAL_INT64_ARRAY(expected, actual, 6);
  delete_skip_list(l);
}

void test_insert_float_skip_list()
{
  struct SkipList *l = create_skip_list(compare_float);
  float actual[6] = { 4.0f, 4.999f, 1.0f, 0.0f, 59595.1f, -1.8f};

  insert_skip_list(l, actual + 0, sizeof(float));
  insert_skip_list(l, actual + 1, sizeof(float));
  insert_skip_list(l, actual + 2, sizeof(float));
  insert_skip_list(l, actual + 3, sizeof(float));
  insert_skip_list(l, actual + 4, sizeof(float));
  insert_skip_list(l, actual + 5, sizeof(float));

  float expected[6] = { -1.8f, 0.0f, 1.0f, 4.0f, 4.999f, 59595.1f};

  struct Node *tmp = l->head->next[0];
  for(int i = 0; tmp != NULL; tmp = tmp->next[0])
  {
    actual[i] = *(float*)tmp->elem;
    i++;
  }

  TEST_ASSERT_EQUAL_FLOAT_ARRAY(expected, actual, 6);
  delete_skip_list(l);
}

void test_insert_string_skip_list()
{
  struct SkipList *l = create_skip_list(compare_string);
  char *actual[6] = {"aaaa", "sdsadaaaaaa", "bbb.,", ",", "away", "4"};

  insert_skip_list(l, actual + 0, sizeof(char*));
  insert_skip_list(l, actual + 1, sizeof(char*));
  insert_skip_list(l, actual + 2, sizeof(char*));
  insert_skip_list(l, actual + 3, sizeof(char*));
  insert_skip_list(l, actual + 4, sizeof(char*));
  insert_skip_list(l, actual + 5, sizeof(char*));

  char *expected[6] = {",", "4", "aaaa", "away", "bbb.,", "sdsadaaaaaa"};

  print_skip_list(l, TYPE_STRING);
  struct Node *tmp = l->head->next[0];
  for(int i = 0; tmp != NULL; tmp = tmp->next[0])
  {
    actual[i] = *(char**)tmp->elem;
    i++;
  }

  TEST_ASSERT_EQUAL_STRING_ARRAY(expected, actual, 6);
  delete_skip_list(l);
}

void test_insert_record_skip_list()
{
  struct SkipList *l = create_skip_list(compare_records);
  struct Record actual[8] = {
      {0, "a\0", 1, 0.0001f},
      {1, "c\0", 0, 1.0001f},
      {2, "a\0", 0, 0.0211f},
      {3, "b\0", 24, 0.0001f},
      {4, "b\0", 15, 0.0001f},
      {5, "c\0", 0, 0.0001f},
      {6, "d\0", 0, 0.0001f},
      {7, "a\0", 0, 0.0001f}
    };
  
  insert_skip_list(l, actual + 0, sizeof(struct Record));
  insert_skip_list(l, actual + 1, sizeof(struct Record));
  insert_skip_list(l, actual + 2, sizeof(struct Record));
  insert_skip_list(l, actual + 3, sizeof(struct Record));
  insert_skip_list(l, actual + 4, sizeof(struct Record));
  insert_skip_list(l, actual + 5, sizeof(struct Record));
  insert_skip_list(l, actual + 6, sizeof(struct Record));
  insert_skip_list(l, actual + 7, sizeof(struct Record));

  struct Record expected[8] = {
      {7, "a\0", 0, 0.0001f},
      {2, "a\0", 0, 0.0211f},
      {0, "a\0", 1, 0.0001f},
      {4, "b\0", 15, 0.0001f},
      {3, "b\0", 24, 0.0001f},
      {5, "c\0", 0, 0.0001f},
      {1, "c\0", 0, 1.0001f},
      {6, "d\0", 0, 0.0001f},
  };

  struct Node *tmp = l->head->next[0];
  for(int i = 0; tmp != NULL; tmp = tmp->next[0])
  {
    actual[i] = *(struct Record*)tmp->elem;
    i++;
  }
  
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

  RUN_TEST(test_search_skip_list);
  RUN_TEST(test_search_int_skip_list);
  RUN_TEST(test_search_char_skip_list);
  return UNITY_END();
}

