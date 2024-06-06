(ns day01
  (:require aoc))


(defn parse-input [input]
  (->> (aoc/parse-input-paragraphs input :int)
       (map #(reduce + %))
       (sort >)))

(defn solve
  ([] (solve (aoc/read-file 1)))
  ([input]
   (let [calories (parse-input input)]
     [(first calories)
      (reduce + (take 3 calories))])))


(solve)
