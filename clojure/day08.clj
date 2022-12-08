(ns day08
  (:require aoc))


(defn viewing-distance [height dir]
  (->> dir
       (take-while #(> height %))
       count
       inc))


(defn solve [forest]
  (let [hor    forest
        vert   (aoc/transpose forest)
        size   (count forest)
        to-int {true 1 nil 0}]
    (reduce
     (fn [[visible-from-outside high-score] [x y]]
       (let [row    (hor y)
             col    (vert x)
             height (row x)
             dirs   [(reverse (subvec row 0 x)) ; left
                     (subvec row (inc x))       ; right
                     (reverse (subvec col 0 y)) ; up
                     (subvec col (inc y))]      ; down
             visible-distances (map #(viewing-distance height %) dirs)
             dir-sizes         (map count dirs)
             visible-trees     (map min visible-distances dir-sizes)]
         [(+ visible-from-outside
             (to-int (some pos? (map - visible-distances dir-sizes))))
          (max high-score (apply * visible-trees))]))
     [0 0]
     (for [x (range size)
           y (range size)]
       [x y]))))


(def input
  (->> 8
       aoc/read-input
       (map aoc/string->digits)
       vec))


(solve input)
