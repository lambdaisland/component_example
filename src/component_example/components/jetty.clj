(ns component-example.components.jetty
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as ring-jetty]))

(defrecord JettyWebServer [handler port]
  component/Lifecycle
  (start [component]
    (if (:jetty component)
      component
      (assoc component
             :jetty (ring-jetty/run-jetty handler {:port port :join? false}))))
  (stop [component]
    (when-let [jetty (:jetty component)]
      (.stop jetty))
    (dissoc component :jetty)))

(defn new-jetty-web-server [handler port]
  (map->JettyWebServer {:handler handler :port port}))
