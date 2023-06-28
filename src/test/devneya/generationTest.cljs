;; (ns devneya.generationTest
;;   (:require [clojure.test :refer :all]
;;             [devneya.core :as core]
;;             [devneya.codeGeneration :as gen]
;;             [failjure.core :as f]
;;             [devneya.gpt :as gpt]))

;; (def hello-world-returner
;;   (let [n (atom 0)]
;;     (with-meta
;;       (fn [config date prompt]
;;         (swap! n inc)
;;         "console.log(\"Hello, world!\");")
;;       {::call-count (fn [] @n)})))

;; (def wrong-returner
;;   (let [n (atom 0)]
;;     (with-meta
;;       (fn [config date prompt]
;;         (swap! n inc)
;;         "There is no code!")
;;       {::call-count (fn [] @n)})))

;; (deftest make-prompt-chain-test
;;     (testing "Got correct code from the first call."
;;       (with-redefs [gpt/get-chatgpt-api-response hello-world-returner]
;;         (is (nil? (gen/generate-code-async "" "" ""))))
;;         (is (= ((::call-count (meta hello-world-returner))) 1)))

;;       (testing "Never got correct code."
;;         (is (f/failed?
;;              (with-redefs [gpt/get-chatgpt-api-response wrong-returner]
;;                (gen/generate-code-async core/config "test-filename" ""))))
;;         (is (= ((::call-count (meta wrong-returner))) (:MAX_REPS core/config)))))