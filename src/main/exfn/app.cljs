(ns exfn.app
  (:import goog.History)
  (:require [reagent.dom :as dom]
            [reagent.core :as rcore]
            [re-frame.core :as rf]
            [exfn.subscriptions]
            [exfn.events]
            [re-pressed.core :as rp]
            [goog.events :as gevents]
            [goog.history.EventType :as EventType]
            [goog.string.format]))

;; -- UI Logic --------------------------------------------------------------------

(defn get-cell-color [v]
  (cond
    (= 2 v) "#EEE4DA"
    (= 4 v) "#EDE0C8"
    (= 8 v) "#F2B178"
    (= 16 v) "#F59563"
    (= 32 v) "#F67C5F"
    (= 64 v) "#F65E3B"
    (= 128 v) "#EDCF72"
    (= 256 v) "#EDCC61"
    (= 512 v) "#EDC850"
    (= 1024 v) "#EDC53F"
    (= 2048 v) "#EDC22E"
    :else "rgba(238,228,218,.35)"))

;; https://github.com/gadfly361/re-pressed

;; -- App -------------------------------------------------------------------------

(defn app []
  (let [board @(rf/subscribe [:board])]
      [:div.container
       [:div.row
        [:div.col.col-lg-8
         [:h1 "2048"]]
        [:div.col.col-lg-4 {:style {:text-align    :right
                                    :padding-right 50}}
         [:i.fab.fa-github]
         [:a {:href  "https://github.com/stuartstein777/sudoku"
              :style {:text-decoration :none}}
          " (repo) "]
         "|"
         [:a {:href  "https://stuartstein777.github.io/"
              :style {:text-decoration :none}}
          " other projects"]]]
       [:div.row
        [:div.board
         (for [row board]
           [:div.row
            (for [cell row]
              [:div
               {:class (str "cell-" cell)}
               [:div.cell {:style {:background-color (get-cell-color cell)
                                   :font-size "50px"}}
                (when (not= 0 cell)
                  cell)]])])]]]))

;; -- After-Load --------------------------------------------------------------------
;; Do this after the page has loaded.
;; Initialize the initial db state.
(defn ^:dev/after-load start
  []
  (dom/render [app]
              (.getElementById js/document "app")))

(defn ^:export init []
  (start)
  (rf/dispatch-sync [::rp/add-keyboard-event-listener "keydown"])
  (rf/dispatch
   [::rp/set-keydown-rules
    {:event-keys [
                   [[:slide-left] ;; left arrow or a
                    [{:keyCode 37}]
                    [{:keyCode 65}]]
                  
                   [[:slide-right] ;; right arrow or d
                    [{:keyCode 39}]
                    [{:keyCode 68}]]
                  
                  [[:slide-down] ;; down arrow or s
                   [{:keyCode 40}]
                   [{:keyCode 83}]]

                  [[:slide-up] ;; up arrow or w
                   [{:keyCode 38}]
                   [{:keyCode 87}]]]
  
     :clear-keys [;; escape
                  [{:keyCode 27}]
                      ;; ctrl+g
                  [{:keyCode 71
                    :ctrlKey true}]]
  
     :prevent-default-keys [;; ctrl+g
                            {:keyCode 71
                             :ctrlKey true}]
  
     :always-listen-keys [;; enter
                          {:keyCode 13}]}])
)

; dispatch the event which will create the initial state. 
(defonce initialize (rf/dispatch-sync [:initialize]))

(comment
  (rf/dispatch-sync [:slide-right])







  )