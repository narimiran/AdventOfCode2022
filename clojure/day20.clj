(ns day20
  (:require aoc))


(defrecord Link [val left right])


(defn nums->links [values]
  (let [links (mapv #(->Link % (atom nil) (atom nil)) values)
        n     (count values)]
    (reset! (:left (first links)) (links (dec n)))
    (dotimes [i (dec n)]
      (reset! (:left (links (inc i))) (links i))
      (reset! (:right (links i)) (links (inc i))))
    (reset! (:right (peek links)) (links 0))
    links))

(defn unlink [link]
  (let [left  @(:left  link)
        right @(:right link)]
    (reset! (:right left) right)
    (reset! (:left right) left)))

(defn rotate-left [link ^long steps]
  (if (zero? steps) link
      (recur @(:right link) (dec steps))))

(defn rotate-right [link ^long steps]
  (if (zero? steps) link
      (recur @(:left link) (dec steps))))

(defn insert [link left]
  (let [right @(:right left)]
    (reset! (:right left) link)
    (reset! (:left  link) left)
    (reset! (:right link) right)
    (reset! (:left right) link)))

(defn move-element [links i]
  (let [n     (count links)
        link  (links i)
        val   (:val link)
        steps (mod val (dec n))]
    (if (zero? steps) links
        (let [left (if (< steps (/ n 2))
                     (rotate-left  link steps)
                     (rotate-right link (- n steps)))]
          (unlink link)
          (insert link left)
          links))))

(defn find-zero [links]
  (aoc/find-first #(zero? (:val %)) links))

(defn mix [links rounds]
  (let [n       (count links)
        links'  (->> links
                     (iterate #(reduce move-element % (range n)))
                     (#(nth % rounds)))
        zero    (find-zero links')
        nth-val (fn [x] (->> (mod x n)
                             (rotate-left zero)
                             :val))]
    (->> [1000 2000 3000]
         (map nth-val)
         (reduce +))))

(defn solve
  ([] (solve (aoc/read-file 20)))
  ([input]
   (let [nums   (aoc/parse-input input :int)
         links1 (nums->links nums)
         links2 (nums->links (map #(* % 811589153) nums))
         p1     (future (mix links1 1))
         p2     (future (mix links2 10))]
     [@p1 @p2])))


(solve)
