(ns day07
  (:require aoc
            [clojure.string :as str]
            [clojure.core.match :refer [match]]))


(defn go-up [path size]
  (let [val (parse-long size)]
    (loop [sizes {}
           path path]
      (if (empty? path) sizes
          (recur (merge-with + sizes {path val})
                 (pop path))))))

(defn calc-sizes [commands]
  (->> commands
       (reduce (fn [[sizes path] cmd]
                 (match (str/split cmd #" ")
                   ["$" "cd" "/"]  [sizes ["/"]]
                   ["$" "cd" ".."] [sizes (pop path)]
                   ["$" "cd" f]    [sizes (conj path f)]
                   (:or ["$" "ls"]
                        ["dir" _]) [sizes path]
                   [size _]        [(merge-with + sizes (go-up path size)) path]))
               [{} []])
       first))

(defn part-1 [sizes]
  (->> sizes
       (filter #(<= % 100000))
       (reduce +)))

(defn part-2 [sizes total-size]
  (let [goal 40000000
        to-remove (- total-size goal)]
    (->> sizes
         (filter #(>= % to-remove))
         (reduce min))))

(defn solve
  ([] (solve 7))
  ([input]
   (let [folders    (calc-sizes (aoc/read-input input))
         total-size (folders ["/"])
         sizes      (vals folders)]
     [(part-1 sizes)
      (part-2 sizes total-size)])))


(solve)
