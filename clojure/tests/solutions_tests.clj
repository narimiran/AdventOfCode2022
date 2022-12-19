(ns solutions-tests
  (:require day01 day02 day03 day04 day05
            day06 day07 day08 day09 day10
            day11 day12 day13 day14 day15
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


(def test-output-10
  '("████    ████    ████    ████    ████    ████    ████    ████    ████    ████    "
    "██████      ██████      ██████      ██████      ██████      ██████      ██████  "
    "████████        ████████        ████████        ████████        ████████        "
    "██████████          ██████████          ██████████          ██████████          "
    "████████████            ████████████            ████████████            ████████"
    "██████████████              ██████████████              ██████████████          "))

(def real-output-10
  '("██████      ████    ██████    ██████    ██    ██    ████    ██████        ████  "
    "██    ██  ██    ██  ██    ██  ██    ██  ██  ██    ██    ██  ██    ██        ██  "
    "██    ██  ██        ██    ██  ██████    ████      ██    ██  ██    ██        ██  "
    "██████    ██        ██████    ██    ██  ██  ██    ████████  ██████          ██  "
    "██        ██    ██  ██        ██    ██  ██  ██    ██    ██  ██        ██    ██  "
    "██          ████    ██        ██████    ██    ██  ██    ██  ██          ████    "))


(check-day 1 [24000 45000] [70369 203002])
(check-day 2 [15 12] [13005 11373])
(check-day 3 [157 70] [8515 2434])
(check-day 4 [2 4] [569 936])
(check-day 5 ["CMZ" "MCD"] ["LBLVVTVLP" "TPFFBDRJD"])
(check-day 6 [7 19] [1287 3716])
(check-day 7 [95437 24933642] [1444896 404395])
(check-day 8 [21 8] [1829 291840])
(check-day 9 [13 1] [6197 2562])
(check-day 10 [13140 test-output-10] [14320 real-output-10])
(check-day 11 [10605 2713310158] [66802 21800916620])
(check-day 12 [31 29] [352 345])
(check-day 13 [13 140] [5013 25038])
(check-day 14 [24 93] [1003 25771])
(check-day 15 [26 56000011] [5299855 13615843289729])


(run-tests)
