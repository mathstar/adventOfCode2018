#!/usr/bin/python3

import sys

n = 0
values = set([0])
while True:
	f = open('input.txt', 'r')
	for line in f:
		n = n + int(line)
		if n in values:
			print("Solution is:")
			print(n)
			sys.exit()
		values.add(n)
		print(n)
	f.close()
