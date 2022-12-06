(ns day02
  (:require aoc))


(defn part-1 [input]
  (let [xa-diff (- (int \X) (int \A))
        shape-score
        (fn [pl]
          (inc (- (aoc/ord pl)
                  (int \X))))]
    (reduce
     (fn [acc [op _ pl]]
       (+ acc
          (shape-score pl)
          (case (- (aoc/ord pl)
                   (aoc/ord op)
                   xa-diff)
            (2 -1) 0
            0      3
            (1 -2) 6)))
     0
     input)))


(defn part-2 [input]
  (let [rps
        (fn [op]
          (- (aoc/ord op) (int \A)))]
    (reduce
     (fn [acc [op _ pl]]
       (+ acc
          (case (first pl)
            \X (+ 0 1 (mod (- (rps op) 1) 3))
            \Y (+ 3 1         (rps op))
            \Z (+ 6 1 (mod (+ (rps op) 1) 3)))))
     0
     input)))



(def input (aoc/read-input 2 {:datatype :list}))

[(part-1 input)
 (part-2 input)]
