(ns day19
  (:require aoc))


(defrecord Blueprint [prices max-resources])


(defn line->blueprint [line]
  (let [[_ ore-ore clay-ore obs-ore obs-clay geode-ore geode-obs] line
        prices {:ore   {:ore ore-ore}
                :clay  {:ore clay-ore}
                :obs   {:ore obs-ore   , :clay obs-clay}
                :geode {:ore geode-ore , :obs geode-obs}}
        max-resources {:ore (max ore-ore clay-ore obs-ore geode-ore)
                       :clay obs-clay , :obs geode-obs , :geode 9999}]
    (->Blueprint prices max-resources)))


(defn best-case [resource bot time]
  (+ resource
     (* bot time)
     (/ (* time (inc time)) 2)))

(defn build-bot  [resources price]
  (reduce-kv (fn [rscs k v] (update rscs k #(- % v)))
             resources
             price))

(defn geodes [t bp]
  (let [types [:ore :clay :obs :geode]
        times {:ore 0 , :clay 4 , :obs 2 , :geode 0}
        state {:t t
               :bots      {:ore 1 , :clay 0 , :obs 0 , :geode 0}
               :resources {:ore 0 , :clay 0 , :obs 0 , :geode 0}
               :skipped   #{}}
        {:keys [prices max-resources]} bp
        ,
        collect (fn [resources bots t]
                  (into {} (for [res types]
                             [res (min (* (max-resources res) t)
                                       (+ (resources res) (bots res)))])))]
    (loop [stack [state]
           seen #{}
           score 0]
      (if (empty? stack) score
          (let [{:keys [t bots resources skipped]} (peek stack)
                state-hash [t bots resources]
                stack' (pop stack)]
            (if (or (zero? t)
                    (seen state-hash)
                    (< (best-case (resources :geode) (bots :geode) t) score))
              (recur stack' (conj seen state-hash) (max score (resources :geode)))
              (let [[stack'' skipped]
                    (reduce (fn [[stack can-build] bot]
                              (let [rscs (build-bot resources (prices bot))]
                                (if (and (not (skipped bot))
                                         (> t (times bot))
                                         (not-any? neg? (vals rscs))
                                         (< (bots bot) (max-resources bot))
                                         (< (resources bot) (* 1.5 (max-resources bot))))
                                  [(conj stack {:t (dec t) , :resources (collect rscs bots t)
                                                :bots (update bots bot inc) , :skipped #{}})
                                   (conj can-build bot)]
                                  [stack can-build])))
                            [stack' #{}]
                            types)
                    ,
                    stack
                    (if (or (< (resources :ore) (max-resources :ore))
                            (and (pos? (bots :clay))
                                 (< (resources :clay) (max-resources :clay)))
                            (and (pos? (bots :obs))
                                 (< (resources :obs) (max-resources :obs))))
                      (conj stack'' {:t (dec t) , :resources (collect resources bots t)
                                     :bots bots , :skipped skipped})
                      stack'')]
                (recur stack (conj seen state-hash) score))))))))


(defn part-1 [blueprints]
  (->> blueprints
       (pmap (partial geodes 24))
       (mapv * (iterate inc 1))
       (reduce +)))

(defn part-2 [blueprints]
  (->> blueprints
       (take 3)
       (pmap (partial geodes 32))
       (reduce *)))

(defn parse-input [input]
  (->> input
       aoc/read-input
       (mapv aoc/integers)
       (mapv line->blueprint)))

(defn solve
  ([] (solve 19))
  ([input]
   (let [blueprints (parse-input input)
         p1 (future (part-1 blueprints))
         p2 (future (part-2 blueprints))]
     [@p1 @p2])))


(solve)
