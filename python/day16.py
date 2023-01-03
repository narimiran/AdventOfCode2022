from aoc import *
import re
from collections import defaultdict
from itertools import product, combinations


pattern = re.compile(r"[A-Z]{2}|\d+")


def parse_line(line):
    valve, flow, *connections = re.findall(pattern, line)
    return valve, int(flow), connections

def create_connections(data):
    valves = set()
    flows = dict()
    connections = defaultdict(lambda: inf)
    for valve, flow, conns in data:
        valves.add(valve)
        if flow or valve == 'AA':
            flows[valve] = flow
        for conn in conns:
            connections[valve, conn] = 1
    for a, b, c in product(valves, repeat=3): # Floyd-Warshall
        if a != b != c != a:
            connections[b, c] = min(connections[b, c],
                                    connections[b, a] + connections[a, c])
    return flows, connections


def score(flows, attempt):
    return sum(flows[valve] * time for valve, time in attempt.items())


def find_best_scores(flows, attempts):
    top_scores = defaultdict(int)
    for attempt in attempts:
        k = frozenset(attempt)
        v = score(flows, attempt)
        top_scores[k] = max(top_scores[k], v)
    return top_scores


def best_tandem_score(flows, attempts):
    top_scores = find_best_scores(flows, attempts)
    return max(h_score + e_score
               for (h_vis, h_score), (e_vis, e_score) in combinations(top_scores.items(), 2)
               if not h_vis & e_vis)


def traverse(time, closed_valves, conns, curr='AA', visited=dict()):
    for valve in closed_valves:
        if (t := time - conns[curr, valve] - 1) > 1:
            yield from traverse(t, closed_valves - {valve}, conns, valve, visited | {valve: t})
    yield visited


def solve(filename=16):
    flows, conns  = create_connections(map(parse_line, read_input(filename)))
    closed_valves = set(flows.keys())
    p1 = max(score(flows, attempt) for attempt in traverse(30, closed_valves, conns))
    p2 = best_tandem_score(flows, traverse(26, closed_valves, conns))
    return p1, p2


print(solve())










def test():
    assert solve("16_test") == (1651, 1707)
    assert solve() == (1880, 2520)
