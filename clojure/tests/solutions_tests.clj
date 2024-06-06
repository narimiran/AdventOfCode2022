(ns solutions-tests
  (:require day01 day02 day03 day04 day05
            day06 day07 day08 day09 day10
            day11 day12 day13 day14 day15
            day16 day17 day18 day19 day20
            day21 day22 day23 day24 day25
            [clojure.test :refer [deftest is run-tests successful?]]))


(defmacro check-day [day test-results real-results]
  (let [day        (format "%02d" day)
        full-day   (str "day" day)
        solve-fn   (symbol (str full-day "/solve"))
        test-name  (symbol (str full-day "-test"))
        test-input (str day "_test")
        real-input day]
    `(deftest ~test-name
       (when ~test-results
         (is (= ~test-results (~solve-fn (aoc/read-file ~test-input)))))
       (is (= ~real-results (~solve-fn (aoc/read-file ~real-input)))))))




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
(check-day 15 nil [5299855 13615843289729])
(check-day 16 [1651 1707] [1880 2520])
(check-day 17 [3068 1514285714288] [3232 1585632183915])
(check-day 18 [64 58] [3326 1996])
(check-day 19 [33 3472] [994 15960])
(check-day 20 [3 1623178306] [5904 8332585833851])
(check-day 21 [152 301] [63119856257960 3006709232464])
(check-day 22 nil [13566 11451])
(check-day 23 [110 20] [3762 997])
(check-day 24 [18 54] [266 853])
(check-day 25 "2=-1=0" "122-2=200-0111--=200")


(let [summ (run-tests)]
  (when-not (successful? summ)
    (throw (Exception. "tests failed"))))
