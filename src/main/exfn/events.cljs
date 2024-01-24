(ns exfn.events
  (:require [re-frame.core :as rf]
            [exfn.logic :as bf]
            [clojure.set :as set]
            [exfn.logic :as lgc]))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:board (bf/generate-board)}))

(rf/reg-event-db
 :slide-up
 (fn [db _]
   (let [board (:board db)]
     (assoc db :board (lgc/collapse-left board)))))

(rf/reg-event-db
 :slide-down
 (fn [db _]
   (let [board (:board db)]
     (assoc db :board (lgc/collapse-right board)))))

(rf/reg-event-db
 :slide-right
 (fn [db _]
   (let [board (:board db)]
     (assoc db :board (lgc/collapse-down board)))))

(rf/reg-event-db
 :slide-left
 (fn [db _]
   (let [board (:board db)]
     (assoc db :board (lgc/collapse-up board)))))

