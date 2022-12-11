from aoc import *
from operator import mul, add, pow
from copy import deepcopy


class Monkey:
    lcm = 1

    def __init__(self, information):
        lines = information.splitlines()
        ops = lines[2].split()
        try:
            val = int(ops[-1])
            op = mul if ops[-2] == '*' else add
        except ValueError:
            val = 2
            op = pow
        d, t, f = mapl(first_int, lines[3:])
        Monkey.lcm *= d
        self.items = list(integers(lines[1]))
        self.operation = op
        self.value = val
        self.divisor = d
        self.true_case = t
        self.false_case = f
        self.inspected = 0

    def division_test(self, x):
        return self.false_case if x % self.divisor else self.true_case

    def inspect(self, worry_division):
        self.inspected += 1
        item = self.items.pop()
        worry = self.operation(item, self.value)
        if worry_division: worry //= 3
        worry %= Monkey.lcm
        return worry


def play_round(monkeys, worry_division):
    for monkey in monkeys:
        while monkey.items:
            worry_level = monkey.inspect(worry_division)
            where_to = monkey.division_test(worry_level)
            monkeys[where_to].items.append(worry_level)


def play_game(monkeys, rounds, worry_division):
    monkeys = deepcopy(monkeys)
    for _ in range(rounds):
        play_round(monkeys, worry_division)
    inspections = sorted(m.inspected for m in monkeys)
    return mul(*inspections[-2:])


def solve(file=11):
    monkeys = [Monkey(m) for m in read_input(file, sep="\n\n")]
    return play_game(monkeys, 20, True), play_game(monkeys, 10_000, False)


print(solve())










def test():
    assert solve("11_test") == (10605, 2713310158)
    assert solve() == (66802, 21800916620)
