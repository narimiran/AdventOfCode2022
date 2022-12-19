(ns day06
  (:require aoc))


(defn process [buffer length]
  (loop [pos    length
         buffer buffer]
    (if (->> buffer
             (take length)
             (apply distinct?))
      pos
      (recur (inc pos) (rest buffer)))))

(defn solve
  ([] (solve 6))
  ([input]
   (let [datastream-buffer (aoc/read-input-line input)]
     [(process datastream-buffer 4)
      (process datastream-buffer 14)])))


(solve)
