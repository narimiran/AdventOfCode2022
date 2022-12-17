(ns day01
  (:require aoc))


(defn parse-input [filename]
  (->> (aoc/read-input-paragraphs filename :int)
       (map #(reduce + %))
       (sort >)))

(defn solve [filename]
  (let [calories (parse-input filename)]
    [(first calories)
     (reduce + (take 3 calories))]))


(solve 1)
