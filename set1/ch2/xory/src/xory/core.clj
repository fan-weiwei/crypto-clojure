(ns xory.core
  (:gen-class)
  (require [sabaki]))

(def str1 "1c0111001f010100061a024b53535009181c")
(def str2 "686974207468652062756c6c277320657965")


(defn -main [& args]
  (println (sabaki/bits-to-string (sabaki/xor-bits (sabaki/string-to-bits str1) (sabaki/string-to-bits str2)))))
