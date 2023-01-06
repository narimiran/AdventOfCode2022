(ns day17
  (:require aoc))


(def rocks
  [[[0 0] [1 0] [2 0] [3 0]]
   [[1 0] [0 1] [1 1] [2 1] [1 2]]
   [[0 0] [1 0] [2 0] [2 1] [2 2]]
   [[0 0] [0 1] [0 2] [0 3]]
   [[0 0] [1 0] [0 1] [1 1]]])

(defn move-left  [[x y]] [(dec x) y])
(defn move-right [[x y]] [(inc x) y])
(defn move-down  [[x y]] [x (dec y)])

(defn inbounds? [rock]
  (every? #(<= 0 % 6) (map first rock)))

(defn not-clashes? [rock tower]
  (not-any? (set rock) tower))

(defn peaks [tower max-y]
  (set (for [[x y] tower
             :when (= y max-y)]
         x)))


(defn play [movements rounds]
  (let [M (count movements)
        R (count rocks)
        tower (for [x (range 7)] [x 0])]
    (loop [[r m max-y skipped seen tower] [0 0 0 0 {} tower]]
      (if (>= r rounds) (+ max-y skipped)
          (let [init-pos [2 (+ max-y 4)]
                rock     (map #(aoc/pt+ init-pos %) (nth rocks (mod r R)))]
            (recur
             (loop [rock rock
                    m m]
               (let [move-fn    (if (= \< (nth movements (mod m M)))
                                  move-left move-right)
                     rock'      (map move-fn rock)
                     moved-rock (if (and (inbounds? rock')
                                         (not-clashes? rock' tower))
                                  rock' rock)
                     rock'' (map move-down moved-rock)]
                 (if (not-clashes? rock'' tower)
                   (recur rock'' (inc m))
                   (let [max-y       (reduce max max-y (map second moved-rock))
                         tower       (->> tower
                                          (filter #(> (second %) (- max-y 100)))
                                          (concat moved-rock)
                                          dedupe)
                         t-hash      [(peaks tower max-y) (mod r R) (mod m M)]
                         r           (inc r)
                         [r skipped] (if-let [[r1 y1] (seen t-hash)]
                                       (let [dr (- r r1)
                                             dy (- max-y y1)
                                             skipped-periods (quot (- rounds r) dr)]
                                         [(+ r (* skipped-periods dr))
                                          (+ skipped (* skipped-periods dy))])
                                       [r skipped])]
                     [r (inc m) max-y skipped (assoc seen t-hash [r max-y]) tower]))))))))))


(defn solve
  ([] (solve 17))
  ([input]
   (let [movements (aoc/read-input-line input)]
     [(play movements 2022)
      (play movements 1000000000000)])))


(solve)
