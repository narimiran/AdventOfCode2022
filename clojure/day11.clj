(ns day11
  (:require aoc
            [clojure.string :as str]
            [clojure.core.match :refer [match]]))


(defn parse-operation [text]
  (match [(vec text)]
    [["*" "old"]] #(* % %)
    [["+" "old"]] #(+ % %)
    [["*" val]]   #(* % (parse-long val))
    [["+" val]]   #(+ % (parse-long val))))

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
    {:inspected 0
     :items     items
     :operation op
     :divisor   divisor
     :test      (partial #(if (zero? (rem % divisor))
                            if-true
                            if-false))}))

(defn monkey-play [monkeys curr]
  (loop [monkeys monkeys]
    (if-let [val (peek (:items (monkeys curr)))]
      (let [m     (monkeys curr)
            worry (-> val ((:operation m)) ((:reducer m)))
            dest  ((:test m) worry)]
        (recur (-> monkeys
                   (update-in [curr :inspected] inc)
                   (update-in [curr :items] pop)
                   (update-in [dest :items] #(conj % worry)))))
      monkeys)))

(defn play-round [monkeys]
  (reduce monkey-play monkeys (range (count monkeys))))

(defn play-game [monkeys rounds p1?]
  (let [lcm (apply * (map :divisor monkeys))
        reducer (if p1? #(quot % 3) #(rem % lcm))]
    (->> monkeys
         (mapv #(assoc % :reducer reducer))
         (iterate play-round)
         (drop rounds)
         first
         (map :inspected)
         (sort >)
         (take 2)
         (apply *))))


(def monkeys
  (->> (aoc/read-input-paragraphs 11)
       (mapv parse-monkey)))

[(play-game monkeys 20 true)
 (play-game monkeys 10000 false)]
