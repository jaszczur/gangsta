(ns gangsta.core
  (:gen-class))

(defn create-mafia [n]
  {:alive-set (apply hash-set (range 0 n)), :num n})

(defn all-dead? [mafia]
  (empty? (:alive-set mafia)))

(defn only-one-alive [mafia]
  (= 1 (count (:alive-set mafia))))

(defn lone-survivor [mafia]
  (if (only-one-alive mafia)
    (first (:alive-set mafia))
    nil))

(defn is-alive [mafia i]
  (contains? (:alive-set mafia) i))

(defn next-gangsta [mafia i]
  (if (all-dead? mafia)
    nil
    (let [imod (mod (inc i) (:num mafia))]
      (if (is-alive mafia imod)
        imod
        (recur mafia imod)))))

(defn kill-motherfucker [mafia i]
  (update-in mafia [:alive-set] disj i))

(def quiz-mafia
  (create-mafia 1500))

(defn lets-play-a-game []
  (loop [curr-mafia quiz-mafia
         i 0]
    (if (only-one-alive curr-mafia)
      (inc (lone-survivor curr-mafia))
      (let [to-be-shoot (next-gangsta curr-mafia i)
            next-shooter (next-gangsta curr-mafia to-be-shoot)]
        (recur (kill-motherfucker curr-mafia to-be-shoot) next-shooter)))))

(defn -main [] (prn (lets-play-a-game)))

