(ns day01
  (:require aoc))


(def calories
  (->> (aoc/read-input 1 :string #"\n\n")
       (map #(aoc/parse-multiline-string % :int))
       (map #(reduce + %))
       sort
       reverse))


(println (first calories))
(println (reduce + (take 3 calories)))
