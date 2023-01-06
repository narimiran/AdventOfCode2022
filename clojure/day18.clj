(ns day18
  (:require aoc
            [clojure.set :as set]))


(defn find-extremes [cubes]
  (let [lower (reduce min (map #(reduce min %) cubes))
        upper (reduce max (map #(reduce max %) cubes))]
    [(dec lower) (inc upper)]))

(defn space-around [cubes]
  (let [[lower upper] (find-extremes cubes)]
    (loop [seen  cubes
           queue [(repeat 3 lower)]]
      (if (empty? queue) (set/difference seen cubes)
          (let [valid
                (for [nb (aoc/neighbours-3d (peek queue))
                      :when (and (not (seen nb))
                                 (every? #(<= lower % upper) nb))]
                  nb)]
            (recur (into seen valid)
                   (into (pop queue) valid)))))))

(defn parse-input [filename]
  (->> filename
       aoc/read-input
       (map aoc/integers)
       set))

(defn solve
  ([] (solve 18))
  ([input]
   (let [cubes (parse-input input)
         neighbours (mapcat aoc/neighbours-3d cubes)
         outside (space-around cubes)]
     [(count (remove cubes neighbours))
      (count (filter outside neighbours))])))


(solve)
