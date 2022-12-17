(ns aoc
  (:require [clojure.string :as str]))


(defn parse-multiline-string
  [s & [out-type sep]]
  (->> (str/split s (or sep #"\n"))
       ((case out-type
          :int    (partial map parse-long)
          :list   (partial map #(str/split % #""))
          :vector (partial map vec)
          nil     identity))))


(defn read-input
  [filename & [out-type sep]]
  (let [name (if (int? filename)
               (format "%02d" filename)
               filename)]
    (-> (str "inputs/" name ".txt")
        slurp
        (parse-multiline-string out-type sep))))


(defn read-input-line
  [filename & [sep]]
  (->> filename
       read-input
       first
       ((case sep
          nil identity
          (partial #(str/split % sep))))))

(defn read-input-paragraphs
  [filename & [out-type]]
  (->> (read-input filename nil #"\n\n")
       (mapv #(parse-multiline-string % out-type))))


(defn string->digits [s]
  (->> (str/split s #"")
       (mapv parse-long)))


(defn transpose [matrix]
  (apply mapv vector matrix))


(defn manhattan
  ([p] (manhattan p [0 0]))
  ([[x1 y1] [x2 y2]]
   (+ (abs (- x1 x2))
      (abs (- y1 y2)))))


(defn ord [s]
  (int (first s)))


(defn integers
  [s & {:keys [negative?]
        :or {negative? true}}]
  (mapv parse-long
        (re-seq (if negative? #"-?\d+" #"\d+") s)))


(defn neighbours [[x y] amount]
  (for [dx [-1 0 1]
        dy [-1 0 1]
        :when
        (case amount
          4 (not= (abs dx) (abs dy))
          8 (not= dx dy 0)
          9 true)]
    [(+ x dx) (+ y dy)]))


(defn vec2d->grid [v]
  (into {}
        (for [x (range (count (first v)))
              y (range (count v))]
          [[x y] ((v y) x)])))
