(ns day14
  (:require aoc
            [clojure.data.int-map :refer [dense-int-set]]))


(def ^:const start-x 500)
(def ^:const X 256)


(defn down  ^long [^long pt] (inc pt))
(defn left  ^long [^long pt] (- (inc pt) X))
(defn right ^long [^long pt] (+ (inc pt) X))

(defn drop-grain ^long [settled]
  (loop [pt (* X start-x)]
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
  (let [^long floor (reduce max (map #(mod % X) rocks))]
    (loop [settled rocks]
      (let [pt (drop-grain settled)]
        (if (= (mod pt X) (dec floor))
          (- (count settled) (count rocks))
          (recur (conj settled pt)))))))

(defn part-2 [rocks]
  (-> rocks
      (fill-to-full (* X start-x))
      count
      (- (count rocks))))


(defn path->rock [[ax ay] [bx by]]
  (for [x (range (min ax bx) (inc (max ax bx)))
        y (range (min ay by) (inc (max ay by)))]
    (+ (* X x) y)))

(defn add-floor [rocks]
  (let [floor-y (+ 2 (reduce max (map #(mod % X) rocks)))
        floor (for [x (range (- start-x floor-y) (+ start-x floor-y 1))] (+ (* X x) floor-y ))]
    (into rocks floor)))

(defn parse-input [input]
  (->> input
       aoc/read-input
       (into (dense-int-set)
             (comp
              (map aoc/integers)
              (map #(partition 2 %))      ; points
              (mapcat #(partition 2 1 %)) ; paths
              (mapcat #(apply path->rock %))))
       add-floor))

(defn solve
  ([] (solve 14))
  ([input]
   (let [rocks (parse-input input)]
     [(part-1 rocks)
      (part-2 rocks)])))


(solve)
