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

(defn new-routes [{:keys [database] :as endpoint}]
  (routes
   (GET "/" [] {:status 200
                :body "OK" #_(pr-str (db/query (:connection database)
                                               "SELECT * FROM widgets"))})))

(def config {:port 4567
             :pg-config (assoc pg/DEFAULT-DB-SPEC
                               :subname "component_example"
                               :user "component_example"
                               :password "component_example")})

(defn new-system [{:keys [port pg-config]}]
  (component/system-map
   :endpoint (component/using (new-endpoint new-routes) {:database :db})
   :web (component/using (new-jetty-web-server port) [:endpoint])
   :db  (pg/new-postgres-database pg-config)))

(def system (new-system config))

(alter-var-root #'system component/stop)

(alter-var-root #'system component/start)

(inspect-system system)
;;=>
{:endpoint (:routes-fn :database :routes)
 :web (:port :endpoint :jetty)
 :db (:db-spec :connection :init-fn)}
