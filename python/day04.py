from aoc import *


def part_1(data):
    return sum((a <= c and d <= b) or (c <= a and b <= d)
               for a, b, c, d in data)


def part_2(data):
    return sum(not(a > d or b < c) for a, b, c, d in data)


def solve(file=4):
    data = [integers(line, negative=False) for line in read_input(file)]
    return part_1(data), part_2(data)


print(solve())










def test():
    assert solve("04_test") == (2, 4)
    assert solve() == (569, 936)
