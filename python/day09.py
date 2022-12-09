from aoc import *


DIRECTIONS = {'U': -1j, 'D': 1j, 'L': -1, 'R': 1}


def solve(file=9):
    commands = map(str.split, read_input(file))
    rope = [0] * 10
    seen = [set(), set()]
    for cmd, amount in commands:
        for _ in range(int(amount)):
            rope[0] += DIRECTIONS[cmd]
            for i in range(1, 10):
                diff = rope[i-1] - rope[i]
                if abs(diff) >= 2:
                    rope[i] += complex(sign(diff.real), sign(diff.imag))
            seen[0].add(rope[1])
            seen[1].add(rope[9])
    return mapt(len, seen)


print(solve())










def test():
    assert solve("09_test") == (13, 1)
    assert solve("09_test2") == (88, 36)
    assert solve() == (6197, 2562)
