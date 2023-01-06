(ns day24
  (:require aoc
            [clojure.set :as set]))


(defn neighbours [[x y]]
  (for [[dx dy] [[-1 0] [1 0] [0 -1] [0 1]]]
       [(+ x dx) (+ y dy)]))

(defn traverse [[blizzards walls w h] start goal t]
  (loop [t t
         queue #{start}]
    (if (queue goal) t
        (let [t          (inc t)
              candidates (into queue (mapcat neighbours queue))
              obstacles  (into walls
                               (for [[[x y] [dx dy]] blizzards]
                                 [(mod (+ x (* dx t)) w)
                                  (mod (+ y (* dy t)) h)]))]
          (recur t (set/difference candidates obstacles))))))


(defn parse-input [input]
  (let [res (for [[y line] (map-indexed vector input)
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
   (let [[_ _ w h :as data] (parse-input (aoc/read-input input :vector))
         start [0 -1]
         goal  [(dec w) h]
         p1 (traverse data start goal 0)
         p2 (->> p1
                 (traverse data goal start)
                 (traverse data start goal))]
     [p1 p2])))


(solve)
