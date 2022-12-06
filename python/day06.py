from aoc import *


def find(data, length):
    return next(pos for pos in range(length, len(data))
                if len(set(data[pos-length:pos])) == length)


def solve(file=6):
    data = read_input_line(file)
    return find(data, 4), find(data, 14)


print(solve())










def test():
    test_strings = ("mjqjpqmgbljsphdztnvjfqwrcgsmlb",
                    "bvwbjplbgvbhsrlpgdmjqwftvncz",
                    "nppdvjthqldpwncqszvftbrmjlhg",
                    "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
                    "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw")
    p1_solutions = (7, 5, 6, 10, 11)
    p2_solutions = (19, 23, 23, 29, 26)
    for test, solution in zip(test_strings, p1_solutions):
        assert find(test, 4) == solution
    for test, solution in zip(test_strings, p2_solutions):
        assert find(test, 14) == solution
    assert solve() == (1287, 3716)
