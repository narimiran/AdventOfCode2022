import re
from itertools import chain, product


# Most of this is shamelessly stolen from Peter Norvig.

cat = ''.join
inf = float('inf')
flatten = chain.from_iterable


def mapl(f, iterable):
    return list(map(f, iterable))

def mapt(f, iterable):
    return tuple(map(f, iterable))

def filterl(f, iterable):
    return list(filter(f, iterable))


def parse_multiline_string(s, datatype=str, sep='\n'):
    return mapl(datatype, s.split(sep))

def read_input(filename, datatype=str, sep='\n'):
    filename = f"{filename:02d}" if isinstance(filename, int) else filename
    with open(f"inputs/{filename}.txt") as f:
        return parse_multiline_string(f.read(), datatype, sep)

def read_input_line(filename, sep=''):
    filename = f"{filename:02d}" if isinstance(filename, int) else filename
    with open(f"inputs/{filename}.txt") as f:
        contents = f.read().strip()
        return contents if not sep else contents.split(sep)


def digits(line):
    return mapl(int, line)

def integers(text, negative=True):
    return mapt(int, re.findall(r"-?\d+" if negative else r"\d+", text))

def first_int(text):
    if (m := re.search(r"-?\d+", text)):
        return int(m.group(0))


def count_if(iterable, pred=bool):
    return sum(1 for item in iterable if pred(item))

def first(iterable, default=None):
    return next(iter(iterable), default)

def filter_first(iterable, pred):
    return first(el for el in iterable if pred(el))


def move_point(a, b):
    return tuple(p + q for p, q in zip(a, b))

def manhattan(a, b=(0, 0)):
    return sum(abs(p - q) for p, q in zip(a, b))


def sign(n):
    if n > 0: return 1
    elif n < 0: return -1
    else: return 0


def print_2d(lines):
    for line in lines:
        print(cat(line))


def maxval(d):
    return max(d.values())


def transpose(matrix):
    return list(zip(*matrix))


def bin2int(s):
    return int(s, 2)


def neighbours(x, y, amount=4):
    assert amount in {4, 5, 8, 9}
    for dy, dx in product((-1, 0, 1), repeat=2):
        if ((amount == 4 and abs(dx) != abs(dy)) or
            (amount == 5 and abs(dx) + abs(dy) <= 1) or
            (amount == 8 and not dx == dy == 0) or
             amount == 9):
            yield (x+dx, y+dy)

def complex_neighbours(pt, amount=4):
    assert amount in {4, 5, 8, 9}
    for dy, dx in product((-1, 0, 1), repeat=2):
        if ((amount == 4 and abs(dx) != abs(dy)) or
            (amount == 5 and abs(dx) + abs(dy) <= 1) or
            (amount == 8 and not dx == dy == 0) or
             amount == 9):
            yield pt + dx + dy*1j

def neighbours_3d(x, y, z):
    yield from [(x-1, y, z), (x+1, y, z),
                (x, y-1, z), (x, y+1, z),
                (x, y, z-1), (x, y, z+1)]


def list2grid(lines, pred=None):
    return {(x, y): val
            for y, line in enumerate(lines)
            for x, val in enumerate(line)
            if (pred(val) if pred else True)}

def grid2list(grid, pred=bool):
    min_x, min_y = map(min, zip(*grid))
    if min_x < 0 or min_y < 0:
        raise ValueError
    max_x, max_y = map(max, zip(*grid))
    lines = [[' ' for _ in range(max_x+1)]
                  for _ in range(max_y+1)]
    for x, y in grid:
        if pred(grid[(x, y)]):
            lines[y][x] = '█'
    return lines
