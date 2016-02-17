(defproject alexarank "0"
  :description "Alexa top 1 million rank JSON Web service"
  :url "http://github.com/llasram/alexarank"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.3.1"]
                 [com.stuartsierra/component "0.3.1"]
                 [cheshire/cheshire "5.5.0"]
                 [ring/ring "1.4.0"]
                 [ring/ring-json "0.4.0"]
                 [com.damballa/inet.data "0.5.7"]
                 [overtone/at-at "1.2.0"]
                 [org.slf4j/slf4j-api "1.7.16"]
                 [org.slf4j/slf4j-log4j12 "1.7.16"]
                 [log4j/log4j "1.2.17"]]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :test {:resource-paths ["test-resources"]}
             :dev {:dependencies [[ring/ring-mock "0.3.0"]]}})
