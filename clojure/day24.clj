(ns day24
  (:require aoc
            [clojure.data.int-map :as i]))


(def ^:const N 128)
(def ^:const -N (- N))


(defn pt->num ^long [[^long x ^long y]]
  (+ x (* N y)))

(defn neighbours ^longs [^long pt]
  (for [^long delta [-1 1 -N N]]
    (+ pt delta)))

(defn move-blizzards [blizzards w h t]
  (let [w (long w) , h (long h) , t (long t)]
    (map (fn [[[^long x ^long y] [^long dx ^long dy]]]
           (pt->num [(mod (+ x (* dx t)) w)
                     (mod (+ y (* dy t)) h)]))
         blizzards)))

(defn traverse [[blizzards walls w h] start goal t]
  (reduce
   (fn [queue ^long t]
     (if (queue goal) (reduced (dec t))
         (let [candidates (into queue (mapcat neighbours queue))
               obstacles  (into walls (move-blizzards blizzards w h t))]
           (i/difference candidates obstacles))))
   (i/dense-int-set [start])
   (iterate inc t)))


(defn parse-input [input]
  (let [inp (aoc/read-input input :vector)
        res (for [[y line] (map-indexed vector inp)
                  [x char] (map-indexed vector line)
                  :let [pt [(dec x) (dec y)]
                        pt' (pt->num pt)]]
              (case char
                \# [nil         pt']
                \> [[pt [1  0]] nil]
                \< [[pt [-1 0]] nil]
                \v [[pt [0  1]] nil]
                \^ [[pt [0 -1]] nil]
                [nil nil]))
        blizzards (->> res (map first) (remove empty?))
        walls' (->> res (map second) (remove nil?))
        w (mod  (last walls') N)
        h (quot (last walls') N)
        walls (i/dense-int-set (into walls' [(* 2 -N)
                                             (pt->num [(dec w) (inc h)])]))]
    [blizzards walls w h]))

(defn solve
  ([] (solve 24))
  ([input]
   (let [[_ _ w h :as data] (parse-input input)
         start -N
         goal  (pt->num [(dec w) h])
         traverse_ (partial traverse data)
         p1 (->> 0
                 (traverse_ start goal))
         p2 (->> p1
                 (traverse_ goal start)
                 (traverse_ start goal))]
     [p1 p2])))


(solve)

