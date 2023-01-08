(ns day16
  (:require aoc))


(defn parse-line [line]
  (let [[valve flow & connections] (re-seq #"[A-Z]{2}|\d+" line)]
    [valve (parse-long flow) connections]))

(defn create-connections [input]
  (reduce
   (fn [[flows connections] [valve flow conns]]
     [(assoc flows valve flow)
      (reduce #(assoc %1 (str valve %2) 1)
              connections
              conns)])
   [{} {}]
   input))

(defn floyd-warshall [valves connections]
  (reduce
   (fn [conns [a b c]]
     (let [bc (str b c)
           ba (str b a)
           ac (str a c)]
       (if-not (distinct? a b c) conns
               (assoc conns bc
                      (min (get conns bc 999)
                           (+ (get conns ba 999)
                              (get conns ac 999)))))))
   connections
   (for [a valves , b valves , c valves] [a b c])))



(defn traverse [current closed-valves conns visited time]
  (let [res [visited]]
    (if (< time 2) res
        (apply concat res
               (for [valve closed-valves
                     :let [t (- time (conns (str current valve)) 1)]
                     :when (> t 1)]
                 (traverse valve (disj closed-valves valve) conns (assoc visited valve t) t))))))


(defn score [flows attempt]
  (->> attempt
       (map (fn [[valve time]]
              (* (flows valve) time)))
       (reduce +)))

(defn best-score-per-valve-set [flows attempts]
  (->> attempts
       (map (fn [attempt]
              [(set (keys attempt)) (score flows attempt)]))
       (reduce (fn [top-scores [k v]]
                 (update top-scores k (fnil #(max v %) 0)))
               {})))

(defn tandem-scores [top-scores]
  (for [[i [h_vis h_score]] (map-indexed vector top-scores)
        [j [e_vis e_score]] (map-indexed vector top-scores)
        :while (> i j)
        :when (not-any? h_vis e_vis)]
    (+ h_score e_score)))



(defn part-1 [[traverse_ flows]]
  (->> (traverse_ 30)
       (map (partial score flows))
       (reduce max)))

(defn part-2 [[traverse_ flows]]
  (->> (traverse_ 26)
       (best-score-per-valve-set flows)
       (sort-by second)
       (take-last 256) ; narrow down the search space
       tandem-scores
       (reduce max)))

(defn parse-input [input]
  (let [[all-flows direct-conns]
        (->> (aoc/read-input input)
             (mapv parse-line)
             create-connections)
        conns  (floyd-warshall (keys all-flows) direct-conns)
        flows (->> all-flows
                   (filter #(pos? (val %)))
                   (into {}))
        valves (set (keys flows))
        traverse_  (partial traverse "AA" valves conns {})]
    [traverse_ flows]))

(defn solve
  ([] (solve 16))
  ([input]
   (let [data (parse-input input)]
     [(part-1 data)
      (part-2 data)])))


(solve)
