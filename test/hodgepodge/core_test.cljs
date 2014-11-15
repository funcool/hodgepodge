(ns hodgepodge.core-test
  (:require [hodgepodge.core :as h]))

(h/clear! h/local-storage)
(h/clear! h/session-storage)

(assert (= 0 (count h/local-storage)))
(assert (= 0 (count h/session-storage)))

(assoc! h/local-storage :foo :bar)
(assert (= 1 (count h/local-storage)))
(assert (contains? h/local-storage :foo))
(assert (= 0 (count h/session-storage)))

(dissoc! h/local-storage :foo)
(assert (= 0 (count h/local-storage)))
(assert (= 0 (count h/session-storage)))

(assoc! h/local-storage {:foo :bar} (js/Date.))
(assert (= 1 (count h/local-storage)))
(assert (contains? h/local-storage {:foo :bar}))
(assert (= 0 (count h/session-storage)))

(def val {:bar 42 :timestamp {:bar (js/Date.) :baz :safd :frob #{1 2 3}}})
(assoc! h/local-storage :foo val)
(assert (= val (get h/local-storage :foo)))

(assoc! h/local-storage val :foo)
(assert (= :foo (get h/local-storage val)))

(def date (js/Date.))
(assoc! h/local-storage :date date)
