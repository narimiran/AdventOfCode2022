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

(defn part-1 [sensors]
  (let [seen  (seen-in-row sensors 2000000)
        [a _] (first seen)
        [_ b] (peek seen)]
    (- b a)))

(defn part-2 [sensors]
  (loop [row 4000000]
    (let [edges (seen-in-row sensors row)
          col   (find-a-hole edges)]
      (if (pos? col)
        (+ (* 4000000 col) row)
        (recur (dec row))))))


(def sensors
  (->> 15
       aoc/read-input
       (map aoc/integers)
       (map find-radius)))


[(part-1 sensors)
 (part-2 sensors)]