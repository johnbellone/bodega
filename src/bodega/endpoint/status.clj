(ns bodega.endpoint.status
  (:require [compojure.core :refer :all]
            [schema.core :as s]))

(defn status-endpoint []
  (routes
   (GET "/_status" [])))
