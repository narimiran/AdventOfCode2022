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
  (loop [commands commands
         sizes {}
         path []]
    (if (empty? commands)
      sizes
      (let [[hd & tl] commands]
        (match [(strs/split hd #" ")]
          [["$" "cd" "/"]]  (recur tl sizes ["/"])
          [["$" "cd" ".."]] (recur tl sizes (pop path))
          [["$" "cd" f]]    (recur tl sizes (conj path f))
          [(:or ["$" "ls"]
                ["dir" _])] (recur tl sizes path)
          [[val _]]         (recur tl (merge-with + sizes (go-up path val)) path))))))


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
