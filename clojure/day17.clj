(ns day17
  (:require aoc))


(defrecord Point [x y])


(defmacro ->Rock [pts]
  `(map (fn [[x# y#]] (->Point x# y#)) ~pts))

(def rocks
  [(->Rock [[0 0] [1 0] [2 0] [3 0]])
   (->Rock [[1 0] [0 1] [1 1] [2 1] [1 2]])
   (->Rock [[0 0] [1 0] [2 0] [2 1] [2 2]])
   (->Rock [[0 0] [0 1] [0 2] [0 3]])
   (->Rock [[0 0] [1 0] [0 1] [1 1]])])

(defn move-left  [{:keys [x y]}] (->Point (dec x) y))
(defn move-right [{:keys [x y]}] (->Point (inc x) y))
(defn move-down  [{:keys [x y]}] (->Point x (dec y)))

(defn pt+ [a b]
  (->Point (+ (:x a) (:x b)) (+ (:y a) (:y b))))

(defn inbounds? [rock]
  (reduce
   (fn [acc x]
     (if-not (<= 0 x 6) (reduced false) acc))
   true
   (map :x rock)))

(defn peaks-hash [tower max-y]
  (transduce
   (map (fn [{:keys [x y]}]
      (if (= y max-y) (* x x) 0)))
   +
   tower))


(defn play [movements rounds]
  (let [M (count movements)
        R (count rocks)
        tower (set (for [x (range 7)] (->Point x 0)))]
    (loop [[r m max-y skipped seen tower]
           [0 0 0 0 (transient {}) tower]]
      (if (>= r rounds) (+ max-y skipped)
          (let [init-pos (->Point 2 (+ max-y 4))
                rock     (mapv (partial pt+ init-pos) (nth rocks (mod r R)))]
            (recur
             (loop [rock rock
                    m    (long m)]
               (let [move-fn    (if (= \< (nth movements (mod m M)))
                                  move-left move-right)
                     rock'      (mapv move-fn rock)
                     moved-rock (if (and (inbounds? rock')
                                         (not-any? tower rock'))
                                  rock' rock)
                     rock'' (mapv move-down moved-rock)]
                 (if (not-any? tower rock'')
                   (recur rock'' (inc m))
                   (let [max-y       (max max-y (:y (peek moved-rock)))
                         tower       (into (set moved-rock)
                                           (filter #(> (:y %) (- max-y 100)))
                                           tower)
                         t-hash      (+ ^long (peaks-hash tower max-y)
                                        ^long (* 100 (mod r R))
                                        ^long (* 500 (mod m M)))
                         r           (inc r)
                         [r skipped] (if-let [[r1 y1] (seen t-hash)]
                                       (let [dr (- r r1)
                                             dy (- max-y y1)
                                             skipped-periods (quot (- rounds r) dr)]
                                         [(+ r (* skipped-periods dr))
                                          (+ skipped (* skipped-periods dy))])
                                       [r skipped])]
                     [r (inc m) max-y skipped (assoc! seen t-hash [r max-y]) tower]))))))))))


(defn solve
  ([] (solve 17))
  ([input]
   (let [movements (aoc/read-input-line input)]
     [(play movements 2022)
      (play movements 1000000000000)])))


(solve)
