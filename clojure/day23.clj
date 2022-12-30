(ns day23
  (:require aoc))


(def n [0 -1])
(def s [0 1])
(def w [-1 0])
(def e [1 0])

(defn pt+ [[ax ay] [bx by]]
  [(+ ax bx) (+ ay by)])

(def adjacent
  [[n [(pt+ n w) n (pt+ n e)]]
   [s [(pt+ s w) s (pt+ s e)]]
   [w [(pt+ n w) w (pt+ s w)]]
   [e [(pt+ n e) e (pt+ s e)]]])


(defn propose [elves elf round proposals]
  (if (not-any? elves (aoc/neighbours elf 8))
    (assoc proposals elf [elf])
    (let [prop (or (first (for [i (range 4)
                                :let [[dir adjs] (adjacent (mod (+ round i) 4))
                                      nbs (map #(pt+ elf %) adjs)]
                                :when (not-any? elves nbs)]
                            (pt+ elf dir)))
                   elf)]
      (update proposals prop #(conj % elf)))))

(defn make-proposals [elves round]
  (reduce
   (fn [proposals elf]
     (propose elves elf round proposals))
   {}
   elves))

(defn play-round [elves round]
  (reduce-kv (fn [new-elves prop old-pos]
     (if (= (count old-pos) 1)
       (conj new-elves prop)
       (apply conj new-elves old-pos)))
   #{}
   (make-proposals elves round)))

(defn calc-area [elves]
  (let [[xs ys] (map sort (apply mapv vector elves))]
    (* (- (inc (last xs)) (first xs))
       (- (inc (last ys)) (first ys)))))

(defn part-1 [elves]
  (-> (reduce play-round elves (range 10))
      calc-area
      (- (count elves))))

(defn part-2 [elves]
  (loop [round 0
         elves elves]
    (let [new-elves (play-round elves round)]
      (if (= new-elves elves)
        (inc round)
        (recur (inc round) new-elves)))))


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
