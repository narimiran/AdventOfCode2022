(ns day10
  (:require aoc
            [clojure.string :as str]))


(defn read-instructions [lines]
  (reduce
   (fn [acc line]
     (let [[instr amt] (str/split line #" ")]
       (if (= instr "addx")
         (conj acc 0 (Integer/parseInt amt))
         (conj acc 0))))
   []
   lines))

(defn part-1 [x-positions]
  (->> (range 20 221 40)
       (map #(* % (nth x-positions (dec %))))
       (apply +)))

(defn part-2 [x-positions]
  (->> x-positions
       (map-indexed #(if (<= (dec %2) (mod %1 40) (inc %2))
                       "██"
                       "  "))
       (partition 40)
       (map str/join)))

(defn solve [filename]
  (let [x-positions
        (->> filename
             aoc/read-input
             read-instructions
             (reductions + 1))] ; x starts at 1
    [(part-1 x-positions)
     (part-2 x-positions)]))


(solve 10)
