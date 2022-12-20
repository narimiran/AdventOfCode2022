(ns day20
  (:require aoc))


(defn move [xs [_ val :as x]]
  (let [idx     (.indexOf xs x)
        sliced  (into (subvec xs (inc idx)) (subvec xs 0 idx))
        new-pos (mod val (dec (count xs)))]
    (apply conj (subvec sliced 0 new-pos) x (subvec sliced new-pos))))

(defn play [xs rounds]
  (let [final (->> xs
                   (iterate #(reduce move % xs))
                   (drop rounds)
                   first
                   (mapv second))
        idx0 (.indexOf final 0)]
    (->> [1000 2000 3000]
         (map #(mod (+ idx0 %) (count final)))
         (map #(nth final %))
         (reduce +))))

(defn parse-input [input multi]
  (->> (aoc/read-input input :int)
       (map #(* multi %))
       (map-indexed vector)
       vec))

(defn solve
  ([] (solve 20))
  ([input]
   (let [nums1 (parse-input input 1)
         nums2 (parse-input input 811589153)]
     [(play nums1 1)
      (play nums2 10)])))


(solve)
