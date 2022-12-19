(ns day14
  (:require aoc))


(def start-x 500)


(defn path->rock [[ax ay] [bx by]]
  (for [x (range (min ax bx) (inc (max ax bx)))
        y (range (min ay by) (inc (max ay by)))]
    [x y]))

(defn add-floor [rocks]
  (let [floor-y (+ 2 (reduce max (map second rocks)))
        floor (for [x (range (- start-x floor-y) (+ start-x floor-y 1))] [x floor-y])]
    (reduce conj rocks floor)))

(defn drop-grain [settled]
  (loop [[x y] [start-x 0]]
    (let [down  [x       (inc y)]
          left  [(dec x) (inc y)]
          right [(inc x) (inc y)]]
      (cond
        (not (settled down))  (recur down)
        (not (settled left))  (recur left)
        (not (settled right)) (recur right)
        :else [x y]))))

(defn fill-to-full [settled [x y]]
  (if (settled [x y]) settled
      (-> settled
          (fill-to-full [x       (inc y)])
          (fill-to-full [(dec x) (inc y)])
          (fill-to-full [(inc x) (inc y)])
          (conj [x y]))))

(defn part-1 [rocks]
  (let [floor (reduce max (map second rocks))]
    (loop [settled rocks]
      (let [[x y] (drop-grain settled)]
        (if (= y (dec floor))
          (- (count settled) (count rocks))
          (recur (conj settled [x y])))))))

(defn part-2 [rocks]
  (-> rocks
      (fill-to-full [start-x 0])
      count
      (- (count rocks))))

(defn parse-input [input]
  (->> input
       aoc/read-input
       (map aoc/integers)
       (map #(partition 2 %))   ; points
       (map #(partition 2 1 %)) ; paths
       (apply concat)
       (map #(apply path->rock %))
       (apply concat)
       set
       add-floor))

(defn solve
  ([] (solve 14))
  ([input]
   (let [rocks (parse-input input)]
     [(part-1 rocks)
      (part-2 rocks)])))


(solve)
