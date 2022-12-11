(ns day09
  (:require aoc
            [clojure.string :as str]
            [clojure.math :as math]))


(defn parse-motion [line]
  (->> line
       (#(str/split % #" "))
       ((fn [[dir amount]]
          (repeat (parse-long amount) dir)))))

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
      [(+ tx (math/signum dx)) (+ ty (math/signum dy))])))

(defn move-tail [head tail]
  (reduce
   (fn [rope tail-piece]
     (->> tail-piece
          (follow (peek rope))
          (conj rope)))
   [head]
   tail))

(defn move-rope [[head & tail] cmd]
  (-> head
      (move-head cmd)
      (move-tail tail)))

(defn simulate [motions length]
  (->> motions
       (reduce
        (fn [{:keys [rope seen]} cmd]
          (let [new-pos (move-rope rope cmd)]
            {:rope new-pos
             :seen (conj seen (peek new-pos))}))
        {:rope (vec (repeat length [0 0]))
         :seen #{}})
       :seen
       count))


(def motions
  (->> 9
       aoc/read-input
       (mapcat parse-motion)))

[(simulate motions 2)
 (simulate motions 10)]
