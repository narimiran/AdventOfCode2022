from aoc import *


def find_extremes(cubes):
    lower = min(min(c) for c in cubes)
    upper = max(max(c) for c in cubes)
    return range(lower-1, upper+2)


def space_around(cubes):
    limits = find_extremes(cubes)
    seen = cubes.copy()
    queue = [(limits[0],) * 3]
    while queue:
        current = queue.pop()
        for nb in neighbours_3d(*current):
            if nb not in seen and all(coord in limits for coord in nb):
                seen.add(nb)
                queue.append(nb)
    return seen - cubes


def part_1(cubes, neighbours):
    return sum(nb not in cubes for nb in neighbours)


def part_2(cubes, neighbours):
    outside = space_around(cubes)
    return sum(nb in outside for nb in neighbours)


def solve(file=18):
    cubes = set(map(integers, read_input(file)))
    neighbours = [nb for cube in cubes
                     for nb in neighbours_3d(*cube)]
    return part_1(cubes, neighbours), part_2(cubes, neighbours)


print(solve())










def test():
    assert solve("18_test") == (64, 58)
    assert solve() == (3326, 1996)