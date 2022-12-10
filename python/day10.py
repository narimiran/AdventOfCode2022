from aoc import *


def read_instructions(file):
    for line in read_input(file):
        match line.split():
            case 'addx', n: yield from [0, int(n)]
            case _:         yield 0


def solve(file=10):
    x = 1
    signal_strength = 0
    screen = [['  ' for _ in range(40)] for _ in range(6)]
    for cycle, instr in enumerate(read_instructions(file), 1):
        row, col = divmod(cycle-1, 40)
        if (cycle - 20) % 40 == 0:
            signal_strength += cycle * x
        if x-1 <= col <= x+1:
            screen[row][col] = '██'
        x += instr
    return signal_strength, screen


signal_strength, screen = solve(10)
print(signal_strength)
print_2d(screen)










def test():
    assert solve("10_test")[0] == 13140
    assert solve()[0] == 14320
