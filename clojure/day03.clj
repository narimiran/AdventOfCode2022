(ns day03
  (:require aoc
            [clojure.set :as set]))


(def item-priority
  (zipmap "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
          (iterate inc 1)))


(defn split-rucksack [rucksack]
  (let [mid (/ (count rucksack) 2)]
    (split-at mid rucksack)))

(defn find-common-item [group]
  (->> group
       (map set)
       (reduce set/intersection)
       first
       item-priority))

(defn priority-sum [rucksacks split-func]
  (->> rucksacks
       split-func
       (pmap find-common-item)
       (reduce +)))

(def f1 #(map split-rucksack %))
(def f2 #(partition 3 %))

(defn solve
  ([] (solve (aoc/read-file 3)))
  ([input]
   (let [rucksacks (aoc/parse-input input)]
     [(priority-sum rucksacks f1)
      (priority-sum rucksacks f2)])))


(solve)
