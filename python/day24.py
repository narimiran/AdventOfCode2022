from aoc import *


def parse_input(data):
    walls = set()
    blizzards = set()
    for y, line in enumerate(data, -1):
        for x, c in enumerate(line, -1):
            match c:
                case '#': walls.add((x, y))
                case '>': blizzards.add((x, y, 1, 0))
                case '<': blizzards.add((x, y, -1, 0))
                case 'v': blizzards.add((x, y, 0, 1))
                case '^': blizzards.add((x, y, 0, -1))
    height, width = len(data)-2, len(data[0])-2
    walls |= {(0, -2), (width-1, height+1)}
    return blizzards, walls, height, width


def traverse(obstacles, h, w, start, goal, t=0):
    blizzards, walls = obstacles
    positions = {start}
    while positions:
        if goal in positions: return t
        t += 1
        candidates = {nb for pt in positions
                         for nb in neighbours(*pt, 5)}
        obstacles = walls | {((x + dx*t) % w, (y + dy*t) % h)
                             for x, y, dx, dy in blizzards}
        positions = candidates - obstacles


def solve(filename=24):
    *obstacles, h, w = parse_input(read_input(filename))
    start = (0, -1)
    goal = (w-1, h)
    p1 = traverse(obstacles, h, w, start, goal)
    back = traverse(obstacles, h, w, goal, start, p1)
    p2 = traverse(obstacles, h, w, start, goal, back)
    return p1, p2


print(solve())










def test():
    assert solve("24_test") == (18, 54)
    assert solve() == (266, 853)
