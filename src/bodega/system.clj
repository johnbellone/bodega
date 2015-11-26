(ns bodega.system
  (:require [clojure.java.io :as io]
            [com.stuartsierra.component :as component]
            [duct.component.endpoint :refer [endpoint-component]]
            [duct.component.handler :refer [handler-component]]
            [duct.component.hikaricp :refer [hikaricp]]
            [duct.component.ragtime :refer [ragtime]]
            [duct.middleware.not-found :refer [wrap-not-found]]
            [meta-merge.core :refer [meta-merge]]
            [ring.component.jetty :refer [jetty-server]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [bodega.endpoint.cookbooks :refer [cookbooks-endpoint]]
            [bodega.endpoint.status :refer [status-endpoint]]))

(def base-config
  {:app {:middleware [[wrap-not-found :not-found]
                      [wrap-defaults :defaults]]
         :not-found (io/resource "bodega/errors/404.html")
         :defaults (meta-merge site-defaults {:static {:resources "bodega/public"}})}
   :ragtime {:resource-path "bodega/migrations"}})

(defn new-system [config]
  (let [config (meta-merge base-config config)]
    (-> (component/system-map
         :app  (handler-component (:app config))
         :http (jetty-server (:http config))
         :db   (hikaricp (:db config))
         :ragtime (ragtime (:ragtime config))
         :cookbooks (endpoint-component cookbooks-endpoint)
         :status (endpoint-component status-endpoint))
        (component/system-using
         {:http [:app]
          :app  [:cookbooks :status]
          :ragtime [:db]
          :cookbooks [:db]
          :status [:db]}))))
