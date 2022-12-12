from aoc import *
from collections import deque


def parse_line(line):
    return [ord(c) - ord('a') for c in line]


def find_start_end(grid):
    start = next(k for k, v in grid.items() if v == -14)
    grid[start] = 0
    end = next(k for k, v in grid.items() if v == -28)
    grid[end] = 25
    return start, end


def travel(grid, start, end, part):
    seen = set()
    queue = deque([(0, end)])
    while queue:
        steps, curr = queue.popleft()
        if grid[curr] == 0 and (part == 2 or curr == start):
            return steps
        for nb in neighbours(*curr):
            if nb not in seen and grid.get(nb, -inf) >= grid[curr] - 1:
                seen.add(nb)
                queue.append((steps+1, nb))
    return steps


def solve(file=12):
    grid = list2grid(map(parse_line, read_input(file)))
    start, end = find_start_end(grid)
    return (travel(grid, start, end, 1),
            travel(grid, start, end, 2))


print(solve())










def test():
    assert solve("12_test") == (31, 29)
    assert solve() == (352, 345)
