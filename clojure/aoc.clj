(ns aoc
  (:require [clojure.string :as str]))


(defn parse-multiline-string
  ([s] (parse-multiline-string s :string #"\n"))
  ([s datatype] (parse-multiline-string s datatype #"\n"))
  ([s datatype sep]
   (->> (str/split s sep)
        ((case datatype
           :int (partial map #(Integer/parseInt %))
           :list (partial map #(str/split % #""))
           :string identity)))))


(defn read-input
  ([filename] (read-input filename :string #"\n"))
  ([filename datatype] (read-input filename datatype #"\n"))
  ([filename datatype sep]
   (let [name (if (int? filename)
                (format "%02d" filename)
                filename)]
     (->> name
          (#(str "inputs/" % ".txt"))
          slurp
          (#(parse-multiline-string % datatype sep))))))


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
