(ns exfn.events
  (:require [re-frame.core :as rf]
            [exfn.logic :as lgc]))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:board (lgc/generate-board)}))

(rf/reg-event-db
 :slide-up
 (fn [db _] 
   ;;todo check for game over
   (assoc db :board (lgc/collapse-left (:board db)))))

(rf/reg-event-db
 :slide-down
 (fn [db _]
   ;;todo check for game over
   (assoc db :board (lgc/collapse-right (:board db)))))

(rf/reg-event-db
 :slide-right
 (fn [db _]
   ;;todo check for game over
   (assoc db :board (lgc/collapse-down (:board db)))))

(rf/reg-event-db
 :slide-left
 (fn [db _]
   ;;todo check for game over
   (assoc db :board (lgc/collapse-up (:board db)))))

