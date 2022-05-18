CC := clang
JC := javac
JAR := jar
override CFLAGS := -Wall -Wextra -O3 -DUNITY_INCLUDE_DOUBLE \
-DUNITY_OUTPUT_COLOR -DNDEBUG $(CFLAGS) # to permit appending -ggdb3 -O0
override JFLAGS := $(JFLAGS)

# Directories ------------------------------------------------------------------

BIN := bin
SRC := src
OBJ := obj
CLS := cls
OUT := out
$(info $(shell mkdir -p $(BIN) $(OBJ) $(CLS) $(OUT)))

# Shared library ---------------------------------------------------------------

SHD      := $(SRC)/main/c/shared
SHD_SRCS := $(wildcard $(SHD)/*.c)
SHD_OBJS := $(patsubst $(SHD)/%.c, $(OBJ)/%.o, $(SHD_SRCS))
SHD_HDRS := $(wildcard $(SHD)/*.h)

# Unity tests ------------------------------------------------------------------

CTST := $(SRC)/test/c

TSTQS_SRCS := $(wildcard $(CTST)/quick_sort_test.c)
TSTQS_OBJS := $(patsubst $(CTST)/%.c, $(OBJ)/%.o, $(TSTQS_SRCS))

TSTBIS_SRCS := $(wildcard $(CTST)/binary_insert_sort_test.c)
TSTBIS_OBJS := $(patsubst $(CTST)/%.c, $(OBJ)/%.o, $(TSTBIS_SRCS))

TSTIS_SRCS := $(wildcard $(CTST)/insert_sort_test.c)
TSTIS_OBJS := $(patsubst $(CTST)/%.c, $(OBJ)/%.o, $(TSTIS_SRCS))

TSTSHD_SRCS := $(wildcard $(CTST)/shared_test.c)
TSTSHD_OBJS := $(patsubst $(CTST)/%.c, $(OBJ)/%.o, $(TSTSHD_SRCS))

TSTSKL_SRCS := $(wildcard $(CTST)/skip_list_test.c)
TSTSKL_OBJS := $(patsubst $(CTST)/%.c, $(OBJ)/%.o, $(TSTSKL_SRCS))

UNITY      := $(CTST)/unity
UNITY_SRCS := $(wildcard $(UNITY)/*.c)
UNITY_OBJS := $(patsubst $(UNITY)/%.c, $(OBJ)/%.o, $(UNITY_SRCS))
UNITY_HDRS := $(wildcard $(UNITY)/*.h)

# Junit tests ------------------------------------------------------------------

JTST := $(SRC)/test/java

TSTMHP_SRCS := $(wildcard $(JTST)/MinHeapTests.java)
TSTMHP_CLSS := $(patsubst $(JTST)/%.java, $(CLS)/*/%.class, $(TSTMHP_SRCS))

TSTGRF_SRCS := $(wildcard $(JTST)/GraphTests.java)
TSTGRF_CLSS := $(patsubst $(JTST)/%.java, $(CLS)/*/%.class, $(TSTGRF_SRCS))

TSTGRH_SRCS := $(wildcard $(JTST)/GraphHelperTests.java)
TSTGRH_CLSS := $(patsubst $(JTST)/%.java, $(CLS)/*/%.class, $(TSTGRH_SRCS))

JUNIT := $(JTST)/junit

# Ex1 --------------------------------------------------------------------------

EX1		 := $(SRC)/main/c/ex1
EX1_SRCS := $(wildcard $(EX1)/*.c)
EX1_OBJS := $(patsubst $(EX1)/%.c, $(OBJ)/%.o, $(EX1_SRCS))
EX1_HDRS := $(wildcard $(EX1)/headers/*.h)

# Ex2 --------------------------------------------------------------------------

EX2		 := $(SRC)/main/c/ex2
EX2_SRCS := $(wildcard $(EX2)/*.c)
EX2_OBJS := $(patsubst $(EX2)/%.c, $(OBJ)/%.o, $(EX2_SRCS))
EX2_HDRS := $(wildcard $(EX2)/headers/*.h)

# Ex3 --------------------------------------------------------------------------

EX3		 := $(SRC)/main/java/ex3
EX3_SRCS := $(wildcard $(EX3)/*.java)
EX3_CLSS := $(patsubst $(EX3)/%.java, $(CLS)/%.class, $(EX3_SRCS))

# Ex4 --------------------------------------------------------------------------

EX4 		:= $(SRC)/main/java/ex4
EX4_SRCS := $(wildcard $(EX4)/*.java)
EX4_CLSS := $(patsubst $(EX4)/%.java, $(CLS)/%.class, $(EX4_SRCS))

# Targets ----------------------------------------------------------------------

.PHONY: clean main_ex1 main_ex2 testall testbis testqs testshd testskl testmhp testgrf testgrh

main_ex1: $(BIN)/main_ex1
$(BIN)/main_ex1: $(EX1_OBJS) $(SHD_OBJS)
	$(CC) $(CFLAGS) -o $@ $^

main_ex2: $(BIN)/main_ex2
$(BIN)/main_ex2: $(EX2_OBJS) $(SHD_OBJS)
	$(CC) $(CFLAGS) -o $@ $^

main_ex4: $(OUT)/main_ex4
$(OUT)/main_ex4: $(EX4_CLSS) $(EX3_CLSS)
	$(JAR) cf $@ $^

testshd: $(BIN)/testshd
$(BIN)/testshd: $(UNITY_OBJS) $(TSTSHD_OBJS) $(SHD_OBJS)
	$(CC) $(CFLAGS) -o $@ $^

testqs: $(BIN)/testqs
$(BIN)/testqs: $(UNITY_OBJS) $(filter-out $(OBJ)/ex1.o, $(EX1_OBJS)) $(TSTQS_OBJS) $(SHD_OBJS)
	$(CC) $(CFLAGS) -o $@ $^

testbis: $(BIN)/testbis
$(BIN)/testbis: $(UNITY_OBJS) $(filter-out $(OBJ)/ex1.o, $(EX1_OBJS)) $(TSTBIS_OBJS) $(SHD_OBJS)
	$(CC) $(CFLAGS) -o $@ $^

testis: $(BIN)/testis
$(BIN)/testis: $(UNITY_OBJS) $(filter-out $(OBJ)/ex1.o, $(EX1_OBJS)) $(TSTIS_OBJS) $(SHD_OBJS)
	$(CC) $(CFLAGS) -o $@ $^

testskl: $(BIN)/testskl
$(BIN)/testskl: $(UNITY_OBJS) $(filter-out $(OBJ)/ex2.o, $(EX2_OBJS)) $(TSTSKL_OBJS) $(SHD_OBJS)
	$(CC) $(CFLAGS) -o $@ $^

testmhp: $(OUT)/testmhp
$(OUT)/testmhp: $(TSTMHP_CLSS) $(EX3_CLSS)
	$(JAR) cf $@ $^

testgrf: $(OUT)/testgrf
$(OUT)/testgrf: $(TSTGRF_CLSS) $(filter-out $(CLS)/Main.class, $(EX4_CLSS))
	$(JAR) cf $@ $^

testgrh: $(OUT)/testgrh
$(OUT)/testgrh: $(TSTGRH_CLSS) $(filter-out $(CLS)/Main.class, $(EX4_CLSS))
	$(JAR) cf $@ $^

testall: testshd testqs testbis testskl testis
	@./$(BIN)/testshd
	@./$(BIN)/testqs
	@./$(BIN)/testbis
	@./$(BIN)/testis
	@./$(BIN)/testskl
	@./$(OUT)/testmhp
	@./$(OUT)/testgrf
	@./$(OUT)/testgrh

$(OBJ)/%.o : $(EX1)/%.c $(EX1_HDRS)
	$(CC) $(CFLAGS) -c $< -o $@

$(OBJ)/%.o : $(EX2)/%.c $(EX2_HDRS)
	$(CC) $(CFLAGS) -c $< -o $@

$(OBJ)/%.o : $(SHD)/%.c $(SHD_HDRS)
	$(CC) $(CFLAGS) -c $< -o $@

$(OBJ)/%.o : $(CTST)/%.c $(TST_HDRS)
	$(CC) $(CFLAGS) -c $< -o $@

$(OBJ)/%.o : $(UNITY)/%.c $(UNITY_HDRS)
	$(CC) $(CFLAGS) -c $< -o $@

%(CLS)/%.class : $(EX4)/%.java
	$(JC) $(JFLAGS) -d $(CLS) -sourcepath $(SRC) $<

%(CLS)/%.class : $(EX3)/*/%.java
	$(JC) $(JFLAGS) -d $(CLS) -sourcepath $(SRC) $<

%(CLS)/%.class : $(JTST)/%.java
	$(JC) $(JFLAGS) -cp $(JUNIT) -d $(CLS) -sourcepath $(SRC) $<

clean:
	@rm -f -r $(BIN) $(OBJ) $(CLS) $(OUT) *~