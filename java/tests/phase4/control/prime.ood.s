STDOUT = 1
STDIN = 0
.data
.comm _IsPrime_n, 4, 4
.comm _IsPrime_half, 4, 4
.comm _IsPrime_i, 4, 4
.comm _IsPrime_quot, 4, 4
.comm _IsPrime_isprime, 4, 4
.text
_IsPrime_start:
.data
.text
	call readint
	pushl %eax
	popl _IsPrime_n
_while2:
	pushl _IsPrime_n
	pushl $0
	popl %ebx
	popl %eax
	cmpl %ebx, %eax
	jg _equal1
	jmp _notequal1
_equal1:
	pushl $1
	jmp _end1
_notequal1:
	pushl $0
	jmp _end1
_end1:
	popl %eax
	cmpl $0, %eax
	jne _startwhilebody2
	jmp _endwhile2
_startwhilebody2:
	pushl $1
	popl _IsPrime_isprime
	pushl _IsPrime_n
	pushl $1
	popl %ebx
	popl %eax
	cmpl %ebx, %eax
	je _equal2
	jmp _notequal2
_equal2:
	pushl $1
	jmp _end2
_notequal2:
	pushl $0
	jmp _end2
_end2:
	popl %eax
	cmpl $0, %eax
	jne _if4
	jmp _else4
_if4:
	pushl $0
	popl _IsPrime_isprime
	jmp _endif4
_else4:
	pushl _IsPrime_n
	pushl $2
	popl %ebx
	popl %eax
	cmpl %ebx, %eax
	jg _equal3
	jmp _notequal3
_equal3:
	pushl $1
	jmp _end3
_notequal3:
	pushl $0
	jmp _end3
_end3:
	popl %eax
	cmpl $0, %eax
	jne _if3
	jmp _else3
_if3:
	pushl $2
	pushl _IsPrime_n
	popl %eax
	popl %ebx
	cdq
	idivl %ebx
	pushl %eax
	popl _IsPrime_quot
	pushl _IsPrime_quot
	pushl $2
	popl %eax
	popl %ebx
	imull %ebx, %eax
	pushl %eax
	pushl _IsPrime_n
	popl %ebx
	popl %eax
	cmpl %ebx, %eax
	je _equal4
	jmp _notequal4
_equal4:
	pushl $1
	jmp _end4
_notequal4:
	pushl $0
	jmp _end4
_end4:
	popl %eax
	cmpl $0, %eax
	jne _if2
	jmp _else2
_if2:
	pushl $0
	popl _IsPrime_isprime
	jmp _endif2
_else2:
	pushl $3
	popl _IsPrime_i
	pushl $1
	pushl $2
	pushl _IsPrime_n
	popl %eax
	popl %ebx
	cdq
	idivl %ebx
	pushl %eax
	popl %eax
	popl %ebx
	addl %ebx, %eax
	pushl %eax
	popl _IsPrime_half
_while1:
	pushl _IsPrime_half
	pushl _IsPrime_i
	popl %ebx
	popl %eax
	cmpl %ebx, %eax
	jge _equal5
	jmp _notequal5
_equal5:
	pushl $1
	jmp _end5
_notequal5:
	pushl $0
	jmp _end5
_end5:
	pushl _IsPrime_isprime
	popl %eax
	popl %ebx
	andl %ebx, %eax
	pushl %eax
	popl %eax
	cmpl $0, %eax
	jne _startwhilebody1
	jmp _endwhile1
_startwhilebody1:
	pushl _IsPrime_i
	pushl _IsPrime_n
	popl %eax
	popl %ebx
	cdq
	idivl %ebx
	pushl %eax
	popl _IsPrime_quot
	pushl _IsPrime_quot
	pushl _IsPrime_i
	popl %eax
	popl %ebx
	imull %ebx, %eax
	pushl %eax
	pushl _IsPrime_n
	popl %ebx
	popl %eax
	cmpl %ebx, %eax
	je _equal6
	jmp _notequal6
_equal6:
	pushl $1
	jmp _end6
_notequal6:
	pushl $0
	jmp _end6
_end6:
	popl %eax
	cmpl $0, %eax
	jne _if1
	jmp _else1
_if1:
	pushl $0
	popl _IsPrime_isprime
	jmp _endif1
_else1:
	jmp _endif1
_endif1:
	pushl $2
	pushl _IsPrime_i
	popl %eax
	popl %ebx
	addl %ebx, %eax
	pushl %eax
	popl _IsPrime_i
	jmp _while1
_endwhile1:
	jmp _endif2
_endif2:
	jmp _endif3
_else3:
	jmp _endif3
_endif3:
	jmp _endif4
_endif4:
	pushl _IsPrime_isprime
	popl %eax
	cmpl $0, %eax
	jne _if5
	jmp _else5
_if5:
	pushl $1
	call writeint
	addl $4, %esp
	jmp _endif5
_else5:
	pushl $0
	call writeint
	addl $4, %esp
	jmp _endif5
_endif5:
	call readint
	pushl %eax
	popl _IsPrime_n
	jmp _while2
_endwhile2:
	ret
.text
.global main
main:
	call _IsPrime_start
	pushl $0
	call exit
