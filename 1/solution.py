#!/usr/bin/python3

f = open('input.txt', 'r')
n = 0
for line in f:
	n = n + int(line)
print(n)
