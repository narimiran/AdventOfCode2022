from aoc import *
from math import prod


def scenic_score(height, dirs):
    return prod(next((i for i, h in enumerate(d, 1) if h >= height),
                     len(d))
                for d in dirs)


def solve(file=8):
    horizontal = mapl(digits, read_input(file))
    vertical = transpose(horizontal)
    size = len(horizontal)
    visible_trees = 0
    highest_score = 0
    for y in range(size):
        for x in range(size):
            if {x, y} & {0, size-1}: # edges
                visible_trees += 1
            else:
                height = horizontal[y][x]
                row = horizontal[y]
                col = vertical[x]
                directions = [
                    list(reversed(row[:x])),
                    row[x+1:],
                    list(reversed(col[:y])),
                    col[y+1:]
                ]
                visible_trees += any(height > max(d) for d in directions)
                highest_score = max(highest_score, scenic_score(height, directions))
    return visible_trees, highest_score


print(solve())










def test():
    assert solve("08_test") == (21, 8)
    assert solve() == (1829, 291840)
