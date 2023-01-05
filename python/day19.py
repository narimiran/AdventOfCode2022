from aoc import *
from collections import namedtuple
from math import prod


Bots = namedtuple('Bots', ('ore', 'clay', 'obs', 'geode'))
Resources = namedtuple('Resources', ('ore', 'clay', 'obs', 'geode'))


def best_case(resource, bot, time):
    return resource + time*bot + time*(time+1)//2


def geodes(costs, t=24,
           bots=Bots(1, 0, 0, 0),
           resources=Resources(0, 0, 0, 0)):
    score = 0
    seen = set()
    ore_ore, clay_ore, obs_ore, obs_clay, geode_ore, geode_obs = costs
    max_ore = max(ore_ore, clay_ore, obs_ore, geode_ore)
    max_clay = obs_clay
    max_obs = geode_obs
    stack = [(t, bots, resources, set())]
    while stack:
        t, bots, resources, skipped = state = stack.pop()
        if (state[:-1] in seen
                or t == 0
                or best_case(resources.geode, bots.geode, t) < score):
            score = max(score, resources.geode)
            continue
        seen.add(state[:-1])
        t -= 1
        ore = min(max_ore*t, resources.ore + bots.ore)
        clay = min(max_clay*t, resources.clay + bots.clay)
        obs = min(max_obs*t, resources.obs + bots.obs)
        geode = resources.geode + bots.geode
        can_build = set()
        if (t > 0 and 'geode' not in skipped
            and resources.ore >= geode_ore and resources.obs >= geode_obs):
            can_build.add('geode')
            stack.append((t,
                          bots._replace(geode=bots.geode+1),
                          Resources(ore-geode_ore, clay, obs-geode_obs, geode),
                          set()))
            # continue   # faster with this, but people say it would be "cheating"
        if (t > 2 and 'obs' not in skipped
            and resources.ore >= obs_ore and resources.clay >= obs_clay
            and bots.obs < max_obs and resources.obs < 1.2*max_obs):
            can_build.add('obs')
            stack.append((t,
                          bots._replace(obs=bots.obs+1),
                          Resources(ore-obs_ore, clay-obs_clay, obs, geode),
                          set()))
        if (t > 4 and 'clay' not in skipped
            and resources.ore >= clay_ore
            and bots.clay < max_clay and resources.clay < 1.2*max_clay):
            can_build.add('clay')
            stack.append((t,
                          bots._replace(clay=bots.clay+1),
                          Resources(ore-clay_ore, clay, obs, geode),
                          set()))
        if ('ore' not in skipped and resources.ore >= ore_ore
            and bots.ore < max_ore and resources.ore < 1.5*max_ore):
            can_build.add('ore')
            stack.append((t,
                          bots._replace(ore=bots.ore+1),
                          Resources(ore-ore_ore, clay, obs, geode),
                          set()))
        if (resources.ore < max_ore or (bots.clay and resources.clay < max_clay)
            or (bots.obs and resources.obs < max_obs)):
            stack.append((t,
                          bots,
                          Resources(ore, clay, obs, geode),
                          can_build))
    return score


def part_1(blueprints):
    return sum(bp * geodes(costs)
               for bp, *costs in blueprints)


def part_2(blueprints):
    return prod(geodes(costs, 32)
                for _, *costs in blueprints[:3])


def solve(filename=19):
    blueprints = mapl(integers, read_input(filename))
    return part_1(blueprints), part_2(blueprints)


print(solve())










def test():
    assert solve("19_test") == (33, 3472)
    assert solve() == (994, 15960)
