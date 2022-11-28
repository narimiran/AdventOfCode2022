(ns aoc
  (:require [clojure.string :as str]))


(defn read-input
  ([filename] (read-input filename :string))
  ([filename datatype]
   (let [name (if (int? filename)
                (format "%02d" filename)
                filename)]
     (->> name
          (#(str "inputs/" % ".txt"))
          slurp
          str/split-lines
          ((case datatype
             :int (partial map #(Integer/parseInt %))
             :list (partial map #(str/split % #""))
             :string identity))))))


(defn read-input-line
  ([filename] (read-input-line filename nil))
  ([filename sep]
   (->> filename
        read-input
        first
        ((case sep
           nil identity
           (partial #(str/split % sep)))))))


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
