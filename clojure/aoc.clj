(ns aoc
  (:require [clojure.string :as str]))


(defn parse-multiline-string
  [s & {:keys [datatype sep]
        :or {datatype :string sep #"\n"}}]
  (->> (str/split s sep)
       ((case datatype
          :int (partial map #(Integer/parseInt %))
          :list (partial map #(str/split % #""))
          :string identity))))


(defn read-input
  [filename & {:keys [datatype sep]
               :or {datatype :string sep #"\n"}}]
  (let [name (if (int? filename)
               (format "%02d" filename)
               filename)]
    (->> name
         (#(str "inputs/" % ".txt"))
         slurp
         (#(parse-multiline-string % {:datatype datatype :sep sep})))))


(defn read-input-line
  [filename & {:keys [sep]
               :or {sep nil}}]
  (->> filename
       read-input
       first
       ((case sep
          nil identity
          (partial #(str/split % sep))))))


(defn string->digits [s]
  (->> s
       (#(str/split % #""))
       (mapv #(Integer/parseInt %))))


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
  (map #(Integer/parseInt %)
       (re-seq (if negative? #"-?\d+" #"\d+") s)))
