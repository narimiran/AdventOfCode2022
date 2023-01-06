(ns day09
  (:require aoc
            [clojure.string :as str]
            [clojure.math :as math]))


(defrecord Rope [rope seen-2 seen-10])


(defn parse-motion [line]
  (->> (str/split line #" ")
       ((fn [[dir amount]]
          (repeat (parse-long amount) dir)))))

(defn move-head [[x y] cmd]
  (case cmd
    "U" [x (dec y)]
    "D" [x (inc y)]
    "L" [(dec x) y]
    "R" [(inc x) y]))

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

(defn simulate [motions]
  (reduce
   (fn [{:keys [rope seen-2 seen-10]} cmd]
     (let [new-pos (move-rope rope cmd)]
       (->Rope new-pos
               (conj seen-2  (second new-pos))
               (conj seen-10 (peek new-pos)))))
   (->Rope (vec (repeat 10 [0 0])) #{} #{})
   motions))

(defn solve
  ([] (solve 9))
  ([input]
   (let [motions (mapcat parse-motion (aoc/read-input input))
         rope (simulate motions)]
     [(count (:seen-2  rope))
      (count (:seen-10 rope))])))


(solve)
