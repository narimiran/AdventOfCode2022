(ns day08
  (:require aoc))


(defn visible-from-outside? [lower-than-current? directions]
  (reduce
   (fn [acc dir]
     (if (every? lower-than-current? dir)
       (reduced true)
       acc))
   false
   directions))

(defn viewing-distance [lower-than-current? direction]
  (->> direction
       (take-while lower-than-current?)
       count
       inc
       (min (count direction))))

(defn go-through-forrest [height-map]
  (let [hor  height-map
        vert (aoc/transpose height-map)]
    (for [[^long y row] (map-indexed vector hor)
          [^long x col] (map-indexed vector vert)
          :let [dirs [(rseq (subvec row 0 x)) ; left
                      (subvec row (inc x))    ; right
                      (rseq (subvec col 0 y)) ; up
                      (subvec col (inc y))]   ; down
                lower-than-current? #(< ^long % ^long (row x))
                visible-trees-count (map #(viewing-distance lower-than-current? %) dirs)]]
      {:is-visible?  (visible-from-outside? lower-than-current? dirs)
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
