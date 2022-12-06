(ns day06
  (:require aoc))


(defn solve
  ([input length]
   (solve input length length))
  ([input length i]
   (let [chars (take length input)]
     (if (apply distinct? chars)
       i
       (solve (rest input) length (inc i))))))


(def input (aoc/read-input-line 6))

[(solve input 4)
 (solve input 14)]
