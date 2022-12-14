from aoc import *
from itertools import count


def parse_line(line):
    points = mapl(integers, line.split(" -> "))
    rock = set()
    for (ax, ay), (bx, by) in zip(points, points[1:]):
        for px in range(min(ax, bx), max(ax, bx)+1):
            for py in range(min(ay, by), max(ay, by)+1):
                rock.add(complex(px, py))
    return rock


def parse_input(data):
    rock = set()
    for line in data:
        rock |= parse_line(line)
    return rock


START = 500


def solve(file=14):
    def pour(pt, p1):
        if pt in settled:
            return pt.imag == floor
        for dx in (0, -1, 1):
            if pour(pt + dx+1j, p1) and p1:
                return True
        settled.add(pt)
    count_sand = lambda settled: len(settled) - rock_count

    settled = parse_input(read_input(file))
    floor = int(max(pt.imag for pt in settled)) + 2
    for x in range(START-floor, START+floor+1):
        settled.add(complex(x, floor))
    rock_count = len(settled)
    pour(START, True)
    p1 = count_sand(settled)
    pour(START, False)
    return p1, count_sand(settled)


print(solve())










def test():
    assert solve("14_test") == (24, 93)
    assert solve() == (1003, 25771)
