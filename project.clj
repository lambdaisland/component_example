(defproject component-example "0.1.0-SNAPSHOT"
  :license {:name "Mozilla Public License 2.0"
            :url "https://www.mozilla.org/en-US/MPL/2.0/"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.stuartsierra/component "0.3.2"]
                 [ring/ring-jetty-adapter "1.5.0"]
                 [compojure "1.5.2"]
                 [org.danielsz/system "0.4.0"]
                 [postgresql "9.3-1102.jdbc41"]
                 [org.clojure/java.jdbc "0.6.1"]])
