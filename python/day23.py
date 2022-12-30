from aoc import *
from collections import defaultdict


def check_direction(dir):
    for delta in (-1, 0, 1):
        yield dir + delta * (1j if dir in {-1, 1} else 1)


def play_round(elves, round):
    new_elves = set()
    proposed = defaultdict(set)
    directions = [-1j, 1j, -1, 1]
    for elf in elves:
        if not any(nb in elves for nb in complex_neighbours(elf, 8)):
            new_elves.add(elf)
        else:
            for attempt in range(4):
                direction = directions[(round + attempt) % 4]
                if not any(elf+nb in elves for nb in check_direction(direction)):
                    proposed[elf+direction].add(elf)
                    break
            else:
                new_elves.add(elf)
    if not proposed:
        return False
    for pos, old_pos in proposed.items():
        if len(old_pos) == 1:
            new_elves.add(pos)
        else:
            new_elves |= old_pos
    return new_elves


def calc_score(elves):
    xs = {int(elf.real) for elf in elves}
    ys = {int(elf.imag) for elf in elves}
    x_len = max(xs) - min(xs) + 1
    y_len = max(ys) - min(ys) + 1
    return x_len * y_len - len(elves)


def play_game(elves, rounds):
    for r in range(rounds):
        elves = play_round(elves, r)
        if not elves:
            return r+1
    return calc_score(elves)


def parse_input(data):
    return {x + y*1j for y, line in enumerate(data)
                     for x, c in enumerate(line)
                     if c == '#'}


def solve(filename=23):
    elves = parse_input(read_input(filename))
    return play_game(elves, 10), play_game(elves, 9999)


print(solve())










def test():
    assert solve("23_test") == (110, 20)
    assert solve() == (3762, 997)
