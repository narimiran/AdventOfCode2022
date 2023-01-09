(ns day08
  (:require aoc))


(defn visible-from-outside? [lower-than-current? direction]
  (every? lower-than-current? direction))

(defn viewing-distance [lower-than-current? direction]
  (->> direction
       (take-while lower-than-current?)
       count
       inc
       (min (count direction))))

(defn go-through-forrest [height-map]
  (let [hor  height-map
        vert (aoc/transpose height-map)]
    (for [[y row] (map-indexed vector hor)
          [x col] (map-indexed vector vert)
          :let [dirs [(rseq (subvec row 0 x)) ; left
                      (subvec row (inc x))    ; right
                      (rseq (subvec col 0 y)) ; up
                      (subvec col (inc y))]   ; down
                is-lower? (partial > (row x))
                f (fn [agg func] (agg #(func is-lower? %) dirs))
                visible-directions  (f filter visible-from-outside?)
                visible-trees-count (f map viewing-distance)]]
      {:is-visible?  (not-empty visible-directions)
       :scenic-score (reduce * visible-trees-count)})))

(defn solve
  ([] (solve 8))
  ([input]
   (let [height-map (->> (aoc/read-input input)
                         (mapv aoc/string->digits))
         results (go-through-forrest height-map)]
     [(->> results (filter :is-visible?) count)
      (->> results (map :scenic-score) (reduce max))])))


(solve)
