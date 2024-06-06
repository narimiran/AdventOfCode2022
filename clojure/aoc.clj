(ns aoc
  (:require [clojure.string :as str]))


(def empty-queue clojure.lang.PersistentQueue/EMPTY)


(defn read-file [file]
  (let [name (if (int? file)
               (format "%02d" file)
               file)]
    (slurp (str "inputs/" name ".txt"))))


(defn integers
  [s & {:keys [negative?]
        :or {negative? true}}]
  (mapv parse-long
        (re-seq (if negative? #"-?\d+" #"\d+") s)))

(defn string->digits [s]
  (->> (str/split s #"")
       (map parse-long)
       (filterv some?)))

(defn parse-input
  [s & [parse-fn {:keys [word-sep nl-sep]}]]
  (let [f (case parse-fn
            :int    parse-long
            :ints   integers
            :digits string->digits
            :chars  vec
            :words  #(str/split % (or word-sep #" "))
            nil     identity
            parse-fn)]
    (mapv f (str/split s (or nl-sep #"\n")))))

(defn parse-input-line
  [input & [parse-fn word-sep]]
  (-> input
      (parse-input parse-fn {:word-sep word-sep})
      first))

(defn parse-input-paragraphs
  [input & [parse-fn word-sep]]
  (->> (parse-input input nil {:nl-sep #"\n\n"})
       (mapv #(parse-input % parse-fn {:word-sep word-sep}))))


(defn transpose [matrix]
  (apply mapv vector matrix))


(defn manhattan ^long
  ([p] (manhattan p [0 0]))
  ([[^long x1 ^long y1] [^long x2 ^long y2]]
   (+ (abs (- x1 x2))
      (abs (- y1 y2)))))


(defn ord [s]
  (int (first s)))


(defn pt+ ^longs [[^long ax ^long ay] [^long bx ^long by]]
  [(+ ax bx) (+ ay by)])

(defn inside?
  ([size x y] (inside? size size x y))
  ([size-x size-y x y]
   (and (< -1 x size-x)
        (< -1 y size-y))))

(defn neighbours ^longs [^long amount [^long x ^long y]]
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


(defn grid->points
  ([v] (grid->points v identity))
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

(defn count-if ^long [pred xs]
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


(defn gcd
  ([] 1)
  ([x] x)
  ([a b] (if (zero? b) a
             (recur b (mod a b)))))

(defn lcm
  ([] 1)
  ([x] x)
  ([a b] (/ (* a b) (gcd a b))))
