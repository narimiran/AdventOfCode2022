(ns day07
  (:require aoc
            [clojure.string :as strs]
            [clojure.core.match :refer [match]]))


(defn go-up [path val]
  (let [val (parse-long val)]
    (loop [sizes {}
           path path]
      (if (empty? path) sizes
          (recur (merge-with + sizes {path val})
                 (pop path))))))

(defn calc-sizes [commands]
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

(defn part-1 [folder-sizes]
  (->> folder-sizes
       vals
       (filter #(<= % 100000))
       (reduce +)))

(defn part-2 [folder-sizes]
  (let [total-size (folder-sizes ["/"])
        goal 40000000
        to-remove (- total-size goal)]
    (->> folder-sizes
         vals
         (filter #(>= % to-remove))
         (apply min))))


(def folder-sizes
  (->> 7
       aoc/read-input
       calc-sizes))

[(part-1 folder-sizes)
 (part-2 folder-sizes)]
