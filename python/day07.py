from aoc import *
from collections import defaultdict
from itertools import accumulate


def parse_input(data):
    sizes = defaultdict(int)
    for line in data:
        match line.split():
            case '$', 'cd', "/":  path = ['/']
            case '$', 'cd', "..": path.pop()
            case '$', 'cd', f:    path.append(f+'/')
            case '$', 'ls':       pass
            case 'dir', _:        pass
            case val, _:
                for p in accumulate(path):
                    sizes[p] += int(val)
    return sizes


def part_1(sizes):
    return sum(v for v in sizes.values()
               if v <= 100_000)


def part_2(sizes):
    total_size = sizes['/']
    goal = 40_000_000
    to_remove = total_size - goal
    return min(v for v in sizes.values()
               if v >= to_remove)


def solve(file=7):
    sizes = parse_input(read_input(file))
    return part_1(sizes), part_2(sizes)


print(solve())










def test():
    assert solve("07_test") == (95437, 24933642)
    assert solve() == (1444896, 404395)
