(ns day19
  (:require aoc))


(defn geodes [costs t]
  (let [[ore-ore clay-ore obs-ore obs-clay geode-ore geode-obs] costs
        prices {:ore   {:ore ore-ore}
                :clay  {:ore clay-ore}
                :obs   {:ore obs-ore   , :clay obs-clay}
                :geode {:ore geode-ore , :obs geode-obs}}
        resources {:ore 0 , :clay 0 , :obs 0 , :geode 0}
        bots  {:ore 1 , :clay 0 , :obs 0 , :geode 0}
        types [:ore :clay :obs :geode]
        times {:ore 0 , :clay 4 , :obs 2 , :geode 0}
        max-resources {:ore (max ore-ore clay-ore obs-ore geode-ore)
                       :clay obs-clay , :obs geode-obs , :geode 9999}
        state {:t t , :bots bots , :resources resources , :skipped #{}}
        ,
        best-case (fn [resource bot time]
                    (+ resource
                       (* bot time)
                       (/ (* time (inc time)) 2)))
        build-bot (fn [resources price]
                    (reduce-kv (fn [rscs k v]
                                 (update rscs k #(- % v)))
                               resources
                               price))
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
  (reduce
   (fn [acc [bp & costs]]
     (+ acc (* bp (geodes costs 24))))
   0
   blueprints))

(defn part-2 [blueprints]
  (reduce
   (fn [acc [_ & costs]]
     (* acc (geodes costs 32)))
   1
   (take 3 blueprints)))

(defn solve
  ([] (solve 19))
  ([input]
   (let [blueprints (map aoc/integers (aoc/read-input input))]
     [(part-1 blueprints)
      (part-2 blueprints)])))


(solve)
