from string import ascii_letters
from aoc import *


def part_1(data):
    result = 0
    for line in data:
        a, b = line[:len(line)//2], line[len(line)//2:]
        same = (set(a) & set(b)).pop()
        result += ascii_letters.index(same) + 1
    return result


def part_2(data):
    result = 0
    for i in range(0, len(data), 3):
        a, b, c = data[i:i+3]
        same = (set(a) & set(b) & set(c)).pop()
        result += ascii_letters.index(same) + 1
    return result


def solve(file=3):
    data = read_input(file)
    return part_1(data), part_2(data)


print(solve())










def test():
    assert solve("03_test") == (157, 70)
    assert solve() == (8515, 2434)
