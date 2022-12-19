(ns day10
  (:require aoc
            [clojure.string :as str]))


(defn read-instructions [lines]
  (reduce
   (fn [acc line]
     (let [[instr amt] (str/split line #" ")]
       (if (= instr "addx")
         (conj acc 0 (parse-long amt))
         (conj acc 0))))
   []
   lines))

(defn part-1 [x-positions]
  (->> (range 20 221 40)
       (map #(* % (nth x-positions (dec %))))
       (reduce +)))

(defn part-2 [x-positions]
  (->> x-positions
       (map-indexed #(if (<= (dec %2) (mod %1 40) (inc %2))
                       "██"
                       "  "))
       (partition 40)
       (map str/join)))

(defn solve
  ([] (solve 10))
  ([input]
   (let [x-positions
         (->> input
              aoc/read-input
              read-instructions
              (reductions + 1))] ; x starts at 1
     [(part-1 x-positions)
      (part-2 x-positions)])))


(solve 10)
