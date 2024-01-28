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
       (assoc :game-over? false)
       (assoc :score 0))))

(defn collapse [f board]
  (->> board
       (f)))

(rf/reg-event-db
 :slide-up
 (fn [db _]
   (let [new-board (collapse lgc/collapse-left (:board db))
         turn-score (lgc/score (:board db) new-board)
         board-with-random (lgc/add-random-if-moved (:board db) new-board)]
     (-> db
         (assoc :board board-with-random)
         (assoc :game-over? (lgc/game-over? new-board))
         (update :score + turn-score)))))

(rf/reg-event-db
 :slide-down
 (fn [db _]
   (let [new-board (collapse lgc/collapse-right (:board db))
         turn-score (lgc/score (:board db) new-board)
         board-with-random (lgc/add-random-if-moved (:board db) new-board)]
     (-> db
         (assoc :board board-with-random)
         (assoc :game-over? (lgc/game-over? new-board))
         (update :score + turn-score)))))

(rf/reg-event-db
 :slide-right
 (fn [db _]
   (let [new-board (collapse lgc/collapse-down (:board db))
         turn-score (lgc/score (:board db) new-board)
         board-with-random (lgc/add-random-if-moved (:board db) new-board)]
     (-> db
         (assoc :board board-with-random)
         (assoc :game-over? (lgc/game-over? new-board))
         (update :score + turn-score)))))

(rf/reg-event-db
 :slide-left
 (fn [db _]
   (let [new-board (collapse lgc/collapse-up (:board db))
         turn-score (lgc/score (:board db) new-board)
         board-with-random (lgc/add-random-if-moved (:board db) new-board)]
     (-> db
         (assoc :board board-with-random)
         (assoc :game-over? (lgc/game-over? new-board))
         (update :score + turn-score)))))

