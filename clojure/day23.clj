(ns day23
  (:require aoc
            [clojure.data.int-map :refer [dense-int-set int-map]]))


(def ^:const S 256)
(def ^:const N (- S))

(def directions
  [[(dec N)  N (inc N)]   ; N
   [(dec S)  S (inc S)]   ; S
   [(dec N) -1 (dec S)]   ; W
   [(inc N)  1 (inc S)]]) ; E

(def adjacent
  (dedupe (reduce concat directions)))


(defn neighbours [^long elf direction]
  (map #(+ elf ^long %) direction))

(defn propose [elves ^long round proposals elf]
  (let [nbs (neighbours elf adjacent)]
    (if (not-any? elves nbs) proposals
        (let [prop (reduce
                    (fn [elf ^long i]
                      (let [n       (mod (+ round i) 4)
                            dir-nbs (neighbours elf (directions n))]
                        (if (not-any? elves dir-nbs)
                          (reduced (second dir-nbs))
                          elf)))
                    elf
                    (range 4))]
          (if (proposals prop)
            (dissoc! proposals prop)
            (assoc!  proposals prop elf))))))

(defn move [elves proposals]
  (as-> elves $
    (transient $)
    (reduce disj! $ (vals proposals))
    (reduce conj! $ (keys proposals))
    (persistent! $)))

(defn play-round [elves round]
  (->> elves
       (reduce (partial propose elves round) (transient (int-map)))
       persistent!
       (#(when (seq %) (move elves %)))))

(defn calc-area [elves]
  (let [xs (sort (map #(mod  % S) elves))
        ys (sort (map #(quot % S) elves))]
    (* (inc (- (last xs) (first xs)))
       (inc (- (last ys) (first ys))))))


(defn part-1 [elves]
  (-> (reduce play-round elves (range 10))
      calc-area
      (- (count elves))))

(defn part-2 [elves]
  (reduce
   (fn [elves round]
     (let [new-elves (play-round elves round)]
       (if (nil? new-elves)
         (reduced (inc round))
         new-elves)))
   elves
   (iterate inc 0)))


(defn parse-input [input]
  (->> input
       aoc/read-input
       (#(for [[y line] (map-indexed vector %)
               [x char] (map-indexed vector line)
               :when (= char \#)]
           (+ (/ S 2) x
              (* S (+ (/ S 2) y)))))
       dense-int-set))

(defn solve
  ([] (solve 23))
  ([input]
   (let [elves (parse-input input)]
     [(part-1 elves)
      (part-2 elves)])))


(solve)
