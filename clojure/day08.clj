(ns day08
  (:require aoc))


(defn viewing-distance [height dir]
  (->> dir
       (take-while #(> height %))
       count
       inc))

(defn go-through-grid [forest]
  (let [hor    forest
        vert   (aoc/transpose forest)
        size   (count forest)
        to-int {true 1 nil 0}]
    (reduce
     (fn [[visible-from-outside high-score] [x y]]
       (let [row    (hor y)
             col    (vert x)
             height (row x)
             dirs   [(rseq (subvec row 0 x)) ; left
                     (subvec row (inc x))    ; right
                     (rseq (subvec col 0 y)) ; up
                     (subvec col (inc y))]   ; down
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

(defn solve [filename]
  (let [height-map
        (mapv aoc/string->digits (aoc/read-input filename))]
    (go-through-grid height-map)))


(solve 8)
