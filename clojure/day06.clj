(ns day06
  (:require aoc))


(defn solve [buffer length]
  (loop [pos    length
         buffer buffer]
    (if (->> buffer
             (take length)
             (apply distinct?))
      pos
      (recur (inc pos) (rest buffer)))))


(def datastream-buffer (aoc/read-input-line 6))

[(solve datastream-buffer 4)
 (solve datastream-buffer 14)]
