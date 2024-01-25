(ns exfn.logic 
  (:require [re-frame.core :as rf]))

(def cell-to-coord
  {1  [0 0]
   2  [0 1]
   3  [0 2]
   4  [0 3]
   5  [1 0]
   6  [1 1]
   7  [1 2]
   8  [1 3]
   9  [2 0]
   10 [2 1]
   11 [2 2]
   12 [2 3]
   13 [3 0]
   14 [3 1]
   15 [3 2]
   16 [3 3]})
   
(defn rotate-board [board]
  (apply map vector board))

(defn pad-left [n v]
  (vec (concat v (repeat (- n (count v)) 0))))

(defn slide-left [row]
  (vec (remove zero? row)))

(defn merge-vector [row]
  (loop [i 0
         res []]
    (if (>= i (count row))
      (vec res)
      (if (= (dec (count row)) i)
        (recur (inc i) (conj res (nth row i)))
        (if (= (nth row i) (nth row (inc i)))
          (recur (+ 2 i) (conj res (* 2 (nth row i))))
          (recur (inc i) (conj res (nth row i))))))))

(defn update-2d-vector [v coord value]
  (assoc-in v coord value))

(defn get-empty-cells [board]
  (let [xs (range 1 17)
        cells (map cell-to-coord xs)]
    (filter #(= 0 (get-in board %)) cells)))

(defn add-random-value [possibilities board]
  (let [number        (first (shuffle possibilities))
        empty-cells   (get-empty-cells board)
        random-cell   (first (shuffle empty-cells))]
    (update-2d-vector board random-cell number)))

(defn add-random-if-moved [board new-board]
  (if (= new-board board)
    board
    (->> new-board
         (add-random-value [2 2 2 2 2 2 2 2 4])
         (add-random-value [2 2 2 2 2 2 2 2 4]))))

(defn collapse-left [board]
  (->> board
       (mapv slide-left)
       (mapv merge-vector)
       (mapv (partial pad-left 4))
       (add-random-if-moved board)))

(defn collapse-right [board]
  (->> board
       (mapv reverse)
       (mapv slide-left)
       (mapv merge-vector)
       (mapv (partial pad-left 4))
       (mapv (comp vec reverse))
       (add-random-if-moved board)))

(defn collapse-down [board]
  (->> board
       (rotate-board)
       (collapse-right)
       (rotate-board)
       (rotate-board)
       (rotate-board)
       (add-random-if-moved board)))
  
(defn collapse-up [board]
  (->> board
       (rotate-board)
       (collapse-left)
       (rotate-board)
       (rotate-board)
       (rotate-board)
       (add-random-if-moved board)))

(defn generate-board []
  (let [board [[0 0 0 0] [0 0 0 0] [0 0 0 0] [0 0 0 0]]]
    (->> board
         (add-random-value [2])
         (add-random-value [2]))))

(defn any-moves? [row]
  (not= 4 (count (partition-by identity row))))

(defn game-over? [board]
  (and
   (every? false? (map any-moves? board))
   (every? false? (map any-moves? (rotate-board board)))))
  
(comment
  
  (let [board [[ 4  8  2  4]
               [ 8  4 16  8]
               [ 4 64  2 32]
               [16  2  8 32]]]
    #_(collapse-left board)
    #_(collapse-right board)
    (collapse-down board)
    #_(collapse-up board)
    #_(add-random-value [2 2 2 2 2 2 2 2 4] board)
    #_(generate-board)
    #_(game-over? board)
    )
  
  [[0 4 0 4]
   [0 4 0 0]
   [4 2 2 2]
   [0 2 4 4]]

  ;; left
  ;; [8 0 0 0]
  ;; [4 0 0 0]
  ;; [4 4 2 0]
  ;; [2 8 0 0]

  ;; right
  ;; [0 0 0 8]
  ;; [0 0 0 4]
  ;; [0 4 2 4]
  ;; [0 0 2 8]

  ;; down
  ;; [0 0 0 0]
  ;; [0 0 0 4]
  ;; [0 8 2 2]
  ;; [4 4 4 4]

  ;; up
  ;; [4 8 2 4]
  ;; [0 4 4 2]
  ;; [0 0 0 4]
  ;; [0 0 0 0]
    )
  
  