(ns exfn.app
  (:require [reagent.dom :as dom]
            [re-frame.core :as rf]
            [exfn.subscriptions]
            [exfn.events]
            [exfn.logic :as lgc]
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
  (start))

; dispatch the event which will create the initial state. 
(defonce initialize (rf/dispatch-sync [:initialize]))

(comment
  (rf/dispatch-sync [:slide-up])







  )