(ns day04
  (:require aoc))


(defn fully-contain? [[a b c d]]
  (or (<= a c d b)
      (<= c a b d)))

(defn overlap? [[a b c d]]
  (or (<= c a d)
      (<= a c b)))

(defn parse-input [input]
  (->> (aoc/read-input input)
       (mapv #(aoc/integers % {:negative? false}))))

(defn solve
  ([] (solve 4))
  ([input]
   (let [assignments-pairs (parse-input input)]
     [(aoc/count-if fully-contain? assignments-pairs)
      (aoc/count-if overlap?       assignments-pairs)])))


(solve)
