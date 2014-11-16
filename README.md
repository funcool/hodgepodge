hodgepodge
==========

[![Travis Badge](https://img.shields.io/travis/dialelo/hodgepodge.svg?style=flat)](https://travis-ci.org/dialelo/hodgepodge "Build in Travis-CI")

A idiomatic ClojureScript interface to HTML5 Storage

```clojure
(require [hodgepodge.core :refer [local-storage]])

(def val {:bar 42 :timestamp (js/Date.)})
(assoc! local-storage :foo val)
(assert (= val (get local-storage :foo)))

(assoc! local-storage val :foo)
(assert (= :foo (get local-storage val)))
```
