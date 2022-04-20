CC := clang
override CFLAGS := -Wall -Ofast -DUNITY_INCLUDE_DOUBLE -DFALLBACK_BIS \
-DUNITY_OUTPUT_COLOR $(CFLAGS) # to permit appending -ggdb3 -O0

# Directories ------------------------------------------------------------------

BIN := bin
SRC := src
OBJ := obj

# Shared library ---------------------------------------------------------------

SHD      := $(SRC)/c/shared
SHD_SRCS := $(wildcard $(SHD)/*.c)
SHD_OBJS := $(patsubst $(SHD)/%.c, $(OBJ)/%.o, $(SHD_SRCS))
SHD_HDRS := $(wildcard $(SHD)/*.h)

# Unity tests ------------------------------------------------------------------

TST := $(SRC)/c/test

TSTQS_SRCS := $(wildcard $(TST)/quick_sort_test.c)
TSTQS_OBJS := $(patsubst $(TST)/%.c, $(OBJ)/%.o, $(TSTQS_SRCS))

TSTBIS_SRCS := $(wildcard $(TST)/binary_insert_sort_test.c)
TSTBIS_OBJS := $(patsubst $(TST)/%.c, $(OBJ)/%.o, $(TSTBIS_SRCS))

TSTSHD_SRCS := $(wildcard $(TST)/shared_test.c)
TSTSHD_OBJS := $(patsubst $(TST)/%.c, $(OBJ)/%.o, $(TSTSHD_SRCS))

TSTSKL_SRCS := $(wildcard $(TST)/skip_list_test.c)
TSTSKL_OBJS := $(patsubst $(TST)/%.c, $(OBJ)/%.o, $(TSTSKL_SRCS))

UNITY      := $(TST)/unity
UNITY_SRCS := $(wildcard $(UNITY)/*.c)
UNITY_OBJS := $(patsubst $(UNITY)/%.c, $(OBJ)/%.o, $(UNITY_SRCS))
UNITY_HDRS := $(wildcard $(UNITY)/*.h)

# Ex1 --------------------------------------------------------------------------

EX1			 := $(SRC)/c/ex1
EX1_SRCS := $(wildcard $(EX1)/*.c)
EX1_OBJS := $(patsubst $(EX1)/%.c, $(OBJ)/%.o, $(EX1_SRCS))
EX1_HDRS := $(wildcard $(EX1)/headers/*.h)

# Ex2 --------------------------------------------------------------------------

EX2			 := $(SRC)/c/ex2
EX2_SRCS := $(wildcard $(EX2)/*.c)
EX2_OBJS := $(patsubst $(EX2)/%.c, $(OBJ)/%.o, $(EX2_SRCS))
EX2_HDRS := $(wildcard $(EX2)/headers/*.h)

# Targets ----------------------------------------------------------------------

main_ex1: $(BIN)/main_ex1
$(BIN)/main_ex1: $(EX1_OBJS) $(SHD_OBJS)
	$(CC) $(CFLAGS) -o $@ $^

main_ex2 : $(BIN)/main_ex2
$(BIN)/main_ex2: $(EX2_OBJS) $(SHD_OBJS)
	$(CC) $(CFLAGS) -o $@ $^

testshd: $(BIN)/testshd
$(BIN)/testshd: $(UNITY_OBJS) $(TSTSHD_OBJS) $(SHD_OBJS)
	$(CC) $(CFLAGS) -o $@ $^

testqs: $(BIN)/testqs
$(BIN)/testqs: $(UNITY_OBJS) $(filter-out $(OBJ)/ex1.o, $(EX1_OBJS)) $(TSTQS_OBJS) $(SHD_OBJS)
	$(CC) $(CFLAGS) -o $@ $^

testbis: $(BIN)/testbis
$(BIN)/testbis: $(UNITY_OBJS) $(filter-out $(OBJ)/ex1.o, $(EX1_OBJS)) $(TSTBIS_OBJS) $(SHD_OBJS)
	$(CC) $(CFLAGS) -o $@ $^

testskl: $(BIN)/testskl
$(BIN)/testskl: $(UNITY_OBJS) $(filter-out $(OBJ)/ex2.o, $(EX2_OBJS)) $(TSTSKL_OBJS) $(SHD_OBJS)
	$(CC) $(CFLAGS) -o $@ $^

testall: testshd testqs testbis testskl
	@./$(BIN)/testshd
	@./$(BIN)/testqs
	@./$(BIN)/testbis
	@./$(BIN)/testskl

$(OBJ)/%.o : $(EX1)/%.c $(EX1_HDRS)
	$(CC) $(CFLAGS) -c $< -o $@

$(OBJ)/%.o : $(EX2)/%.c $(EX2_HDRS)
	$(CC) $(CFLAGS) -c $< -o $@

$(OBJ)/%.o : $(SHD)/%.c $(SHD_HDRS)
	$(CC) $(CFLAGS) -c $< -o $@

$(OBJ)/%.o : $(TST)/%.c $(TST_HDRS)
	$(CC) $(CFLAGS) -c $< -o $@

$(OBJ)/%.o : $(UNITY)/%.c $(UNITY_HDRS)
	$(CC) $(CFLAGS) -c $< -o $@

.PHONY: clean
clean:
	@rm -f $(BIN)/* $(OBJ)/* *~