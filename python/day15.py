from aoc import *


def find_radii(data):
    return [((sx, sy), manhattan((sx, sy), (bx, by)))
            for sx, sy, bx, by in data]


def seen_in_row(sensors, row):
    all_edges = []
    for (sx, sy), r in sensors:
        diff =  r - abs(row - sy)
        if diff >= 0:
            all_edges.append((sx-diff, sx+diff))
    return sorted(all_edges)


def find_a_hole(edges):
    highest = 0
    for (a, b) in edges:
        if a <= highest+1:
            highest = max(b, highest)
        else:
            return a-1


def part_1(sensors, row):
    (a, _), *_, (_, b) = seen_in_row(sensors, row)
    return b - a


def part_2(sensors, limit):
    for row in reversed(range(limit+1)):
        edges = seen_in_row(sensors, row)
        if col := find_a_hole(edges):
            return MULTI*col + row


MULTI = 4_000_000


def solve(file=15, limit=MULTI):
    sensors = find_radii(map(integers, read_input(file)))
    return part_1(sensors, limit//2), part_2(sensors, limit)


print(solve())










def test():
    assert solve("15_test", 20) == (26, 56_000_011)
    assert solve() == (5299855, 13615843289729)
