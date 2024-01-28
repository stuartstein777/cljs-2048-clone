(ns exfn.events
  (:require [re-frame.core :as rf]
            [exfn.logic :as lgc]))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:board      (lgc/generate-board)
    :game-over? false}))

(rf/reg-event-db
 :restart
 (fn [db _]
   (-> db
       (assoc :board (lgc/generate-board))
       (assoc :game-over? false))))

(defn collapse [f board]
  (->> board
       (f)
       (lgc/add-random-if-moved board)))

(rf/reg-event-db
 :slide-up
 (fn [db _]
   (prn "slide left")
   (let [new-board (collapse lgc/collapse-left (:board db))]
     (-> db
         (assoc :board new-board)
         (assoc :game-over? (lgc/game-over? new-board))))))

(rf/reg-event-db
 :slide-down
 (fn [db _]
   (prn "slide right")
   (let [new-board (collapse lgc/collapse-right (:board db))]
     (-> db
         (assoc :board new-board)
         (assoc :game-over? (lgc/game-over? new-board))))))

(rf/reg-event-db
 :slide-right
 (fn [db _]
   (prn "slide down")
   (let [new-board (collapse lgc/collapse-down (:board db))]
     (-> db
         (assoc :board new-board)
         (assoc :game-over? (lgc/game-over? new-board))))))

(rf/reg-event-db
 :slide-left
 (fn [db _]
   (let [new-board (collapse lgc/collapse-up (:board db))]
     (-> db
         (assoc :board new-board)
         (assoc :game-over? (lgc/game-over? new-board))))))

