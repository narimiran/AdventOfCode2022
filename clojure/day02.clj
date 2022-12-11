(ns day02
  (:require aoc))


(defn part-1 [guide]
  (let [xa-diff     (- (int \X) (int \A))
        shape-score (fn [pl]
                      (inc (- (aoc/ord pl)
                              (int \X))))]
    (reduce
     (fn [acc [op _ pl]]
       (+ acc
          (shape-score pl)
          (case (- (aoc/ord pl) (aoc/ord op) xa-diff)
            (2 -1) 0
            0      3
            (1 -2) 6)))
     0
     guide)))

(defn part-2 [guide]
  (let [rps (fn [op]
              (- (aoc/ord op) (int \A)))]
    (reduce
     (fn [acc [op _ pl]]
       (+ acc
          (case (first pl)
            \X (+ 0 1 (mod (dec (rps op)) 3))
            \Y (+ 3 1           (rps op))
            \Z (+ 6 1 (mod (inc (rps op)) 3)))))
     0
     guide)))


(def strategy-guide (aoc/read-input 2 {:datatype :list}))

[(part-1 strategy-guide)
 (part-2 strategy-guide)]
