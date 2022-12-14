from aoc import *
from ast import literal_eval


def compair(left, right):
    match left, right:
        case int(), int(): return left - right
        case list(), list():
            for l, r in zip(left, right):
                if diff := compair(l, r):
                    return diff
            return len(left) - len(right)
        case int(), list():
            return compair([left], right)
        case list(), int():
            return compair(left, [right])


def part_1(packets):
    return sum(i for i, (l, r) in enumerate(packets, 1)
               if compair(literal_eval(l), literal_eval(r)) < 0)


def divider_index(packets, target):
    return sum(compair(p, target) <= 0 for p in packets)


def part_2(packets):
    packets = mapl(literal_eval, flatten(packets))
    two, six = [[2]], [[6]]
    packets += [two, six]
    return divider_index(packets, two) * divider_index(packets, six)


def solve(file=13):
    packets = mapl(parse_multiline_string, read_input(file, sep="\n\n"))
    return part_1(packets), part_2(packets)


print(solve())










def test():
    assert solve("13_test") == (13, 140)
    assert solve() == (5013, 25038)
