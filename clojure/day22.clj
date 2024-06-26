(ns day22
  (:require aoc
            [clojure.string :as str]
            [clojure.core.match :refer [match]]))


(defn wrap-1 [[x y] [dx dy]]
  ;; hardcoded for my input
  (cond
    (zero? dy) (let [wrap (case (quot y 50) , (0 2) 100 , (1 3) 50)]
                 [[(- x (* wrap dx)) y] [dx dy]])
    (zero? dx) (let [wrap (case (quot x 50) , 0 100 , 1 150 , 2 50)]
                 [[x (- y (* wrap dy))] [dx dy]])))

(defn wrap-2 [[x y] d]
  ;; hardcoded for my input
  (match [(quot x 50) (quot y 50) d]
    [_ 0 [-1 0]] [[0 (- 149 y)]   [1  0]]
    [_ 1 [-1 0]] [[(- y 50) 100]  [0  1]]
    [_ 2 [-1 0]] [[50 (- 149 y)]  [1  0]]
    [_ 3 [-1 0]] [[(- y 100) 0]   [0  1]]
    [_ 0 [1  0]] [[99 (- 149 y)]  [-1 0]]
    [_ 1 [1  0]] [[(+ 50 y) 49]   [0 -1]]
    [_ 2 [1  0]] [[149 (- 149 y)] [-1 0]]
    [_ 3 [1  0]] [[(- y 100) 149] [0 -1]]
    [0 _ [0 -1]] [[50 (+ 50 x)]   [1  0]]
    [1 _ [0 -1]] [[0 (+ 100 x)]   [1  0]]
    [2 _ [0 -1]] [[(- x 100) 199] [0 -1]]
    [0 _ [0  1]] [[(+ x 100) 0]   [0  1]]
    [1 _ [0  1]] [[49 (+ 100 x)]  [-1 0]]
    [2 _ [0  1]] [[99 (- x 50)]   [-1 0]]))



(defn walk [grid wrap-fn move pos d]
  (reduce
   (fn [[p d] _]
     (let [p'       (aoc/pt+ p d)
           [p'' d'] (if-not (contains? grid p')
                      (wrap-fn p' d)
                      [p' d])]
       (if (= \. (grid p'' \#))
         [p'' d']
         (reduced [p d]))))
   [pos d]
   (range (parse-long move))))


(defn traverse [start moves grid wrap-fn]
  (let [walk_ (partial walk grid wrap-fn)]
    (reduce
     (fn [[pos [dx dy :as d]] move]
       (case move
         "L" [pos [dy (- dx)]]
         "R" [pos [(- dy) dx]]
         (walk_ move pos d)))
     [start [1 0]]
     moves)))

(defn password [[[x y] d]]
  (+ (* 4 (inc x))
     (* 1000 (inc y))
     ({[1 0] 0 , [0 1] 1 , [-1 0] 2 , [0 -1] 3} d)))

(defn solve
  ([] (solve (aoc/read-file 22)))
  ([input]
   (let [[field [moves]] (aoc/parse-input-paragraphs input)
         start  [(str/index-of (first field) \.) 0]
         moves  (-> moves
                    (str/replace #"(L|R)" " $1 ")
                    (str/split #" "))
         grid   (aoc/grid->points field #{\. \#})
         move_  (partial traverse start moves grid)]
     [(-> (move_ wrap-1) password)
      (-> (move_ wrap-2) password)])))


(solve)
