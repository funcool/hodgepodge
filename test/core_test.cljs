(ns hodgepodge.core-test
  (:require [cemerick.cljs.test :as t]
            [hodgepodge.core :as h])
  (:require-macros [cemerick.cljs.test :refer (deftest testing is)]))


(deftest hodgepodge-test
  (testing "We can get the count of the local-storage object"
    (is (= 0
           (count h/local-storage)))))
