(ns exfn.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :board
 (fn [db _]
   (:board db)))

(rf/reg-sub
 :game-over?
 (fn [db _]
   (:game-over? db)))

(rf/reg-sub
 :score
 (fn [db _]
   (:score db)))