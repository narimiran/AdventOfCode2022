(ns day05
  (:require aoc))


(defn parse-stacks [drawing]
  (->> drawing
       drop-last
       aoc/transpose
       (map #(remove #{\space \[ \]} %))
       (remove empty?)
       (into [[]]))) ; empty first to have real stacks start from index 1

(defn move-boxes [stacks [amount from to] pick-multiple?]
  (let [[took remains] (split-at amount (stacks from))
        put (into (stacks to) (if pick-multiple? (reverse took) took))]
    (-> stacks
        (assoc! from remains)
        (assoc! to put))))

(defn operate-crane [stacks instructions pick-multiple?]
  (->> instructions
       (reduce #(move-boxes %1 %2 pick-multiple?) (transient stacks))
       persistent!
       (map first)
       (apply str)))

(defn solve
  ([] (solve (aoc/read-file 5)))
  ([input]
   (let [[raw-stacks raw-instructions] (aoc/parse-input-paragraphs input)
         stacks       (parse-stacks raw-stacks)
         instructions (mapv aoc/integers raw-instructions)
         operate      #(operate-crane stacks instructions %)]
     [(operate false)
      (operate :CrateMover9001)])))


(solve)
