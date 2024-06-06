(ns day02
  (:require aoc
            [clojure.set :as set]))


(defn parse-shape [v]
  (case v
    (\A \X) :rock
    (\B \Y) :paper
    (\C \Z) :scissors))

(defn parse-outcome [v]
  (case v
    \X :lose
    \Y :draw
    \Z :win))

(def wins {:rock     :scissors
           :paper    :rock
           :scissors :paper})

(def loses (set/map-invert wins))

(def points {:rock 1 :paper 2 :scissors 3
             :lose 0 :draw 3 :win 6})

(defn p1-rules [line]
  (let [[op pl] (map parse-shape line)
        outcome (condp = op
                  pl :draw
                  (pl wins) :win
                  :lose)]
    (+ (points pl)
       (points outcome))))

(defn p2-rules [[op' outcome']]
  (let [op      (parse-shape op')
        outcome (parse-outcome outcome')
        pl      (case outcome
                  :draw op
                  :lose (op wins)
                  :win  (op loses))]
    (+ (points pl)
       (points outcome))))

(defn play [rules guide]
  (transduce (map rules) + guide))

(defn solve
  ([] (solve (aoc/read-file 2)))
  ([input]
   (let [strategy-guide (aoc/parse-input input #(take-nth 2 %))]
     [(play p1-rules strategy-guide)
      (play p2-rules strategy-guide)])))


(solve)
