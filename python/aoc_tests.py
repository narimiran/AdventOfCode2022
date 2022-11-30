import pytest
from aoc import *


def test():
    assert cat(["ab", "cd", "ef"]) == "abcdef"

    assert list(flatten([[1, 2], [3, 4]])) == [1, 2, 3, 4]
    assert list(flatten([[1, 2]])) == [1, 2]
    assert list(flatten(["1", "2"])) == ["1", "2"]

    assert mapl(int, ["1", "2"]) == [1, 2]
    assert mapl(str, range(3)) == ["0", "1", "2"]

    assert mapt(int, ["1", "2"]) == (1, 2)
    assert mapt(str, range(3)) == ("0", "1", "2")

    assert filterl(lambda x: x > 3, [1, 5, 2, 4, 3]) == [5, 4]
    assert filterl(lambda x: x > 3, []) == []
    assert filterl(lambda x: x == 'c', "acdc") == ["c", "c"]

    assert parse_multiline_string("ab\ncd") == ["ab", "cd"]
    assert parse_multiline_string("12\n34", int) == [12, 34]
    assert parse_multiline_string("ab\ncd\n\nef\ngh", sep='\n\n') == ["ab\ncd", "ef\ngh"]

    assert digits("123") == [1, 2, 3]
    with pytest.raises(ValueError):
        digits("1 2 3")

    assert integers("23 -42 55") == (23, -42, 55)
    assert integers("23 -42 55", negative=False) == (23, 42, 55)

    assert count_if([3, -5, 10, -7, 33], lambda x: x > 0) == 3
    assert count_if([3, -5, 10, -7, 33], lambda x: x > 1000) == 0

    assert first([2, 4, 6, 8]) == 2
    assert first([]) == None
    assert first([], 9) == 9
    assert first([2, 4, 6, 8], 9) == 2

    assert filter_first([2, 7, 4, 6, 8], lambda x: x > 5) == 7

    assert manhattan((5, -3)) == 8
    assert manhattan((5, -3), (2, 7)) == 13

    assert maxval(dict(a=3, b=99, c=66)) == 99
    assert maxval(dict(a=3, b=-99, c=66)) == 66

    assert tuple(neighbours(5, 7)) == ((5, 6),
                               (4, 7),         (6, 7),
                                       (5, 8))
    assert tuple(neighbours(5, 7, amount=8)) == ((4, 6), (5, 6), (6, 6),
                                                 (4, 7),         (6, 7),
                                                 (4, 8), (5, 8), (6, 8))
    assert tuple(neighbours(5, 7, amount=9)) == ((4, 6), (5, 6), (6, 6),
                                                 (4, 7), (5, 7), (6, 7),
                                                 (4, 8), (5, 8), (6, 8))

    assert list2grid([[10, 20], [30, 40]]) == {(0, 0): 10, (1, 0): 20,
                                               (0, 1): 30, (1, 1): 40}

    assert grid2list({(0, 1): 1, (1, 0): 1, (2, 0): 1}) == [[' ', '█', '█'],
                                                            ['█', ' ', ' ']]
    with pytest.raises(ValueError):
        grid2list({(0, 1): 1, (-1, 0): 1, (2, 0): 1})
