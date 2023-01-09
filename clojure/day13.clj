(ns day13
  (:require aoc))


(defn compair [l r]
  (cond
    (and (number? l) (number? r))     (- l r)
    (and (number? l) (sequential? r)) (compair [l] r)
    (and (sequential? l) (number? r)) (compair l [r])
    :else (loop [[hd & tl] (map compair l r)]
            (case hd
              nil (- (count l) (count r))
              0   (recur tl)
              hd))))

(defn part-1 [packets]
  (->> packets
       (map-indexed (fn [idx [l r]]
                      (if (neg? (compair l r))
                        (inc idx)
                        0)))
       (reduce +)))

(defn divider-index [packets target]
  (->> packets
       (remove #(pos? (compair % target)))
       count))

(defn part-2 [packets]
  (let [two [[2]]
        six [[6]]]
    (->> [two six]
         (into packets)
         (reduce concat)
         (#(* (divider-index % two)
              (divider-index % six))))))

(defn parse-input [input]
  (->> input
       aoc/read-input
       (remove empty?)
       (mapv read-string)
       (partition 2)))

(defn solve
  ([] (solve 13))
  ([input]
   (let [packets (parse-input input)]
     [(part-1 packets)
      (part-2 packets)])))


(solve)
