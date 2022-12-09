(ns day09
  (:require aoc
            [clojure.string :as str]))


(defn parse-line [line]
  (->> line
       (#(str/split % #" "))
       ((fn [[dir amount]]
          (repeat (Integer/parseInt amount) dir)))))


(defn move-head [head cmd]
  (let [dirs {"U" [0 -1]
              "D" [0  1]
              "L" [-1 0]
              "R" [1  0]}]
    (mapv + head (dirs cmd))))


(defn follow [[hx hy] [tx ty]]
  (let [dx (- hx tx)
        dy (- hy ty)]
    (if (< (max (abs dx) (abs dy)) 2) ; Chebyshev distance
      [tx ty]
      [(+ tx (aoc/sign dx)) (+ ty (aoc/sign dy))])))


(defn move-tail [head tail]
  (loop [[current & remaining] tail
         rope [head]]
    (if (empty? current) rope
        (recur remaining
               (->> current
                    (follow (peek rope))
                    (conj rope))))))


(defn move-rope [[head & tail] cmd]
  (-> head
      (move-head cmd)
      (move-tail tail)))


(defn simulate [motions length]
  (loop [[cmd & rem] motions
         rope (vec (repeat length [0 0]))
         seen #{}]
    (if (empty? cmd) (count (conj seen (peek rope)))
        (recur rem
               (move-rope rope cmd)
               (conj seen (peek rope))))))


(def motions
  (->> 9
       aoc/read-input
       (mapcat parse-line)))


[(simulate motions 2)
 (simulate motions 10)]
