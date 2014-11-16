(ns hodgepodge.core
  (:require [cljs.reader :as reader]))


; Crude storage API

(def local-storage js/localStorage)
(def session-storage js/sessionStorage)

(defn contains-key?
  [^Storage storage ^string key]
  (let [ks (.keys js/Object storage)
        idx (.indexOf ks key)]
    (>= idx 0)))

(defn get-item
  ([^Storage storage ^string key]
     (get-item storage key nil))
  ([^Storage storage ^string key ^string default]
     (if (contains-key? storage key)
       (.getItem storage key)
       default)))

(defn set-item
  [^Storage storage ^string key ^string val]
  (.setItem storage key val))

(defn remove-item
  [^Storage storage ^string key]
  (.removeItem storage key))

(defn length
  [^Storage storage]
  (.-length storage))

(defn clear!
  [^Storage storage]
  (.clear storage))

; Transient storage

(defn serialize [v]
  (binding [*print-dup* true]
    (pr-str v)))

(def deserialize
  (memoize reader/read-string))

(extend-type js/Storage
  ICounted
  (-count [^Storage s]
    (length s))

  ITransientAssociative
  (-assoc! [^Storage s key val]
    (set-item s (serialize key) (serialize val)))

  ITransientMap
  (-dissoc! [^Storage s key]
    (remove-item s (serialize key)))

  ILookup
  (-lookup
    ([^Storage s key]
       (-lookup s key nil))
    ([^Storage s key not-found]
       (if (contains-key? s (serialize key))
         (deserialize (get-item s (serialize key)))
         not-found))))
