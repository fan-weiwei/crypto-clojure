(ns hex.core
  (:gen-class))

(def hex-string "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d")
(def irl-string "I'm killing your brain like a poisonous mushroom")


(def ^:private base64-chars
  "Base64 Characters in the right order."
  (vec
    (concat
      (map char
           (concat
             (range (int \A) (inc (int \Z)))
             (range (int \a) (inc (int \z)))
             (range (int \0) (inc (int \9)))))
      [\+ \/ \=])))


(defn chr-to-byte [c] (Character/digit c 16))

(defn byte-to-ones [b]
  (apply str (reverse
    (for [x (range 0 8)]
      (if
        (< 0 (->> x
             (bit-shift-left 1)
             (bit-and b)
        ))
        1 0
     ))
  ))
)

(defn pair-to-bytes [pair]
  (+ (bit-shift-left (nth pair 0) 4) (nth pair 1))
  )


(defn string-to-ones [string]
  (->> string
      (map chr-to-byte)
      (partition 2)
      (map pair-to-bytes)
      (map byte-to-ones)
      (apply str)
  )
)

(defn string-to-bytes [string]
  (->> string
       (map chr-to-byte)
       (partition 2)
       (map pair-to-bytes)
  )
)

(defn sextuple-to-byte [string]
  (reduce + (for [x (range 0 6)]
    (bit-shift-left (Character/digit (nth string x) 2) (- 5 x))
  ))
)

(defn ones-to-base64 [string]
  (->> string
       (partition 6)
       (map sextuple-to-byte)

  )
)

(defn ones-to-base64String [string]
  (->> string
       (partition 6)
       (map sextuple-to-byte)
       (map #(get base64-chars %))
       (apply str)
  )
)

(defn -main [& args]
  (println (ones-to-base64String (string-to-ones hex-string))))
