#include "unity/unity.h"
#include "../ex1/headers/quick_sort.h"

void test_null_array(void) {
  int *actual = NULL;
  TEST_ASSERT_NULL(actual);
  quick_sort(actual,0,0,0,compare_int);
  TEST_ASSERT_NULL(actual);
}

void test_empty_array(void) {
  int actual[] = {};
  int expected[] = {};
  quick_sort(actual,0,0,0,compare_int);
  TEST_ASSERT_EQUAL_INT_ARRAY(expected,actual,0);
}

void test_int_array(void) 
{
  int actual[]={3,5,6,78,8,5};
  int expected[]={3,5,5,6,8,78};
  quick_sort(actual,sizeof(int), 0, 5, compare_int);

  for (int i = 0; i < 6; i++)
        printf("%d \n", actual[i]);
  return 0;

  TEST_ASSERT_EQUAL_INT_ARRAY(expected,actual,6);
}

// qui bisognerebbe passare la size delle stringhe sennò va in segfault
void test_string_array(void) {
  char *actual[]={"aa\0","zz\0","yy\0","cc\0","ab\0","ba\0"};
  char *expected[]={"aa\0","ab\0","ba\0","cc\0","yy\0","zz\0"};
  quick_sort(actual,6,0,5,compare_string);
  TEST_ASSERT_EQUAL_STRING_ARRAY(expected,actual,6);
}

void test_float_array(void) {
  float actual[] = {0.0f, 0.58f, 0.42f, 98.31f, 15.42f};
  float expected[] = {0.0f, 0.42f, 0.58f, 15.42f, 98.31f};
  quick_sort(actual,5,0,4,compare_float);
  TEST_ASSERT_EQUAL_FLOAT_ARRAY(expected,actual,5);
}

void test_char_array(void) {
  char actual[]={'d', 'c', 'a', 'b', 'f', 'e'};
  char expected[]={'a', 'b', 'c', 'd', 'e', 'f'};
  quick_sort(actual,6,0,5,compare_char);
  TEST_ASSERT_EQUAL_FLOAT_ARRAY(expected,actual,5);
}

void test_double_array(void) {
  double actual[] = {0.0000f, 0.5812f, 0.4122f, 98.0931f, 0.4123f};
  double expected[] = {0.0000f, 0.4122f, 0.4123f, 0.5812f, 98.0931f};
  quick_sort(actual,5,0,4,compare_double);
  TEST_ASSERT_EQUAL_DOUBLE_ARRAY(expected,actual,5);
}

/*void test_long_array(void) {
  long actual[] = { INT_MAX + 124, INT_MAX + 12251, 0, LONG_MAX, INT_MAX + 34612};
  long expected[] = {0, INT_MAX + 124, INT_MAX + 12251, INT_MAX + 34612, LONG_MAX};
  quick_sort(actual,4,0,3,compare_long);
  TEST_ASSERT_EQUAL_LONG_ARRAY(expected,actual,4);
}*/

void test_array_with_only_duplicated_elements(void) {
  int actual[] = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
  int expected[] = {1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
  quick_sort(actual,10,0,9,compare_int);
  TEST_ASSERT_EQUAL_INT_ARRAY(expected,actual,10);
}

void test_already_sorted_array(void) {
  int actual[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
  int expected[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
  quick_sort(actual,10,0,9,compare_int);
  TEST_ASSERT_EQUAL_INT_ARRAY(expected,actual,10);
}

void test_invalid_array(void) {

}

void test_negative_int_array(void) {
  int actual[]={-1,-5,-9,-2,-3};
  int expected[]={-9,-5,-3,-2,-1};
  quick_sort(actual,5,0,4,compare_int);
  TEST_ASSERT_EQUAL_INT_ARRAY(expected,actual,6);
}

int main(int argc, char const *argv[])
{
  UNITY_BEGIN();
    
  RUN_TEST(test_null_array);
  RUN_TEST(test_empty_array);
  RUN_TEST(test_int_array);
  RUN_TEST(test_string_array);
  RUN_TEST(test_float_array);
  RUN_TEST(test_char_array);
  RUN_TEST(test_double_array);
  //RUN_TEST(test_long_array);
  RUN_TEST(test_array_with_only_duplicated_elements);
  RUN_TEST(test_already_sorted_array);
  RUN_TEST(test_invalid_array);
  RUN_TEST(test_negative_int_array);
  
  return UNITY_END();
}
