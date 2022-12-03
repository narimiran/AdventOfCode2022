(ns day03
  (:require aoc
            [clojure.set :as sets]
            [clojure.string :as str]))


(def letters " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")
(defn item-priority [l] (str/index-of letters l))

(defn split-rucksack [rucksack]
  (let [mid (quot (count rucksack) 2)]
    (split-at mid rucksack)))


(defn find-common-item [group]
  (->> group
       (map set)
       (reduce sets/intersection)
       first))


(defn solve [rucksacks split-func]
  (->> rucksacks
       split-func
       (map find-common-item)
       (map item-priority)
       (reduce +)))


(def rucksacks (aoc/read-input 3))
(defn p1 [r] (map split-rucksack r))
(defn p2 [r] (partition 3 r))

[(solve rucksacks p1)
 (solve rucksacks p2)]
