(ns day09
  (:require aoc
            [clojure.string :as str]
            [clojure.math :as math]))


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
       {:rope    new-pos
        :seen-2  (conj seen-2  (second new-pos))
        :seen-10 (conj seen-10 (peek new-pos))}))
   {:rope (vec (repeat 10 [0 0]))
    :seen-2  #{}
    :seen-10 #{}}
   motions))

(defn solve [filename]
  (let [motions (mapcat parse-motion (aoc/read-input filename))
        rope (simulate motions)]
    [(count (:seen-2  rope))
     (count (:seen-10 rope))]))


(solve 9)
