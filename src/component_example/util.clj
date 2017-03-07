(ns component-example.util)

(defn inspect-system [system]
  (into {} (map (fn [[k v]] [k (keys v)]) system)))
