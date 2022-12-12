(ns day05
  (:require aoc))


(defn parse-stacks [drawing]
  (->> drawing
       aoc/transpose
       (map #(remove #{\space \[ \]} %))
       (remove empty?)
       (cons '())
       vec))

(defn move [stacks [amount from to] pick-multiple?]
  (let [[took remains] (split-at amount (stacks from))
        put (apply conj
                   (stacks to)
                   (if pick-multiple? (reverse took) took))]
    (-> stacks
        (assoc from remains)
        (assoc to put))))

(defn solve [stacks instructions can-pick-multiple?]
  (let [move-boxes
        (fn [acc instr] (move acc instr can-pick-multiple?))]
    (->> instructions
         (reduce move-boxes stacks)
         (map first)
         (apply str))))


(def input
  (->> (aoc/read-input 5 {:sep #"\n\n"})
       (map aoc/parse-multiline-string)))

(def stacks
  (->> input
       first
       drop-last
       parse-stacks))

(def instructions
  (->> input
       last
       (mapv aoc/integers)))


[(solve stacks instructions false)
 (solve stacks instructions true)]
