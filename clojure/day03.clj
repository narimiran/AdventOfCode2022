(ns day03
  (:require aoc
            [clojure.data.int-map :as i]))


(def item-priority
  (zipmap "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
          (iterate inc 1)))


(defn split-rucksack [rucksack]
  (let [mid (/ (count rucksack) 2)]
    (split-at mid rucksack)))

(defn find-common-item [group]
  (->> group
       (map i/dense-int-set)
       (reduce i/intersection)
       first))

(defn priority-sum [rucksacks split-func]
  (->> rucksacks
       split-func
       (pmap find-common-item)
       (reduce +)))

(def f1 #(map split-rucksack %))
(def f2 #(partition 3 %))

(defn solve
  ([] (solve 3))
  ([input]
   (let [rucksacks (->> input
                        aoc/read-input
                        (map #(map item-priority %)))
         p1 (future (priority-sum rucksacks f1))
         p2 (future (priority-sum rucksacks f2))]
     [@p1 @p2])))


(solve)
