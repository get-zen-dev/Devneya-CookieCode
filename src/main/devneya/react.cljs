(ns devneya.react
  (:require [reagent.dom :as rdom]
            [reagent.core :as r]
            [failjure.core :as f]
            [devneya.prompt :as prompt]
            [devneya.utils :refer [date-hms log-with-id]]
            [cljs.core.async :refer [<!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn- request-page []
  (let [prompt (r/atom "")
        response (r/atom "")
        openai-key (r/atom "")]
    (fn []
      [:div
       [:header
        [:h1 "Devneya"]]
       [:main
        [:section.type-a
         [:h2 "Enter prompt"]
         [:label {:for "prompt"} "Prompt:"]
         [:input {:type "text"
                  :id "prompt"
                  :name "prompt"
                  :value @prompt
                  :on-change #(reset! prompt (-> % .-target .-value))}]
         [:br]
         [:label {:for "api-key"} "OpenAI api key:"]
         [:input {:type "text"
                  :id "openai-key"
                  :name "openai key"
                  :value @openai-key
                  :on-change #(reset! openai-key (-> % .-target .-value))}]
         [:br]
         [:button
          {:type "submit"
           :on-click #(go 
                        (let [current-time (date-hms)
                              resp (f/attempt f/message (<! (prompt/make-prompt-chain
                                                             current-time
                                                             @openai-key
                                                             3
                                                             @prompt)))]
                          (log-with-id current-time (str "returned on click to react: " resp))
                          (reset! response resp)))}
          "Submit"]]
        [:section.type-b
        [:input {:type "response"
                 :id "response"
                 :name "response"
                 :value @response}]]]])))

(defn run-react []
  (rdom/render
   [request-page]
   (.-body js/document)))