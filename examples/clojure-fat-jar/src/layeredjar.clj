(ns layeredjar
    (:gen-class)
    (:require [io.pedestal.http :as http]
      [io.pedestal.http.route :as route]))

(defn respond-hello [request]
      {:status 200 :body "{\"message\":\"Layered JARS are great!!\"}"})

(def routes
  (route/expand-routes
    #{["/" :get respond-hello :route-name :greet]}))

(defn create-server []
      (http/create-server
        {::http/routes routes
         ::http/type   :jetty
         ::http/port   8080}))

(defn start []
      (http/start (create-server)))

(defn -main [& args]
      (start))
