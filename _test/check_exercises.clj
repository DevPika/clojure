(ns check-exercises
  (:require [cheshire.core :as json]
            [clojure.string :as string]
            [clojure.test :refer :all]))

(let [problems (-> (slurp "config.json")
                   (json/parse-string true)
                   :problems
                   (->> (remove #{"bank-account"})))]
  (doseq [problem  problems]
    (load-file (str problem "/example.clj"))
    (load-file (str problem "/" (string/replace problem \- \_) "_test.clj")))
  (->> (for [problem problems] (symbol (str problem "-test")))
       (apply run-tests)))
