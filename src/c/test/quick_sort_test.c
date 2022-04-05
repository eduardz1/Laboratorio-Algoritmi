#include "unity/unity.h"
#include "../ex1/headers/quick_sort.h"

void setUp(void) {
    printf("Hello World!");
}

void tearDown(void) {
    printf("Hello World! 33333");
}

void abdullah(void) {
    printf("Hello World! 44444");
}

int main(int argc, char const *argv[])
{
    UNITY_BEGIN();
    printf("Hello World! 222222");
    RUN_TEST(abdullah);
    RUN_TEST(abdullah);
    return UNITY_END();
}
