(ns day08
  (:require aoc))


(defn viewing-distance [height dir]
  (->> dir
       (take-while #(> height %))
       count
       inc))

(defn go-through-forrest [height-map]
  (let [hor  height-map
        vert (aoc/transpose height-map)
        size (count height-map)]
    (for [x (range size)
          y (range size)]
      (let [row  (hor y)
            col  (vert x)
            dirs [(rseq (subvec row 0 x)) ; left
                  (subvec row (inc x))    ; right
                  (rseq (subvec col 0 y)) ; up
                  (subvec col (inc y))]   ; down
            dir-sizes (map count dirs)
            height    (row x)
            visible-distances (map #(viewing-distance height %) dirs)
            visible-trees     (map min visible-distances dir-sizes)]
        {:is-visible?  (some pos? (map - visible-distances dir-sizes))
         :scenic-score (reduce * visible-trees)}))))

(defn solve
  ([] (solve 8))
  ([input]
   (let [height-map (->> (aoc/read-input input)
                         (mapv aoc/string->digits))
         results (go-through-forrest height-map)]
     [(->> results (filter :is-visible?) count)
      (->> results (map :scenic-score) (reduce max))])))


(solve)
