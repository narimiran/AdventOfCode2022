from aoc import *


S = 256
N = -S


directions = [[N-1,  N, N+1], # N
              [S-1,  S, S+1], # S
              [N-1, -1, S-1], # W
              [N+1, +1, S+1]] # E

adjacent = {pt for dir in directions for pt in dir}

def play_round(elves, round):
    proposed = dict()
    for elf in elves:
        if any(elf+pt in elves for pt in adjacent):
            for attempt in range(4):
                n = (round + attempt) % 4
                if not any(elf+pt in elves for pt in directions[n]):
                    prop = elf + directions[n][1]
                    if prop in proposed:
                        del proposed[prop]
                    else:
                        proposed[prop] = elf
                    break
    if not proposed:
        return False
    for new_pos, old_pos in proposed.items():
        elves.remove(old_pos)
        elves.add(new_pos)
    return elves


def calc_score(elves):
    xs = {(elf+10) % S for elf in elves} # avoid negatives
    ys = {elf // S for elf in elves}
    x_len = max(xs) - min(xs) + 1
    y_len = max(ys) - min(ys) + 1
    return x_len * y_len - len(elves)


def play_game(elves, rounds):
    elves = elves.copy()
    for r in range(rounds):
        elves = play_round(elves, r)
        if not elves:
            return r+1
    return calc_score(elves)


def parse_input(data):
    return {x + S*y
            for y, line in enumerate(data)
            for x, c in enumerate(line)
            if c == '#'}


def solve(filename=23):
    elves = parse_input(read_input(filename))
    return play_game(elves, 10), play_game(elves, 9999)


print(solve())










def test():
    assert solve("23_test") == (110, 20)
    assert solve() == (3762, 997)
