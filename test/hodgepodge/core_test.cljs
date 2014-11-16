(ns hodgepodge.core-test
  (:require [hodgepodge.core :refer [session-storage local-storage clear!]]))

(enable-console-print!)

(print "Clearing local and session storage makes them empty")
(clear! local-storage)
(clear! session-storage)
(assert (= 0 (count local-storage)))
(assert (= 0 (count session-storage)))

; Local storage

(print "Keywords can be stored, retrieved and deleted")
; insert
(assoc! local-storage :foo :bar)
(assert (= 1 (count local-storage)))
(assert (contains? local-storage :foo))
; fetch
(assert (= :bar (get local-storage :foo)))
; delete
(dissoc! local-storage :foo)
(assert (= 0 (count local-storage)))

(print "Dates can be stored, retrieved and deleted")
(def date (js/Date.))
; insert
(assoc! local-storage {:foo :bar} date)
(assert (= 1 (count local-storage)))
(assert (contains? local-storage {:foo :bar}))
; fetch
(assert (= date (get local-storage {:foo :bar})))
; delete
(dissoc! local-storage {:foo :bar})
(assert (= 0 (count local-storage)))
