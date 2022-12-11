(ns day04
  (:require aoc))


(defn p1 [[a b c d]]
  (or (<= a c d b)
      (<= c a b d)))

(defn p2 [[a b c d]]
  (not (or (> a d) (< b c))))

(defn solve [IDs overlap]
  (->> IDs
       (filter overlap)
       count))


(def assignments
  (->> (aoc/read-input 4)
       (map #(aoc/integers % {:negative? false}))))

[(solve assignments p1)
 (solve assignments p2)]
