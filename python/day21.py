from aoc import *
from operator import add, mul, sub, floordiv


operators = {
    "+": add,
    "-": sub,
    "*": mul,
    "/": floordiv
}


def parse_input(data):
    monkeys = dict()
    for line in data:
        m, *ops = line.split()
        m = m[:-1]
        if len(ops) == 1:
            monkeys[m] = int(ops[0])
        else:
            monkeys[m] = (operators[ops[1]], ops[0], ops[2])
    return monkeys


def dfs(monkeys, m='root'):
    if type(monkeys[m]) is int:
        return monkeys[m]
    else:
        op, l, r = monkeys[m]
        return op(dfs(monkeys, l), dfs(monkeys, r))


def human_yell(monkeys):
    _, l, r = monkeys['root']
    monkeys['root'] = (sub, l, r)
    root_value = dfs(monkeys)
    initial_sign = sign(root_value)
    space = (0, 99999999999999)
    while root_value:
        mid = sum(space) // 2
        monkeys['humn'] = mid
        root_value = dfs(monkeys)
        if sign(root_value) == initial_sign:
            space = (mid+1, space[1])
        else:
            space = (space[0], mid)
    return monkeys['humn']


def solve(filename=21):
    monkeys = parse_input(read_input(filename))
    return dfs(monkeys), human_yell(monkeys)


print(solve())










def test():
    assert solve("21_test") == (152, 301)
    assert solve() == (63119856257960, 3006709232464)