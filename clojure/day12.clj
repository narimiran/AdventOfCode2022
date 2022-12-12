(ns day12
  (:require aoc))


(defn parse-line [line]
  (mapv #(-(int %) (int \a)) line))

(defn find-val [grid height]
  (->> grid
       (filter #(= height (val %)))
       first
       key))

(defn travel [grid part]
  (let [start (find-val grid -14)
        end   (find-val grid -28)
        grid  (merge grid {start 0 end 25})]
    (loop [seen #{end}
           queue [[0 end]]]
      (let [[steps curr] (first queue)]
        (if (and (= (grid curr) 0)
                 (or (= part 2) (= curr start)))
          steps
          (let [nbs (filter #(and (not (seen %))
                                  (>= (get grid % -99)
                                      (dec (grid curr))))
                            (aoc/neighbours curr 4))
                nexts (map #(vector (inc steps) %) nbs)]
            (recur (reduce conj seen nbs)
                   (reduce conj (vec (rest queue)) nexts))))))))


(def grid
  (->> (aoc/read-input 12 {:datatype :vector})
       (mapv parse-line)
       (aoc/vec2d->grid)))

[(travel grid 1)
 (travel grid 2)]
