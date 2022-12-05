from aoc import *


def parse_stacks(drawing):
    columns = transpose(drawing[:-1][::-1])
    return [[""]] + [list(cat(col).strip())
                     for col in columns[1::4]]


def p1(s, amount, fr, to):
    for _ in range(amount):
        s[to].append(s[fr].pop())


def p2(s, amount, fr, to):
    s[to] += s[fr][-amount:]
    s[fr]  = s[fr][:-amount]


def rearrange(drawing, instructions, func):
    stacks = parse_stacks(drawing)
    for line in instructions:
        func(stacks, *line)
    return cat(s[-1] for s in stacks)


def solve(file=5):
    drawing, raw_instructions = map(parse_multiline_string,
                                    read_input(file, sep="\n\n"))
    instructions = mapl(integers, raw_instructions)
    return (rearrange(drawing, instructions, p1),
            rearrange(drawing, instructions, p2))


print(solve())











def test():
    assert solve("05_test") == ("CMZ", "MCD")
    assert solve() == ('LBLVVTVLP', 'TPFFBDRJD')
