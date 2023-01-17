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
      :test      (fn [x] (if (zero? ^long (mod x divisor)) if-true if-false))})))

(defn update-monkeys [m k1 k2 f]
  ;; Much faster than the `update-in` built-in.
  (let [m2 (m k1)]
    (assoc m k1 (assoc m2 k2 (f (k2 m2))))))

(defn monkey-play [monkeys curr]
  (loop [monkeys monkeys]
    (if-let [val (first (:items (monkeys curr)))]
      (let [{:keys [operation reducer test]} (monkeys curr)
            worry (reducer (operation val))
            dest  (test worry)]
        (recur (-> monkeys
                   (update-monkeys curr :inspected inc)
                   (update-monkeys curr :items rest)
                   (update-monkeys dest :items #(conj % worry)))))
      monkeys)))

(defn play-round [monkeys]
  (reduce monkey-play monkeys (range (count monkeys))))

(defn play-game [monkeys rounds divide-worry?]
  (let [lcm     (reduce * (map :divisor monkeys))
        reducer (if divide-worry? #(quot ^long % 3) #(mod ^long % lcm))]
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
