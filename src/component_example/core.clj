(ns component-example.core
  (:require [com.stuartsierra.component :as component]
            [component-example.components.jetty
             :refer [new-jetty-web-server]]
            [component-example.components.endpoint
             :refer [new-endpoint]]
            [compojure.core :refer [GET routes]]
            [component-example.util :refer [inspect-system]]
            [system.components.postgres :as pg]
            [component-example.db :as db]))

(defn new-routes [{:keys [db]}]
  (routes
   (GET "/" [] {:status 200
                :body "OK"})))

(def config {:port 4567
             :pg-config (assoc pg/DEFAULT-DB-SPEC
                               :subname "component_example"
                               :user "component_example"
                               :password "component_example")})

(defn new-system [{:keys [port pg-config]}]
  (component/system-map
   :endpoint (new-endpoint new-routes)
   :web (new-jetty-web-server (new-routes) port)
   :db  (pg/new-postgres-database pg-config)))

(def system (new-system config))

#_
(alter-var-root #'system component/stop)
#_
(alter-var-root #'system component/start)
