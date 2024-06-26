(ns day06
  (:require aoc))


(defn process [buffer length]
  (reduce
   (fn [buffer pos]
     (if (->> buffer
              (take length)
              (apply distinct?))
       (reduced pos)
       (rest buffer)))
   buffer
   (iterate inc length)))

(defn solve
  ([] (solve (aoc/read-file 6)))
  ([input]
   (let [datastream-buffer (aoc/parse-input-line input)]
     [(process datastream-buffer 4)
      (process datastream-buffer 14)])))


(solve)
