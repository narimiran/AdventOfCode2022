(ns day12
  (:require aoc
            [clojure.data.int-map :refer [int-map]]))


(def ^:const N 100)


(defn neighbours ^longs [^long pt]
  (for [^long delta [-1 1 (- N) N]]
    (+ delta pt)))

(defn find-val [grid height]
  (some #(when (= height (val %)) (key %)) grid))

(defn travel [grid part]
  (let [start (find-val grid -14)
        end   (find-val grid -28)
        grid  (-> grid
                  (assoc start 0)
                  (assoc end 25))]
    (loop [seen  #{end}
           queue [[0 end]]]
      (let [[steps curr] (peek queue)
            h (grid curr)]
        (if (and (zero? h)
                 (or (= part 2) (= curr start)))
          steps
          (let [nbs (filter #(and (not (seen %))
                                  (>= (grid % -99) (dec h)))
                            (neighbours curr))
                nexts (mapv #(vector (inc steps) %) nbs)]
            (recur (into seen nbs)
                   (into nexts (pop queue)))))))))

(defn make-grid [lines]
  (into (int-map)
        (for [[y line] (map-indexed vector lines)
              [x char] (map-indexed vector line)]
          [(+ x (* N y))
           (- (int char) (int \a))])))

(defn solve
  ([] (solve (aoc/read-file 12)))
  ([input]
   (let [grid (make-grid (aoc/parse-input input :chars))
         p1 (future (travel grid 1))
         p2 (future (travel grid 2))]
     [@p1 @p2])))


(solve)
