(ns hodgepodge.core
  (:require [cljs.reader :as reader]))

(enable-console-print!)

; Storages

(def local-storage js/localStorage)
(def session-storage js/sessionStorage)

; Serialization & deserialization

(defmulti serialize type)
(defmethod serialize :default
   [v]
   (str v))

(defmethod serialize js/Date
  [date]
  (str {:type :js/date
        :value (.toString date)}))

(defmulti deserialize (comp :type reader/read-string))
(defmethod deserialize :js/date
  [v]
  (let [{:keys [value]} (reader/read-string  v)]
    (js/Date.  (.parse js/Date value))))

(defmethod deserialize :default
  [v]
  (reader/read-string v))

(extend-type js/Storage
  ICounted
  (-count [s]
    (.-length s))

  ITransientAssociative
  (-assoc! [s key val]
    (.setItem s
              (serialize key)
              (serialize val)))

  ITransientMap
  (-dissoc! [s key]
    (.removeItem s
                 (serialize key)))

  ILookup
  (-lookup
    ([s key]
       (some->
        (.getItem s (serialize key))
        deserialize))
    ([s key not-found]
       (if-let [i (.getItem s (serialize key))]
         (deserialize i)
         not-found)))
)

(defn clear! [storage] (.clear storage))

(comment
  (clear! local-storage)
  (clear! session-storage)

  (assert (= 0 (count local-storage)))
  (assert (= 0 (count session-storage)))

  (assoc! local-storage :foo :bar)
  (assert (= 1 (count local-storage)))
  (assert (contains? local-storage :foo))
  (assert (= 0 (count session-storage)))

  (dissoc! local-storage :foo)
  (assert (= 0 (count local-storage)))
  (assert (= 0 (count session-storage)))

  (assoc! local-storage {:foo :bar} (js/Date.))
  (assert (= 1 (count local-storage)))
  (assert (contains? local-storage {:foo :bar}))
  (assert (= 0 (count session-storage)))
)
