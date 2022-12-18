(ns day05
  (:require aoc))


(defn parse-stacks [drawing]
  (->> drawing
       drop-last
       aoc/transpose
       (map #(remove #{\space \[ \]} %))
       (remove empty?)
       (cons '()) ; to have real stacks start from index 1
       vec))

(defn move-boxes [stacks [amount from to] pick-multiple?]
  (let [[took remains] (split-at amount (stacks from))
        put (reduce conj
                    (stacks to)
                    (if pick-multiple? (reverse took) took))]
    (-> stacks
        (assoc from remains)
        (assoc to put))))

(defn operate-crane [stacks instructions pick-multiple?]
  (->> instructions
       (reduce #(move-boxes %1 %2 pick-multiple?) stacks)
       (map first)
       (apply str)))

(defn solve [filename]
  (let [[raw-stacks raw-instructions] (aoc/read-input-paragraphs filename)
        stacks       (parse-stacks raw-stacks)
        instructions (mapv aoc/integers raw-instructions)]
    [(operate-crane stacks instructions false)
     (operate-crane stacks instructions true)]))


(solve 5)
