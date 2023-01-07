(ns day23
  (:require aoc))


(defn pick-direction [nbs n]
  (case n
    0 (take 3 nbs)
    1 (take-last 3 nbs)
    2 (let [[[nw] [w _ sw]] (partition 3 nbs)]
        [nw w sw])
    3 (let [[_ [ne] [e] [_ se]] (partition 2 nbs)]
        [ne e se])))

(defn propose [elves round elf]
  (let [nbs (aoc/neighbours elf 8)]
    (if (not-any? elves nbs) [elf elf]
        (let [prop (or (first (for [i (range 4)
                                    :let [n   (mod (+ round i) 4)
                                          dir (pick-direction nbs n)]
                                    :when (not-any? elves dir)]
                                (second dir)))
                       elf)]
          [prop elf]))))

(defn play-round [elves round]
  (->> elves
       (pmap (partial propose elves round))
       (reduce (fn [proposals [k v]]
                 (update proposals k #(conj % v)))
               {})
       (reduce-kv (fn [new-elves prop old-pos]
                    (if (= (count old-pos) 1)
                      (conj new-elves prop)
                      (into new-elves old-pos)))
                  #{})))

(defn calc-area [elves]
  (let [[xs ys] (map sort (apply mapv vector elves))]
    (* (- (inc (last xs)) (first xs))
       (- (inc (last ys)) (first ys)))))

(defn part-1 [elves]
  (-> (reduce play-round elves (range 10))
      calc-area
      (- (count elves))))

(defn part-2 [elves]
  (reduce
   (fn [elves round]
     (let [new-elves (play-round elves round)]
       (if (= new-elves elves)
         (reduced (inc round))
         new-elves)))
   elves
   (iterate inc 0)))


(defn parse-input [input]
  (set (for [[y line] (map-indexed vector input)
             [x char] (map-indexed vector line)
             :when (= char \#)]
         [x y])))

(defn solve
  ([] (solve 23))
  ([input]
   (let [elves (parse-input (aoc/read-input input))]
     [(part-1 elves)
      (part-2 elves)])))


(solve)
