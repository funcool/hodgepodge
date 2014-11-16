(ns hodgepodge.core
  (:require [cljs.reader :as reader]))


; Crude storage API

(def local-storage js/localStorage)
(def session-storage js/sessionStorage)

(defn contains-key?
  [storage key]
  (let [ks (.keys js/Object storage)
        idx (.indexOf ks key)]
    (>= idx 0)))

(defn get-item
  ([storage key]
     (get-item storage key nil))
  ([storage key default]
     (if (contains-key? storage key)
       (.getItem storage key)
       default)))

(defn set-item
  [storage key val]
  (.setItem storage key val))

(defn remove-item
  [storage key]
  (.removeItem storage key))

(defn length
  [storage]
  (.-length storage))

(defn clear! [storage]
  (.clear storage))

; Transient storage

(defn serialize [v]
  (binding [*print-dup* true
            *print-readably* true]
    (pr-str v)))

(def deserialize
  (memoize reader/read-string))

(extend-type js/Storage
  ICounted
  (-count [s]
    (length s))

  ITransientAssociative
  (-assoc! [s key val]
    (set-item s (serialize key) (serialize val)))

  ITransientMap
  (-dissoc! [s key]
    (remove-item s (serialize key)))

  ILookup
  (-lookup
    ([s key]
       (-lookup s key nil))
    ([s key not-found]
       (if (contains-key? s (serialize key))
         (deserialize (get-item s (serialize key)))
         not-found))))
