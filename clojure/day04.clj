(ns day04
  (:require aoc))


(defn fully-contain? [[a b c d]]
  (or (<= a c d b)
      (<= c a b d)))

(defn overlap? [[a b c d]]
  (or (<= c a d)
      (<= a c b)))

(defn parse-input [filename]
  (->> (aoc/read-input filename)
       (mapv #(aoc/integers % {:negative? false}))))

(defn solve [filename]
  (let [assignments-pairs (parse-input filename)]
    [(count (filter fully-contain? assignments-pairs))
     (count (filter overlap?       assignments-pairs))]))


(solve 4)
