(ns day14
  (:require aoc))


(def start-x 500)


(defn down  [[x y]] [x       (inc y)])
(defn left  [[x y]] [(dec x) (inc y)])
(defn right [[x y]] [(inc x) (inc y)])

(defn path->rock [[ax ay] [bx by]]
  (for [x (range (min ax bx) (inc (max ax bx)))
        y (range (min ay by) (inc (max ay by)))]
    [x y]))

(defn add-floor [rocks]
  (let [floor-y (+ 2 (reduce max (map second rocks)))
        floor (for [x (range (- start-x floor-y) (+ start-x floor-y 1))] [x floor-y])]
    (into rocks floor)))

(defn drop-grain [settled]
  (loop [pt [start-x 0]]
    (let [d (down pt)
          l (left pt)
          r (right pt)]
      (cond
        (not (settled d)) (recur d)
        (not (settled l)) (recur l)
        (not (settled r)) (recur r)
        :else pt))))

(defn fill-to-full [settled pt]
  (if (settled pt) settled
      (-> settled
          (fill-to-full (down pt))
          (fill-to-full (left pt))
          (fill-to-full (right pt))
          (conj pt))))

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
       (map #(partition 2 %))      ; points
       (mapcat #(partition 2 1 %)) ; paths
       (mapcat #(apply path->rock %))
       set
       add-floor))

(defn solve
  ([] (solve 14))
  ([input]
   (let [rocks (parse-input input)]
     [(part-1 rocks)
      (part-2 rocks)])))


(solve)
