(ns xory.core
  (:gen-class)
  (require [sabaki.core :as s]))


(def hex-string "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d")
(def irl-string "I'm killing your brain like a poisonous mushroom")
(def str1 "1c0111001f010100061a024b53535009181c")
(def str2 "686974207468652062756c6c277320657965")

(defn -main [& args]

  (->> hex-string
       (s/string-to-bits)
       (s/bits-to-base64String)
       (println)
  )

  (->> (s/xor-bits (s/string-to-bits str1) (s/string-to-bits str2))
       (s/bits-to-hexString)
       (println)
  )

)