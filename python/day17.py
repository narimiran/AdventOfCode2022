from aoc import *


left = -1
right = 1
down = -1j

rocks = [
    {0, 1, 2, 3},
    {1, 1j, 1+1j, 2+1j, 1+2j},
    {0, 1, 2, 2+1j, 2+2j},
    {0, 1j, 2j, 3j},
    {0, 1, 1j, 1+1j}
]


def move(rock, delta):
    return set(map(lambda pt: pt + delta, rock))

def is_inbounds(rock):
    return all(0 <= pt.real < 7 for pt in rock)

def would_clash(rock, tower):
    return any(pt in tower for pt in rock)

def peaks(tower, max_y):
    return frozenset(pt.real for pt in tower if pt.imag == max_y)


def play(movements, rounds):
    M = len(movements)
    R = len(rocks)
    m = 0
    max_y = 0
    extra_y = 0
    seen = dict()
    tower = set(range(7))
    t = 0
    while t < rounds:
        rock = move(rocks[t%R], 2 + (4+max_y)*1j)
        t += 1
        while True:
            dx = [left, right][movements[m%M] == '>']
            m += 1
            rock_ = move(rock, dx)
            if is_inbounds(rock_) and not would_clash(rock_, tower):
                rock = rock_
            rock_ = move(rock, down)
            if not would_clash(rock_, tower):
                rock = rock_
            else:
                max_y = int(max(max_y, *(pt.imag for pt in rock)))
                tower = rock | {pt for pt in tower if pt.imag >= max_y - 100}
                t_hash = peaks(tower, max_y), t%R, m%M
                if t_hash in seen:
                    t1, y1 = seen[t_hash]
                    dt = t - t1
                    dy = max_y - y1
                    skipped_periods = (rounds - t) // dt
                    extra_y += skipped_periods * dy
                    t += skipped_periods * dt
                seen[t_hash] = t, max_y
                break
    return max_y + extra_y


def solve(filename=17):
    movements = read_input_line(filename)
    return play(movements, 2022), play(movements, 1000000000000)

print(solve())










def test():
    assert solve("17_test") == (3068, 1514285714288)
    assert solve() == (3232, 1585632183915)
