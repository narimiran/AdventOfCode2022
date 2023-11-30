# Advent of Code 2022

All my Advent of Code repos:

* [AoC 2015 in Nim, Python](https://github.com/narimiran/advent_of_code_2015)
* [AoC 2016 in Python, Clojure](https://github.com/narimiran/advent_of_code_2016)
* [AoC 2017 in Nim, OCaml, Python](https://github.com/narimiran/AdventOfCode2017)
* [AoC 2018 in Nim, Python, Racket](https://github.com/narimiran/AdventOfCode2018)
* [AoC 2019 in OCaml, Python](https://github.com/narimiran/AdventOfCode2019)
* [AoC 2020 in Nim, one liner-y Python, Racket](https://github.com/narimiran/AdventOfCode2020)
* [AoC 2021 in Python, Racket](https://github.com/narimiran/AdventOfCode2021)
* [AoC 2022 in Python, Clojure](https://github.com/narimiran/AdventOfCode2022) (this repo)
* [AoC 2023 in Clojure](https://github.com/narimiran/AdventOfCode2023)


&nbsp;

Last year's Python was fun, so I'll keep it as my main language this year.

But to keep things interesting and (too?) challenging, I'll make my first learning steps in Clojure.
If you have any advice how to improve my Clojure solutions, please open an issue or comment in any other way.



## Solutions


Task                                          | Python Solution             | Clojure Solution               | Comment for Python solution
---                                           | ---                         | ---                            | ---
Day 00: Helper file                           | [aoc.py](python/aoc.py)     | [aoc.clj](clojure/aoc.clj)     | Utilities I use to solve the tasks.
[Day 01](http://adventofcode.com/2022/day/1)  | [day01.py](python/day01.py) | [day01.clj](clojure/day01.clj) | Unexpected double-newline input for the first task.
[Day 02](http://adventofcode.com/2022/day/2)  | [day02.py](python/day02.py) | [day02.clj](clojure/day02.clj) | Using `match` statement (Python 3.10+).
[Day 03](http://adventofcode.com/2022/day/3)  | [day03.py](python/day03.py) | [day03.clj](clojure/day03.clj) | I like my Clojure solution more.
[Day 04](http://adventofcode.com/2022/day/4)  | [day04.py](python/day04.py) | [day04.clj](clojure/day04.clj) | Look ma, no sets!
[Day 05](http://adventofcode.com/2022/day/5)  | [day05.py](python/day05.py) | [day05.clj](clojure/day05.clj) | Parsing the input like a real man.
[Day 06](http://adventofcode.com/2022/day/6)  | [day06.py](python/day06.py) | [day06.clj](clojure/day06.clj) | The easiest one so far.
[Day 07](http://adventofcode.com/2022/day/7)  | [day07.py](python/day07.py) | [day07.clj](clojure/day07.clj) | The toughest one so far.
[Day 08](http://adventofcode.com/2022/day/8)  | [day08.py](python/day08.py) | [day08.clj](clojure/day08.clj) | Look pa, no dicts!
[Day 09](http://adventofcode.com/2022/day/9)  | [day09.py](python/day09.py) | [day09.clj](clojure/day09.clj) | Complex numbers --> simple solution.
[Day 10](http://adventofcode.com/2022/day/10) | [day10.py](python/day10.py) | [day10.clj](clojure/day10.clj) | Advent of off-by-one errors.
[Day 11](http://adventofcode.com/2022/day/11) | [day11.py](python/day11.py) | [day11.clj](clojure/day11.clj) | Classy monkeys.
[Day 12](http://adventofcode.com/2022/day/12) | [day12.py](python/day12.py) | [day12.clj](clojure/day12.clj) | Start from the end, obviously.
[Day 13](http://adventofcode.com/2022/day/13) | [day13.py](python/day13.py) | [day13.clj](clojure/day13.clj) | `eval` + pattern matching on types.
[Day 14](http://adventofcode.com/2022/day/14) | [day14.py](python/day14.py) | [day14.clj](clojure/day14.clj) | Both parts in one go.
[Day 15](http://adventofcode.com/2022/day/15) | [day15.py](python/day15.py) | [day15.clj](clojure/day15.clj) | Scan row-by-row for a hole in the scanned positions.
[Day 16](http://adventofcode.com/2022/day/16) | [day16.py](python/day16.py) | [day16.clj](clojure/day16.clj) | Floyd-Warshall and only traverse through the valves with positive flow.
[Day 17](http://adventofcode.com/2022/day/17) | [day17.py](python/day17.py) | [day17.clj](clojure/day17.clj) | The traditional "find a cycle" task.
[Day 18](http://adventofcode.com/2022/day/18) | [day18.py](python/day18.py) | [day18.clj](clojure/day18.clj) | Cubes are just points in 3D.
[Day 19](http://adventofcode.com/2022/day/19) | [day19.py](python/day19.py) | [day19.clj](clojure/day19.clj) | Ungh.
[Day 20](http://adventofcode.com/2022/day/20) | [day20.py](python/day20.py) | [day20.clj](clojure/day20.clj) | The traditional "just use `deque.rotate`" task.
[Day 21](http://adventofcode.com/2022/day/21) | [day21.py](python/day21.py) | [day21.clj](clojure/day21.clj) | DFS and binary search.
[Day 22](http://adventofcode.com/2022/day/22) | [day22.py](python/day22.py) | [day22.clj](clojure/day22.clj) | Hardcoded the wrapping rules for my input. Sorry, not sorry.
[Day 23](http://adventofcode.com/2022/day/23) | [day23.py](python/day23.py) | [day23.clj](clojure/day23.clj) | ~~More complex numbers.~~ Plain numbers are much faster.
[Day 24](http://adventofcode.com/2022/day/24) | [day24.py](python/day24.py) | [day24.clj](clojure/day24.clj) | Set operations for the win.
[Day 25](http://adventofcode.com/2022/day/25) | [day25.py](python/day25.py) | [day25.clj](clojure/day25.clj) | `+/- 2` for an easy conversion.

