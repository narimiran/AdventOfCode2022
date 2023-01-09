(ns day24
  (:require aoc
            [clojure.set :as set]))


(defn neighbours [[x y]]
  (for [[dx dy] [[-1 0] [1 0] [0 -1] [0 1]]]
       [(+ x dx) (+ y dy)]))

(defn traverse [[blizzards walls w h] start goal t]
  (reduce
   (fn [queue t]
     (if (queue goal) (reduced (dec t))
         (let [candidates (into queue (mapcat neighbours queue))
               obstacles  (into walls
                                (for [[[x y] [dx dy]] blizzards]
                                  [(mod (+ x (* dx t)) w)
                                   (mod (+ y (* dy t)) h)]))]
           (set/difference candidates obstacles))))
   #{start}
   (iterate inc t)))


(defn parse-input [input]
  (let [inp (aoc/read-input input :vector)
        res (for [[y line] (map-indexed vector inp)
                  [x char] (map-indexed vector line)
                  :let [pt [(dec x) (dec y)]]]
                 (case char
                   \# [nil         pt]
                   \> [[pt [1  0]] nil]
                   \< [[pt [-1 0]] nil]
                   \v [[pt [0  1]] nil]
                   \^ [[pt [0 -1]] nil]
                   [nil nil]))
        blizzards (->> res (map first) (remove empty?))
        walls' (->> res (map second) (remove empty?))
        [w h] (last walls')
        walls (set (into walls' [[0 -2] [(dec w) (inc h)]]))]
    [blizzards walls w h]))

(defn solve
  ([] (solve 24))
  ([input]
   (let [[_ _ w h :as data] (parse-input input)
         start [0 -1]
         goal  [(dec w) h]
         traverse_ (partial traverse data)
         p1 (->> 0
                 (traverse_ start goal))
         p2 (->> p1
                 (traverse_ goal start)
                 (traverse_ start goal))]
     [p1 p2])))


(solve)
