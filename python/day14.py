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
down  = lambda pt: pt + 1j
left  = lambda pt: pt + 1j - 1
right = lambda pt: pt + 1j + 1


def pour_sand(rock):
    sand = START
    while True:
        if (pos := down(sand)) not in rock:
            sand = pos
        elif (pos := left(sand)) not in rock:
            sand = pos
        elif (pos := right(sand)) not in rock:
            sand = pos
        else:
            return sand


def solve(file=14):
    settled = parse_input(read_input(file))
    floor = int(max(pt.imag for pt in settled)) + 2
    for x in range(START-floor, START+floor+1):
        settled.add(complex(x, floor))
    p1 = 0
    for n in count(1):
        sand = pour_sand(settled)
        if not p1 and sand.imag == floor-1:
            p1 = n-1
        settled.add(sand)
        if sand == START:
            return p1, n


print(solve())










def test():
    assert solve("14_test") == (24, 93)
    assert solve() == (1003, 25771)
