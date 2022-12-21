(ns day21
  (:require aoc
            [clojure.string :as str]
            [clojure.math :as math]))


(defn parse-line [line]
  (let [[mkey & ops] (str/split line #" ")
        monkey (keyword (apply str (drop-last mkey)))
        yell   (if (= (count ops) 1)
                 {:val (parse-long (first ops))}
                 (let [[l op r] ops]
                   {:op    (symbol op)
                    :left  (keyword l)
                    :right (keyword r)}))]
    [monkey yell]))

(defn dfs [monkeys m]
  (let [{:keys [val op left right]} (monkeys m)]
    (if val val
        ((eval op) (dfs monkeys left) (dfs monkeys right)))))

(defn human-yell [monkeys]
  (let [monkeys'  (assoc-in monkeys [:root :op] '-)
        init-sign (math/signum (dfs monkeys' :root))]
    (loop [lo 0
           hi 99999999999999]
      (let [mid       (quot (+ lo hi) 2)
            monkeys'' (assoc-in monkeys' [:humn :val] mid)
            root-val  (dfs monkeys'' :root)]
        (if (zero? root-val) mid
            (if (= (math/signum root-val) init-sign)
              (recur (inc mid) hi)
              (recur lo mid)))))))

(defn solve
  ([] (solve 21))
  ([input]
   (let [monkeys (->> (aoc/read-input input)
                      (into {} (map parse-line)))]
     [(dfs monkeys :root)
      (human-yell monkeys)])))


(solve)
