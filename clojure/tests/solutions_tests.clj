(ns solutions-tests
  (:require day01 day02 day03 day04 day05
            [clojure.test :refer [deftest are run-tests]]))


(defmacro check-day [day test-results real-results]
  (let [day        (format "%02d" day)
        full-day   (str "day" day)
        solve-fn   (symbol (str full-day "/solve"))
        test-name  (symbol (str full-day "-test"))
        test-input (str day "_test")
        real-input day]
    `(deftest ~test-name
       (are [input output] (= (~solve-fn input) output)
         ~test-input ~test-results
         ~real-input ~real-results))))


(check-day 1 [24000 45000] [70369 203002])
(check-day 2 [15 12] [13005 11373])
(check-day 3 [157 70] [8515 2434])
(check-day 4 [2 4] [569 936])
(check-day 5 ["CMZ" "MCD"] ["LBLVVTVLP" "TPFFBDRJD"])


(run-tests)
