(ns component-example.components.jetty
  (:require [com.stuartsierra.component :as component]
            [ring.adapter.jetty :as ring-jetty]))

(defrecord JettyWebServer [port]
  component/Lifecycle
  (start [component]
    (if (:jetty component)
      component
      (assoc component
             :jetty (ring-jetty/run-jetty (-> component :endpoint :routes)
                                          {:port port :join? false}))))
  (stop [component]
    (when-let [jetty (:jetty component)]
      (.stop jetty))
    (dissoc component :jetty)))

(defn new-jetty-web-server [port]
  (map->JettyWebServer {:port port}))
