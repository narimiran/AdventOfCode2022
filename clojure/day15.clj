(ns day15
  (:require aoc))


(defn find-radius [[sx sy bx by]]
  [[sx sy] (aoc/manhattan [sx sy] [bx by])])

(defn seen-in-row [sensors row]
  (->> sensors
       (mapcat (fn [[[sx sy] r]]
                 (let [diff (- r (abs (- row sy)))]
                   (when (pos? diff)
                     [(- sx diff) (+ sx diff)]))))
       (remove nil?)
       sort
       vec))

(defn part-1 [sensors row]
  (let [seen (seen-in-row sensors row)]
    (- (peek seen) (first seen))))



(defn find-coeffs [[[sx sy] r]]
  [[(+ (- sy sx) (inc r))
    (- (- sy sx) (inc r))]
   [(+ (+ sy sx) (inc r))
    (- (+ sy sx) (inc r))]])

(defn extract-useful [coeffs a-or-b]
  (->> coeffs
       (mapcat a-or-b)
       frequencies
       (filter #(> (val %) 1))
       keys))

(defn found-beacon? [sensors location]
  (not-any?
   (fn [[sensor r]]
     (<= (aoc/manhattan sensor location) r))
   sensors))

(defn calc-score [[col row]]
  (+ (* 4000000 col) row))

(defn part-2 [sensors limit]
  (let [coeffs     (map find-coeffs sensors)
        pos-coeffs (extract-useful coeffs first)
        neg-coeffs (extract-useful coeffs second)
        potential-locations
        (for [a pos-coeffs
              b neg-coeffs
              :when (and (> b a) (zero? (mod (- b a) 2)))
              :let [x (/ (- b a) 2)
                    y (/ (+ b a) 2)]
              :when (and (< 0 x limit) (< 0 y limit))]
          [x y])]
    (->> potential-locations
         (aoc/find-first (partial found-beacon? sensors))
         calc-score)))



(defn solve
  ([] (solve 15))
  ([input & [limit]]
   (let [limit   (if (= (str input) "15")
                   4000000
                   (or limit 20))
         sensors (->> input
                      aoc/read-input
                      (map aoc/integers)
                      (map find-radius))]
     [(part-1 sensors (/ limit 2))
      (part-2 sensors limit)])))


(solve)
