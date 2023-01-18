(ns day08
  (:require aoc))


(defn visible-from-outside? [^long height directions]
  (reduce
   (fn [acc dir]
     (if (every? #(< ^long % height) dir)
       (reduced true)
       acc))
   false
   directions))

(defn viewing-distance [^long height direction]
  (reduce
   (fn [^long acc ^long h]
     (if (< h height)
       (inc acc)
       (reduced (min (count direction) (inc acc)))))
   0
   direction))

(defn go-through-forrest [height-map]
  (let [hor  height-map
        vert (aoc/transpose height-map)]
    (for [[^long y row] (map-indexed vector hor)
          [^long x col] (map-indexed vector vert)
          :let [dirs [(rseq (subvec row 0 x)) ; left
                      (subvec row (inc x))    ; right
                      (rseq (subvec col 0 y)) ; up
                      (subvec col (inc y))]   ; down
                height (row x)]]
      {:is-visible?  (visible-from-outside? height dirs)
       :scenic-score (transduce (map #(viewing-distance height %)) * dirs)})))

(defn solve
  ([] (solve 8))
  ([input]
   (let [height-map (->> (aoc/read-input input)
                         (mapv aoc/string->digits))
         results (go-through-forrest height-map)]
     [(->> results (aoc/count-if :is-visible?))
      (->> results (map :scenic-score) (reduce max))])))


(solve)
