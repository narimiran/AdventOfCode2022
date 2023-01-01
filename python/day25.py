from aoc import *


digits = "=-012"

def from_5(number):
    s = 0
    for d in number:
        s *= 5
        s += digits.find(d) - 2
    return s


def to_5(number):
    acc = []
    d = number
    while d:
        d, m = divmod(d+2, 5)
        acc.append(digits[m])
    return cat(reversed(acc))


def solve(filename=25):
    numbers = read_input(filename)
    return to_5(sum(from_5(n) for n in numbers))


print(solve())










def test():
    assert solve("25_test") == "2=-1=0"
    assert solve() == "122-2=200-0111--=200"
