(ns day07
  (:require aoc
            [clojure.string :as strs]
            [clojure.core.match :refer [match]]))


(defn go-up
  ([path val] (go-up path {} (Integer/parseInt val)))
  ([path dict val]
   (if (empty? path)
     dict
     (let [[hd & tl] path
           full-path (apply str (conj (interpose "/" tl) hd))]
       (go-up (pop path)
              (merge-with + dict {full-path val})
              val)))))


(defn parse-input [commands]
  (->> commands
       (reduce
        (fn [[sizes path] cmd]
          (match [(strs/split cmd #" ")]
            [["$" "cd" "/"]]  [sizes ["/"]]
            [["$" "cd" ".."]] [sizes (pop path)]
            [["$" "cd" f]]    [sizes (conj path f)]
            [(:or ["$" "ls"]
                  ["dir" _])] [sizes path]
            [[val _]]         [(merge-with + sizes (go-up path val)) path]))
        [{} []])
       first))


(defn part-1 [sizes]
  (->> sizes
       vals
       (filter #(<= % 100000))
       (reduce +)))


(defn part-2 [sizes]
  (let [total-size (sizes "/")
        goal 40000000
        to-remove (- total-size goal)]
    (->> sizes
         vals
         (filter #(>= % to-remove))
         (apply min))))


(def sizes
  (->> 7
       aoc/read-input
       parse-input))


[(part-1 sizes)
 (part-2 sizes)]
