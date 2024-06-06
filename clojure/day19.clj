(ns day19
  (:require aoc
            [clojure.data.int-map :refer [dense-int-set]]))


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
  (reduce-kv
   (fn [rscs rsc cost]
     (assoc rscs rsc (- (resources rsc) cost)))
   resources
   price))

(defn no-negatives? [resources]
  (reduce-kv
   (fn [positive _ v]
     (if (neg? (long v))
       (reduced false)
       positive))
   true
   resources))


(defn calc-hash ^long [t bots resources]
  ;; "Hashing" function to avoid having a nested vector
  ;; as a hash-map key.
  ;;
  ;; Very fragile! Don't touch the coefficients,
  ;; i.e. I need a more robust way of doing this.
  (+ ^long t
     (* 33 (+ (* 2 ^long (:ore bots))
              (* 5 ^long (:clay bots))
              (* 13 ^long (:obs bots))
              (* 37 ^long (:geode bots))))
     (* 1151 (+ (* 3 ^long (:ore resources))
                (* 5 ^long (:clay resources))
                (* 11 ^long (:obs resources))
                (* 31 ^long (:geode resources))))))

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
                  (->> resources
                       (reduce-kv (fn [acc res v]
                                    (assoc! acc res
                                            (min (* (max-resources res) t)
                                                 (+ v (bots res)))))
                                  (transient resources))
                       persistent!))]
    (loop [stack (list state)
           seen (transient (dense-int-set))
           score 0]
      (if-let [state (first stack)]
        (let [{:keys [t bots resources skipped]} state
              state-hash (calc-hash t bots resources)
              stack' (rest stack)]
          (if (or (zero? t)
                  (seen state-hash)
                  (< (best-case (resources :geode) (bots :geode) t) score))
            (recur stack' (conj! seen state-hash) (max score ^long (resources :geode)))
            (let [[stack'' skipped]
                  (reduce (fn [[stack can-build] bot]
                            (let [rscs (build-bot resources (prices bot))]
                              (if (and (not (skipped bot))
                                       (> t (times bot))
                                       (no-negatives? rscs)
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
              (recur stack (conj! seen state-hash) score))))
        score))))

(defn part-1 [blueprints]
  (->> blueprints
       (pmap #(geodes 24 %))
       (mapv * (iterate inc 1))
       (reduce +)))

(defn part-2 [blueprints]
  (->> blueprints
       (take 3)
       (pmap #(geodes 32 %))
       (reduce *)))

(defn solve
  ([] (solve (aoc/read-file 19)))
  ([input]
   (let [blueprints (aoc/parse-input input (comp line->blueprint aoc/integers))
         p1 (future (part-1 blueprints))
         p2 (future (part-2 blueprints))]
     [@p1 @p2])))


(time (solve))
