from aoc import *


DIRECTIONS = {'U': -1j, 'D': 1j, 'L': -1, 'R': 1}


def solve(file=9):
    commands = mapl(str.split, read_input(file))
    snake = [0] * 10
    snake_paths = [set() for _ in snake]
    for cmd, amount in commands:
        for _ in range(int(amount)):
            snake[0] += DIRECTIONS[cmd]
            for i, tail in enumerate(snake[1:], 1):
                diff = snake[i-1] - tail
                if abs(diff) >= 2:
                    snake[i] += complex(sign(diff.real), sign(diff.imag))
                snake_paths[i].add(snake[i])
    return len(snake_paths[1]), len(snake_paths[9])


print(solve())










def test():
    assert solve("09_test") == (13, 1)
    assert solve("09_test2") == (88, 36)
    assert solve() == (6197, 2562)
