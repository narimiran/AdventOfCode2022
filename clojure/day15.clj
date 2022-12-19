(ns day15
  (:require aoc))


(defn find-radius [[sx sy bx by]]
  [[sx sy] (aoc/manhattan [sx sy] [bx by])])

(defn seen-in-row [sensors row]
  (->> sensors
       (reduce
        (fn [edges [[sx sy] r]]
          (let [diff (- r (abs (- row sy)))]
            (if (pos? diff)
              (conj edges [(- sx diff) (+ sx diff)])
              edges)))
        [])
       sort
       vec))

(defn find-a-hole [edges]
  (let [val
        (reduce
         (fn [highest [a b]]
           (if (<= a (inc highest))
             (max b highest)
             (reduced (dec a))))
         0
         edges)]
    (if (< val (first (peek edges))) val -1)))

(defn part-1 [sensors row]
  (let [seen  (seen-in-row sensors row)
        [a _] (first seen)
        [_ b] (peek seen)]
    (- b a)))

(defn part-2 [sensors limit]
  (loop [row limit]
    (let [edges (seen-in-row sensors row)
          col   (find-a-hole edges)]
      (if (pos? col)
        (+ (* 4000000 col) row)
        (recur (dec row))))))

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
     [(part-1 sensors (quot limit 2))
      (part-2 sensors limit)])))


(solve)
