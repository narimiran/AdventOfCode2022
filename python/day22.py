from aoc import *
import re


pattern = re.compile(r"(L|R)")


def field2grid(field):
    return {x+y*1j: c
            for y, line in enumerate(field)
            for x, c in enumerate(line)
            if c in "#."}


def wrap_1(pt, d):
    """Hardcoded for my input's shape
          A B
          C
        D E
        F
    """
    x = pt.real
    y = pt.imag * 1j
    if d in {1, -1}:
        match pt.imag//50:
            case 0 | 2: return (x - 100*d) + y, d
            case 1 | 3: return (x -  50*d) + y, d
    else:
        match x//50:
            case 0: return x + (y - 100*d), d
            case 1: return x + (y - 150*d), d
            case 2: return x + (y -  50*d), d


def wrap_2(pt, d):
    """Hardcoded for my input's shape
          A B
          C
        D E
        F
    """
    x, y = pt.real, pt.imag
    match x//50, y//50, d:
        case _, 0, -1:  return complex(0, 149-y), 1     # A --> D
        case _, 1, -1:  return complex(y-50, 100), 1j   # C --> D
        case _, 2, -1:  return complex(50, 149-y), 1    # D --> A
        case _, 3, -1:  return complex(y-100, 0), 1j    # F --> A
        case _, 0,  1:  return complex(99, 149-y), -1   # B --> E
        case _, 1,  1:  return complex(y+50, 49), -1j   # C --> B
        case _, 2,  1:  return complex(149, 149-y), -1  # E --> B
        case _, 3,  1:  return complex(y-100, 149), -1j # F --> E
        case 0, _, -1j: return complex(50, x+50), 1     # D --> C
        case 1, _, -1j: return complex(0, x+100), 1     # A --> F
        case 2, _, -1j: return complex(x-100, 199), -1j # B --> F
        case 0, _,  1j: return complex(x+100, 0), 1j    # F --> B
        case 1, _,  1j: return complex(49, x+100), -1   # E --> F
        case 2, _,  1j: return complex(99, x-50), -1    # B --> C


def traverse(pos, moves, grid, wrap):
    d = 1
    for move in moves:
        match move:
            case 'L': d *= -1j
            case 'R': d *=  1j
            case _:
                for _ in range(int(move)):
                    new_pos = pos + d
                    d_ = d
                    if new_pos not in grid:
                        new_pos, d_ = wrap(new_pos, d)
                    if grid[new_pos] == '.':
                        pos = new_pos
                        d = d_
                    else:
                        break
    return pos, d


def password(pos, d):
    x = pos.real + 1
    y = pos.imag + 1
    facing = {1: 0, 1j: 1, -1: 2, -1j: 3}[d]
    return int(4*x + 1000*y + facing)


def solve(filename=22):
    *field, _, path = read_input(filename)
    start = field[0].find('.')
    moves = re.split(pattern, path)
    grid = field2grid(field)
    p1 = traverse(start, moves, grid, wrap_1)
    p2 = traverse(start, moves, grid, wrap_2)
    return password(*p1), password(*p2)


print(solve())










def test():
    assert solve() == (13566, 11451)
