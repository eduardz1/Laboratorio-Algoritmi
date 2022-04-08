#include "unity/unity.h"
#include "../ex1/headers/quick_sort.h"

void test_null_array(void) {

}

void test_empty_array(void) {

}

void test_int_array(void) {
  int actual[]={3,5,6,78,8,5};
  int expected[]={3,5,5,6,8,78};
  // TODO: Fix it
  // quick_sort(actual,6,1,5,compare_int);
  TEST_ASSERT_EQUAL_INT_ARRAY(expected,actual,6);

}

void test_string_array(void) {

}

void test_float_array(void) {

}

void test_char_array(void) {

}

void test_double_array(void) {

}

void test_long_array(void) {

}

void test_array_with_only_duplicated_elements(void) {

}

void test_already_sorted_array(void) {

}

void test_invalid_array(void) {

}

void test_negative_int_array(void) {

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
  RUN_TEST(test_long_array);
  RUN_TEST(test_array_with_only_duplicated_elements);
  RUN_TEST(test_already_sorted_array);
  RUN_TEST(test_invalid_array);
  RUN_TEST(test_negative_int_array);
  
  return UNITY_END();
}
