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

(defn priority-sum [rucksacks split-func]
  (->> rucksacks
       split-func
       (transduce
        (comp
         (map find-common-item)
         (map item-priority))
        +)))

(def p1 #(map split-rucksack %))
(def p2 #(partition 3 %))

(defn solve [filename]
  (let [rucksacks (aoc/read-input filename)]
    [(priority-sum rucksacks p1)
     (priority-sum rucksacks p2)]))


(solve 3)
