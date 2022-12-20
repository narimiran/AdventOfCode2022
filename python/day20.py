from aoc import *
from collections import deque


def move(d, el):
    idx = d.index(el)
    d.rotate(-idx)
    _, v = d.popleft()
    d.rotate(-v)
    d.appendleft(el)


def mix(d, rounds=1):
    original = d.copy()
    for _ in range(rounds):
        for el in original:
            move(d, el)
    while d[0][1] != 0:
        d.rotate(-1)
    l = len(d)
    return sum(d[n % l][1] for n in (1000, 2000, 3000))


def solve(filename=20):
    data = read_input(filename, int)
    d1 = deque(enumerate(data))
    d2 = deque((i, 811589153*n) for i, n in enumerate(data))
    return mix(d1), mix(d2, 10)


print(solve())










def test():
    assert solve("20_test") == (3, 1623178306)
    assert solve(20) == (5904, 8332585833851)
