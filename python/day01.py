from aoc import *


def parse_input(file):
    return (sum(parse_multiline_string(elf, int))
            for elf in read_input(file, sep='\n\n'))


def solve(file=1):
    elves = sorted(parse_input(file), reverse=True)
    return tuple(sum(elves[:n]) for n in (1, 3))


print(solve())










def test():
    assert solve("01_test") == (24_000, 45_000)
    assert solve() == (70_369, 203_002)
