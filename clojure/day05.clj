(ns day05
  (:require aoc))


(defn parse-stacks [drawing]
  (->> drawing
       aoc/transpose
       (map #(remove #{\space \[ \]} %))
       (remove empty?)
       vec))


(defn move [stacks [amount from to] pick-multiple?]
  (let [from-stack (dec from)
        to-stack (dec to)
        [took remains] (split-at amount (stacks from-stack))
        put (apply conj
                   (stacks to-stack)
                   (if pick-multiple? (reverse took) took))]
    (-> stacks
        (assoc from-stack remains)
        (assoc to-stack put))))


(defn solve [stacks instructions can-pick-multiple?]
  (let [move-boxes
        (fn [acc instr] (move acc instr can-pick-multiple?))]
    (->> instructions
         (reduce move-boxes stacks)
         (map first)
         (apply str))))


(def input
  (->> (aoc/read-input 5 :string #"\n\n")
       (map aoc/parse-multiline-string)))
(def stacks
  (->> input
       first
       drop-last
       parse-stacks))
(def instructions
  (->> input
       last
       (map aoc/integers)))


[(solve stacks instructions false)
 (solve stacks instructions true)]
