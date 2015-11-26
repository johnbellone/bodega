(ns bodega.endpoint.cookbooks
  (:require [compojure.core :refer :all]
            [schema.core :as s]))

(s/defschema Cookbook
  {:id String
   :name String})

(defn cookbooks-endpoint []
  (routes
   (GET "/universe" [])
   (GET "/cookbooks" [])))
