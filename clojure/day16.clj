(ns day16
  (:require aoc
            [clojure.data.int-map :refer [int-map]]))


(defn parse-valve [valve]
  (reduce
   (fn [^long acc l]
     (+ (* 26 acc)
        (- (int l) 65)))
   0
   valve))

(defn connection-hash ^long [^long v1 ^long v2]
  (+ (* 26 26 v1) v2))

(defn parse-line [line]
  (let [[valve flow & connections] (re-seq #"[A-Z]{2}|\d+" line)]
    [(parse-valve valve) (parse-long flow) connections]))

(defn create-connections [input]
  (reduce
   (fn [[flows connections] [valve flow conns]]
     [(assoc flows valve flow)
      (reduce #(assoc %1 (connection-hash valve (parse-valve %2)) 1)
              connections
              conns)])
   [(int-map) (int-map)]
   input))

(defn floyd-warshall [valves connections]
  (reduce
   (fn [conns [a b c]]
     (let [bc (connection-hash b c)
           ba (connection-hash b a)
           ac (connection-hash a c)]
       (if-not (distinct? a b c) conns
               (assoc conns bc
                      (min (conns bc 999)
                           (+ (conns ba 999)
                              (conns ac 999)))))))
   connections
   (for [a valves , b valves , c valves] [a b c])))



(defn traverse [current closed-valves conns visited time]
  (let [res [visited]]
    (if (< time 2) res
        (apply concat res
               (for [valve closed-valves
                     :let [t (- ^long time
                                ^long (conns (connection-hash current valve))
                                1)]
                     :when (> t 1)]
                 (traverse valve (disj closed-valves valve) conns (assoc visited valve t) t))))))


(defn score [flows attempt]
  (->> attempt
       (map (fn [[valve time]]
              (* ^long (flows valve) ^long time)))
       (reduce +)))

(defn best-score-per-valve-set [flows attempts]
  (->> attempts
       (map (fn [attempt]
              [(set (keys attempt)) (score flows attempt)]))
       (reduce (fn [top-scores [k v]]
                   (assoc! top-scores k (max (top-scores k 0) v)))
               (transient {}))
       persistent!))

(defn tandem-scores [top-scores]
  (for [[^long i [h_vis ^long h_score]] (map-indexed vector top-scores)
        [^long j [e_vis ^long e_score]] (map-indexed vector top-scores)
        :while (> i j)
        :when (aoc/none? h_vis e_vis)]
    (+ h_score e_score)))



(defn part-1 [[valves conns flows]]
  (->> (traverse 0 valves conns {} 30)
       (map (partial score flows))
       (reduce max)))

(defn part-2 [[valves conns flows]]
  (->> (traverse 0 valves conns {} 26)
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
        valves (set (keys flows))]
    [valves conns flows]))

(defn solve
  ([] (solve 16))
  ([input]
   (let [data (parse-input input)
         p1 (future (part-1 data))
         p2 (future (part-2 data))]
     [@p1 @p2])))


(solve)
