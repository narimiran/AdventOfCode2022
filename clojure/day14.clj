(ns day14
  (:require aoc))


(defn path->rock [[ax ay] [bx by]]
  (for [x (range (min ax bx) (inc (max ax bx)))
        y (range (min ay by) (inc (max ay by)))]
    [x y]))

(defn pour-sand [settled]
  (loop [[x y] [500 0]]
    (let [down  [x       (inc y)]
          left  [(dec x) (inc y)]
          right [(inc x) (inc y)]]
      (cond
        (not (settled down))  (recur down)
        (not (settled left))  (recur left)
        (not (settled right)) (recur right)
        :else [x y]))))

(defn simulate [rocks part]
  (let [start      500
        floor      (+ 2 (apply max (map second rocks)))
        floor-pts  (for [x (range (- start floor) (+ start floor 1))] [x floor])
        rocks      (apply merge rocks floor-pts)
        count-sand (fn [settled] (- (count settled) (count rocks) (- 1 part)))]
    (loop [settled rocks]
      (let [[x y] (pour-sand settled)]
        (if (= y (if (= part 1) (dec floor) 0))
          (count-sand settled)
          (recur (conj settled [x y])))))))


(def rocks
  (->> 14
       aoc/read-input
       (map aoc/integers)
       (map #(partition 2 %))   ; points
       (map #(partition 2 1 %)) ; paths
       (apply concat)
       (map #(apply path->rock %))
       (apply concat)
       set))

[(simulate rocks 1)
 (simulate rocks 2)]
