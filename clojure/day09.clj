(ns day09
  (:require aoc
            [clojure.data.int-map :refer [dense-int-set]]
            [clojure.math :as math]
            [clojure.string :as str]))


(def ^:const N 500)
(def ^:const start (* (inc N) (/ N 2)))


(defn parse-motion [line]
  (->> (str/split line #" ")
       ((fn [[dir amount]]
          (repeat (parse-long amount) (keyword dir))))))

(defn move-head [^long pt cmd]
  (case cmd
    :U (- pt N)
    :D (+ pt N)
    :L (dec pt)
    :R (inc pt)))

(defn follow ^long [^long head ^long tail]
  (let [hx (rem head N)
        hy (quot head N)
        tx (rem tail N)
        ty (quot tail N)
        dx (- hx tx)
        dy (- hy ty)]
    (if (< (max (abs dx) (abs dy)) 2)
      tail
      (+ tail (long (math/signum dx)) (* N (long (math/signum dy)))))))

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
   (fn [[rope seen-2 seen-10] cmd]
     (let [new-pos (move-rope rope cmd)]
       [new-pos
        (conj! seen-2  (nth new-pos 1))
        (conj! seen-10 (nth new-pos 9))]))
   [(vec (repeat 10 start))
    (transient (dense-int-set))
    (transient (dense-int-set))]
   motions))

(defn solve
  ([] (solve 9))
  ([input]
   (let [motions   (mapcat parse-motion (aoc/read-input input))
         [_ p1 p2] (simulate motions)]
     [(count p1)
      (count p2)])))


(solve)
