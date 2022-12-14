(ns day11
  (:require aoc
            [clojure.string :as str]
            [clojure.core.match :refer [match]]))


(defrecord Monkey [inspected items operation divisor test reducer])


(defn parse-operation [text]
  (match (vec text)
    ["*" "old"] #(* % %)
    ["+" "old"] #(+ % %)
    ["*" val]   #(* % (parse-long val))
    ["+" val]   #(+ % (parse-long val))))

(defn parse-monkey [lines]
  (let [items (aoc/integers (lines 1))
        op    (-> (lines 2)
                  (str/split #" ")
                  (#(take-last 2 %))
                  parse-operation)
        [divisor if-true if-false] (->> lines
                                        (drop 3)
                                        (map aoc/integers)
                                        (map first))]
    (map->Monkey
     {:inspected 0
      :items     items
      :operation op
      :divisor   divisor
      :test      (partial #(if (zero? (mod % divisor)) if-true if-false))})))

(defn monkey-play [monkeys curr]
  (loop [monkeys monkeys]
    (if-let [val (peek (:items (monkeys curr)))]
      (let [{:keys [operation reducer test]} (monkeys curr)
            worry (reducer (operation val))
            dest  (test worry)]
        (recur (-> monkeys
                   (update-in [curr :inspected] inc)
                   (update-in [curr :items] pop)
                   (update-in [dest :items] #(conj % worry)))))
      monkeys)))

(defn play-round [monkeys]
  (reduce monkey-play monkeys (range (count monkeys))))

(defn play-game [monkeys rounds divide-worry?]
  (let [lcm     (reduce * (map :divisor monkeys))
        reducer (if divide-worry? #(quot % 3) #(mod % lcm))]
    (->> monkeys
         (mapv #(assoc % :reducer reducer))
         (iterate play-round)
         (#(nth % rounds))
         (map :inspected)
         (sort >)
         (take 2)
         (reduce *))))

(defn parse-input [input]
  (->> input
       aoc/read-input-paragraphs
       (mapv parse-monkey)))

(defn solve
  ([] (solve 11))
  ([input]
   (let [monkeys (parse-input input)]
     [(play-game monkeys 20 true)
      (play-game monkeys 10000 false)])))


(solve)
