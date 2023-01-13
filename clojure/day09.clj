(ns day09
  (:require aoc
            [clojure.data.int-map :refer [dense-int-set]]
            [clojure.math :as math]
            [clojure.string :as str]))




(defn parse-motion [line]
  (->> (str/split line #" ")
       ((fn [[dir amount]]
          (repeat (parse-long amount) (keyword dir))))))

(defn move-head [[x y] cmd]
  (case cmd
    :U [x (dec y)]
    :D [x (inc y)]
    :L [(dec x) y]
    :R [(inc x) y]))

(defn follow ^longs [[^long hx ^long hy] [^long tx ^long ty]]
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
  (let [hash-pos (fn ^long [[^long x ^long y]]
                   (+ x (* 234 y)))]
    (reduce
     (fn [[rope seen-2 seen-10] cmd]
       (let [new-pos (move-rope rope cmd)]
         [new-pos
          (conj! seen-2  (hash-pos (nth new-pos 1)))
          (conj! seen-10 (hash-pos (nth new-pos 9)))]))
     [(vec (repeat 10 [0 0]))
      (transient (dense-int-set))
      (transient (dense-int-set))]
     motions)))

(defn solve
  ([] (solve 9))
  ([input]
   (let [motions   (mapcat parse-motion (aoc/read-input input))
         [_ p1 p2] (simulate motions)]
     [(count p1)
      (count p2)])))


(solve)
