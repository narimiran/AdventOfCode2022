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

(defn solve [filename]
  (let [datastream-buffer (aoc/read-input-line filename)]
    [(process datastream-buffer 4)
     (process datastream-buffer 14)]))


(solve 6)
