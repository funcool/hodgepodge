(ns hodgepodge.core
  (:require [cljs.reader :as reader]))


; Crude storage API

(def local-storage js/localStorage)
(def session-storage js/sessionStorage)

(defn contains-key?
  [^js/Storage storage ^string key]
  (let [ks (.keys js/Object storage)
        idx (.indexOf ks key)]
    (>= idx 0)))

(defn get-item
  ([^js/Storage storage ^string key]
     (get-item storage key nil))
  ([^js/Storage storage ^string key ^string default]
     (if (contains-key? storage key)
       (.getItem storage key)
       default)))

(defn set-item
  [^js/Storage storage ^string key ^string val]
  (.setItem storage key val))

(defn remove-item
  [^js/Storage storage ^string key]
  (.removeItem storage key))

(defn length
  [^js/Storage storage]
  (.-length storage))

(defn clear!
  [^js/Storage storage]
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
  (-count [^js/Storage s]
    (length s))

  ITransientAssociative
  (-assoc! [^js/Storage s key val]
    (set-item s (serialize key) (serialize val)))

  ITransientMap
  (-dissoc! [^js/Storage s key]
    (remove-item s (serialize key)))

  ILookup
  (-lookup
    ([^js/Storage s key]
       (let [sk (serialize key)]
         (when (contains-key? s sk)
           (deserialize (get-item s sk)))))
    ([^js/Storage s key not-found]
       (let [sk (serialize key)]
         (if (contains-key? s sk)
           (deserialize (get-item s sk))
           not-found)))))
