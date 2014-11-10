(ns hodgepodge.core)


(def local-storage js/localStorage)


(extend-type js/Storage
  ICounted
  (-count [ls]
    (.-length ls)))
