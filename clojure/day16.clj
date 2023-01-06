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
  (reduce-kv
   (fn [acc valve time]
     (+ acc (* (flows valve) time)))
   0
   attempt))

(defn best-score-per-valve-set [flows attempts]
  (reduce
   (fn [top-scores attempt]
     (let [k   (set (keys attempt))
           v   (score flows attempt)
           old (get top-scores k 0)]
       (assoc top-scores k (max v old))))
   {}
   attempts))

(defn tandem-scores [top-scores]
  (for [[i [h_vis h_score]] (map-indexed vector top-scores)
        [j [e_vis e_score]] (map-indexed vector top-scores)
        :while (> i j)
        :when (not-any? h_vis e_vis)]
    (+ h_score e_score)))



(defn part-1 [release-pressure flows]
  (->> (release-pressure 30)
       (map (partial score flows))
       (reduce max)))

(defn part-2 [release-pressure flows]
  (->> (release-pressure 26)
       (best-score-per-valve-set flows)
       tandem-scores
       (reduce max)))

(defn solve
  ([] (solve 16))
  ([input]
   (let [[all-flows direct-conns]
         (->> (aoc/read-input input)
              (map parse-line)
              create-connections)
         flows (->> all-flows
                    (filter #(pos? (val %)))
                    (into {}))
         valves (set (keys flows))
         conns  (floyd-warshall (keys all-flows) direct-conns)
         release-pressure  (partial traverse "AA" valves conns {})]
     [(part-1 release-pressure flows)
      (part-2 release-pressure flows)])))


(solve)
