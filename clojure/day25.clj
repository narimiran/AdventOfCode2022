(ns day25
  (:require aoc
            [clojure.string :as str]))


(def digits "=-012")


(defn from-5 [number]
  (reduce
   (fn [acc d]
     (+ (* 5 acc)
        (- (str/index-of digits d) 2)))
   0
   number))

(defn to-5 [number]
  (loop [d number
         acc '()]
    (if (zero? d) (str/join acc)
        (let [m (mod  (+ d 2) 5)
              d (quot (+ d 2) 5)
              digit (nth digits m)]
          (recur d (cons digit acc))))))

(defn solve
  ([] (solve (aoc/read-file 25)))
  ([input]
   (let [numbers (aoc/parse-input input)]
     (->> numbers
          (map from-5)
          (reduce +)
          to-5))))


(solve)
