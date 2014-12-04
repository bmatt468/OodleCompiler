// ----------------------------------------------------------------------
// File: stdlib.c
// Runtime support library for Oodle
// ----------------------------------------------------------------------

#include <stdlib.h>
#include "stdlib.h"
typedef enum { false, true } bool;

// ----------------------------------------------------------------------
// I/O Management Functions
// ----------------------------------------------------------------------

// writes <ch> to standard output (<out> is the predefined Oodle Writer object)
void _Writer_io_write(void *out, int ch) {
  char c = ch;
  
  write(1, &c, 1);  
}

// reads a character from stdin and returns it (<in> is the predefined Oodle Reader object)
int _Reader_io_read(void *in) {
  char c;

  read(0, &c, 1);

  return c;
}

// writes float num to stdout
void _Writer_io_writefloat(void *out,float num) {
	printf("%10.10f\n",num);
}

// reads float num from stdin
float _Reader_io_readfloat(void *in) {
	float num;
	scanf("%f",&num);
	return num;
}

// ----------------------------------------------------------------------
// String Management Functions
// ----------------------------------------------------------------------

// Constructs and returns an Oodle String using chars in <lit>, which must be null terminated
struct String *string_fromlit(char *lit)
{
  struct String *newstr = (struct String *)calloc(sizeof(struct String), 1);
  struct CharNode *cur = NULL;
  while (*lit) {
    struct CharNode *node = (struct CharNode *)calloc(sizeof(struct CharNode), 1);
    node->ch = *lit;
    if (cur == NULL) {
      newstr->list = node;
    } else {
      cur->next = node;
    }
    cur = node;
    lit++;
  }
  return newstr; 
}

void nullpointertest(int lineno, void* ptr) {
	if (ptr == NULL) {
		char text[] = "NULL pointer exception found\n ";			
		write(1, text, sizeof(text) - 1);
		printf("%i\n",lineno);
		exit(0);
	}
} 

// ----------------------------------------------------------------------
// Float Management Functions
// ----------------------------------------------------------------------
bool _Float_isGreater(float x, float y) {
	return (x > y);
}

bool _Float_isEqual(float x, float y) {
	return (x == y);
}

bool _Float_isGreaterEqual(float x, float y) {
	return (x >= y);
}

