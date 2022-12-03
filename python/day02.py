from aoc import *


def part_1(data):
    return sum(ord(pl) - ord("X") + 1               # shape score
               + 3 * ((ord(pl) - ord(op) + 2) % 3)  # outcome score
               for op, _, pl in data)


def part_2(data):
    result = 0
    for op, _, pl in data:
        rps = ord(op) - ord("A")
        match pl:
            case "X":
                result += 0 + 1 + (rps - 1) % 3
            case "Y":
                result += 3 + 1 + rps
            case "Z":
                result += 6 + 1 + (rps + 1) % 3
    return result


def solve(file=2):
    data = read_input(file, list)
    return part_1(data), part_2(data)


print(solve())










def test():
    assert solve("02_test") == (15, 12)
    assert solve() == (13_005, 11_373)
