(ns alexarank.core
  (:require [clojure.java.io :as io]
            [clojure.tools.logging :as log]
            [com.stuartsierra.component :as component]
            [cheshire.core :as json]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.util.response :refer [response]]
            [ring.adapter.jetty :refer [run-jetty]]))

(def default-psl-url
  "https://publicsuffix.org/list/effective_tld_names.dat")

(def default-alexa-url
  "http://s3.amazonaws.com/alexa-static/top-1m.csv.zip")

(def default-port
  "Default HTTP server port."
  8080)

(defn handler
  "Core API ring handler function."
  [req]
  (response {:domain nil, :rank nil}))

(defn ->handler
  "Build full ring handler chain."
  []
  (-> handler
      wrap-json-response))

(defrecord HttpApi [config]
  component/Lifecycle
  (start [this]
    (log/info "Starting HTTP API")
    (let [handler (->handler)]
      (assoc this :handler handler)))
  (stop [this]
    (log/info "Stopping HTTP API")
    (assoc this :handler nil)))

(defn api
  "Build API component for configuration map `config`."
  [config] (map->HttpApi {:config config}))

(defrecord HttpServer [config api server]
  component/Lifecycle
  (start [this]
    (log/info "Starting HTTP server")
    (let [handler (:handler api), port (:port config default-port)
          server (run-jetty handler {:join? false :port port})]
      (assoc this :server server)))
  (stop [this]
    (log/info "Stopping HTTP server")
    (some-> server .stop)
    (assoc this :server nil)))

(defn server
  "Build server component for configuration map `config`."
  [config] (map->HttpServer {:config config}))

(defn system
  "Build system map for application from configuration map `config`."
  [config]
  (component/system-map
   :api (component/using (api config) [])
   :server (component/using (server config) [:api])))

(defn -main
  [& args]
  (let [[config-path] args
        config (with-open [r (io/reader config-path)]
                 (json/parse-stream r))
        system (-> config system component/start)]
    (-> system :server :server .join)))
