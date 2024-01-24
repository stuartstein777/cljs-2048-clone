(ns exfn.subscriptions
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :board
 (fn [db _]
   (:board db)))

(rf/reg-sub
 :is-game-over?
 (fn [db _]
   (:is-game-over? db)))

