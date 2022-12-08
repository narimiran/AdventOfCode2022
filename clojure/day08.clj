(ns day08
  (:require aoc))


(defn viewing-distance [height dir]
  (->> dir
       (take-while #(> height %))
       count
       inc
       (min (count dir))))


(defn solve [forest]
  (let [hor forest
        vert (aoc/transpose forest)
        size (count forest)]
    (reduce
     (fn [[visible score] [x y]]
       (if (some #{0 (dec size)} [x y]) ; edges
         [(inc visible) score]
         (let [row    (hor y)
               col    (vert x)
               height (row x)
               dirs   [(reverse (subvec row 0 x)) ; left
                       (subvec row (inc x))       ; right
                       (reverse (subvec col 0 y)) ; up
                       (subvec col (inc y))]]     ; down
           [(if (some #(> height (apply max %)) dirs)
              (inc visible)
              visible)
            (max score (apply * (map #(viewing-distance height %) dirs)))])))
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
