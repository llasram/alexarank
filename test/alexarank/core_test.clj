(ns alexarank.core-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [cheshire.core :as json]
            [alexarank.core :as arc]))

(deftest test-handler-basic
  (let [handler (arc/->handler)]
    (is (= {:status  200
            :headers {"Content-Type" "application/json; charset=utf-8"}
            :body (json/encode {"domain" nil, "rank" nil})}
           (handler (mock/request :get "/example.com"))))))
