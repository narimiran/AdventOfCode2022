(ns day03
  (:require aoc
            [clojure.data.int-map :as i]))


(defn item-priority [l]
  (let [i (int l)]
    (if (> i 96) (- i 96) (- i 38))))

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
       (map find-common-item)
       (apply +)))

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
