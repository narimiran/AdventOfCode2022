(ns aoc
  (:require [clojure.string :as str]))


(defn parse-multiline-string
  [s & [out-type sep]]
  (->> (str/split s (or sep #"\n"))
       ((case out-type
          :int    (partial map parse-long)
          :list   (partial map #(str/split % #""))
          :vector (partial mapv vec)
          nil     identity))))


(defn read-input
  [input & [out-type sep]]
  (let [name (if (int? input)
               (format "%02d" input)
               input)]
    (-> (str "inputs/" name ".txt")
        slurp
        (parse-multiline-string out-type sep))))


(defn read-input-line
  [input & [sep]]
  (->> input
       read-input
       first
       ((case sep
          nil identity
          (partial #(str/split % sep))))))

(defn read-input-paragraphs
  [input & [out-type]]
  (->> (read-input input nil #"\n\n")
       (mapv #(parse-multiline-string % out-type))))


(defn string->digits [s]
  (->> (str/split s #"")
       (mapv parse-long)))


(defn transpose [matrix]
  (apply mapv vector matrix))


(defn manhattan ^long
  ([p] (manhattan p [0 0]))
  ([[^long x1 ^long y1] [^long x2 ^long y2]]
   (+ (abs (- x1 x2))
      (abs (- y1 y2)))))


(defn ord [s]
  (int (first s)))


(defn integers
  [s & {:keys [negative?]
        :or {negative? true}}]
  (mapv parse-long
        (re-seq (if negative? #"-?\d+" #"\d+") s)))


(defn pt+ ^longs [[^long ax ^long ay] [^long bx ^long by]]
  [(+ ax bx) (+ ay by)])

(defn neighbours ^longs [[^long x ^long y] ^long amount]
  (for [^long dy [-1 0 1]
        ^long dx [-1 0 1]
        :when
        (case amount
          4 (odd? (- dx dy))
          5 (<= (+ (abs dx) (abs dy)) 1)
          8 (not= dx dy 0)
          9 true)]
    [(+ x dx) (+ y dy)]))

(defn neighbours-3d [[^long x ^long y ^long z]]
  [[(dec x) y z] [(inc x) y z]
   [x (dec y) z] [x (inc y) z]
   [x y (dec z)] [x y (inc z)]])


(defn vec2d->grid
  ([v] (vec2d->grid v identity))
  ([v pred]
   (into {}
         (for [[y line] (map-indexed vector v)
               [x char] (map-indexed vector line)
               :when (pred char)]
           [[x y] char]))))

(defn none? [pred xs]
  ;; Faster version of `not-any?`.
  (reduce
   (fn [acc x]
     (if (pred x)
       (reduced false)
       acc))
   true
   xs))

(defn array-none? [pred ^longs arr]
  ;; Much much faster version of `not-any?` for long-arrays.
  (loop [idx (dec (alength arr))
         acc true]
    (if (neg? idx)
      acc
      (if (pred (aget arr idx))
        false
        (recur (dec idx) acc)))))

(defn count-if [pred xs]
  (reduce
   (fn [^long acc x]
     (if (pred x) (inc acc) acc))
   0
   xs))

(defn find-first [pred xs]
  (reduce
   (fn [_ x]
     (when (pred x) (reduced x)))
   nil
   xs))
