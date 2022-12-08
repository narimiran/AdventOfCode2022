from aoc import *
from math import prod


def viewing_distance(height, d):
    return next((i for i, h in enumerate(d, 1) if h >= height), inf)

def solve(file=8):
    horizontal = mapl(digits, read_input(file))
    vertical = transpose(horizontal)
    size = len(horizontal)
    visible_from_outside = 0
    highest_score = 0
    for y in range(size):
        for x in range(size):
            height = horizontal[y][x]
            row = horizontal[y]
            col = vertical[x]
            directions = (
                row[:x][::-1],
                row[x+1:],
                col[:y][::-1],
                col[y+1:]
            )
            viewing_distances = [viewing_distance(height, d) for d in directions]
            visible_from_outside += any(dist == inf for dist in viewing_distances)
            visible_trees = (min(len(d), v)
                             for v, d in zip(viewing_distances, directions))
            highest_score = max(highest_score, prod(visible_trees))
    return visible_from_outside, highest_score


print(solve())










def test():
    assert solve("08_test") == (21, 8)
    assert solve() == (1829, 291840)
