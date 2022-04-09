#include "unity/unity.h"
#include "../shared/common.h"
#include "../shared/record.h"

void test_compare_int(void) {
  
  int member1, member2, result;

  // Equal
  member1 = 0;
  member2 = 0;
  result = compare_int(&member1, &member2);
  TEST_ASSERT_EQUAL_INT(member1, member2);
  TEST_ASSERT_EQUAL_INT(0, result);

  member1 = INT_MAX;
  member2 = INT_MIN;

  // Greater
  result = compare_int(&member1, &member2);
  TEST_ASSERT_EQUAL_INT(1, result);

  // Lower
  result = compare_int(&member2, &member1);
  TEST_ASSERT_EQUAL_INT(-1, result);
}

void test_compare_long(void) {
  long member1, member2, result;

  // Equal
  member1 = 0;
  member2 = 0;
  result = compare_long(&member1, &member2);
  TEST_ASSERT_EQUAL_INT(0, result);

  member1 = LONG_MAX;
  member2 = LONG_MIN;
  // Greater

  result = compare_long(&member1, &member2);
  TEST_ASSERT_EQUAL_INT(1, result);

  // Lower
  result = compare_long(&member2, &member1);
  TEST_ASSERT_EQUAL_INT(-1, result);
}

void test_compare_float(void) {
  float member1, member2, result;

  // Equal
  member1 = 0.0f;
  member2 = 0.0f;
  result = compare_float(&member1, &member2);
  TEST_ASSERT_EQUAL_INT(0, result);

  member1 = 0.9f;
  member2 = 0.0f;
  // Greater

  result = compare_float(&member1, &member2);
  TEST_ASSERT_EQUAL_INT(1, result);

  // Lower
  result = compare_float(&member2, &member1);
  TEST_ASSERT_EQUAL_INT(-1, result);
}

void test_compare_double(void) {
  double member1, member2, result;

  // Equal
  member1 = 0.0000f;
  member2 = 0.0000f;
  result = compare_float(&member1, &member2);
  TEST_ASSERT_EQUAL_INT(0, result);

  member1 = 0.9584f;
  member2 = 0.0000f;

  // Greater
  result = compare_double(&member1, &member2);
  TEST_ASSERT_EQUAL_INT(1, result);

  // Lower
  result = compare_double(&member2, &member1);
  TEST_ASSERT_EQUAL_INT(-1, result);
}

void test_compare_char(void) {
  char member1, member2, result;

  // Equal
  member1 = 'a';
  member2 = 'a';
  result = compare_char(&member1, &member2);
  TEST_ASSERT_EQUAL_INT(0, result);

  member1 = 'z';
  member2 = 'a';
  // Greater

  result = compare_char(&member1, &member2);
  TEST_ASSERT_EQUAL_INT(1, result);

  // Lower
  result = compare_char(&member2, &member1);
  TEST_ASSERT_EQUAL_INT(-1, result);
}

void test_compare_string(void) {
  char *member1, *member2, result;

  // Equal
  member1 = "aaa\0";
  member2 = "aaa\0";
  result = compare_string(&member1, &member2);
  TEST_ASSERT_EQUAL_INT(0, result);

  member1 = "zyx\0";
  member2 = "abc\0";
  
  // Greater
  result = compare_string(&member1, &member2);
  TEST_ASSERT_TRUE(result > 0);

  // Lower
  result = compare_string(&member2, &member1);
  TEST_ASSERT_TRUE(result < 0);
}

void test_compare_record(void) {

  struct _record r1;
  struct _record r2;
  int result;
  
  // Equal
  r1.id = 0; 
  r1.field1 = "abc\0";
  r1.field2 = 0;
  r1.field3 = 0.0001f;

  r2.id = 0;
  r2.field1 = "abc\0";
  r2.field2 = 0;
  r2.field3 = 0.0001f;

  result = compare_records(r1, r2);
  TEST_ASSERT_EQUAL_INT(0, result);

  // Greater and lower by first field
  r1.field1 = "abc\0"; r2.field1 = "zyx\0";
  result = compare_records(r1, r2);
  TEST_ASSERT_TRUE(result < 0);
  result = compare_records(r2, r1);
  TEST_ASSERT_TRUE(result > 0);

  // Greater and lower by second field
  r1.field1 = "abc\0"; r2.field1 = "abc\0";
  r1.field2 = 0; r2.field2 = INT_MAX;
  result = compare_records(r1, r2);
  TEST_ASSERT_EQUAL_INT(-1, result);
  result = compare_records(r2, r1);
  TEST_ASSERT_EQUAL_INT(1, result);

  // Greater and lower by third field
  r1.field1 = "abc\0"; r2.field1 = "abc\0";
  r1.field2 = 0; r2.field2 = 0;
  r1.field3 = 0.1245f; r2.field3 = 0.9289f;
  result = compare_records(r1, r2);
  TEST_ASSERT_EQUAL_INT(-1, result);
  result = compare_records(r2, r1);
  TEST_ASSERT_EQUAL_INT(1, result);

}


int main(int argc, char const *argv[])
{
  UNITY_BEGIN();

  RUN_TEST(test_compare_int);
  RUN_TEST(test_compare_long);
  RUN_TEST(test_compare_float);
  RUN_TEST(test_compare_double);
  RUN_TEST(test_compare_char);
  RUN_TEST(test_compare_string);
  RUN_TEST(test_compare_record);

  return UNITY_END();
}