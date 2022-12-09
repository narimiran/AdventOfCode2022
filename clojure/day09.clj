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
         snake [head]]
    (if (empty? current) snake
        (recur remaining
               (->> current
                    (follow (peek snake))
                    (conj snake))))))


(defn move-snake [[head & tail] cmd]
  (-> head
      (move-head cmd)
      (move-tail tail)))


(defn play [commands length]
  (loop [[cmd & rem] commands
         snake (vec (repeat length [0 0]))
         seen #{}]
    (if (empty? cmd) (count (conj seen (peek snake)))
        (recur rem
               (move-snake snake cmd)
               (conj seen (peek snake))))))


(def commands
  (->> 9
       aoc/read-input
       (mapcat parse-line)))


[(play commands 2)
 (play commands 10)]
