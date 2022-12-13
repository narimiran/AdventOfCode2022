(ns day13
  (:require aoc))


(defn compair [l r]
  (cond
    (and (number? l) (number? r))     (- l r)
    (and (number? l) (sequential? r)) (compair [l] r)
    (and (sequential? l) (number? r)) (compair l [r])
    :else (loop [diffs (map compair l r)]
            (case (first diffs)
              nil (- (count l) (count r))
              0   (recur (rest diffs))
              (first diffs)))))

(defn part-1 [packets]
  (->> packets
       (reduce
        (fn [[acc idx] [l r]]
          [(if (neg? (compair l r)) (+ acc idx) acc)
           (inc idx)])
        [0 1])
       first))

(defn divider-index [packets target]
  (->> packets
       (filter #(<= (compair % target) 0))
       count))

(defn part-2 [packets]
  (let [two [[2]]
        six [[6]]]
    (->> (conj packets [two six])
         (apply concat)
         (#(* (divider-index % two)
              (divider-index % six))))))


(def packets
  (->> 13
       (#(aoc/read-input % {:sep #"\n\n"}))
       (mapv aoc/parse-multiline-string)
       (mapv #(mapv read-string %))))

[(part-1 packets)
 (part-2 packets)]
