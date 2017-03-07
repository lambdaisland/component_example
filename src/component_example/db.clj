(ns component-example.db)

(defn query [^java.sql.Connection conn ^String qry]
  (let [rs (.. conn createStatement (executeQuery qry))
        columns (.. rs getMetaData getColumnCount)]
    (loop [result []]
      (if (.next rs)
        (recur (conj result (mapv #(.getObject rs (inc %)) (range columns))))
        result))))

(defn execute! [^java.sql.Connection conn ^String qry]
  (.. conn createStatement (execute qry)))
