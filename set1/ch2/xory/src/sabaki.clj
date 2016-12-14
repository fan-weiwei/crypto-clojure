(ns sabaki
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

(def hex-chars [\0 \1 \2 \3 \4 \5 \6 \7 \8 \9 \a \b \c \d \e \f])

(defn chr-to-nibble [c] (Character/digit c 16))

(defn byte-to-bits [b]
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

(defn nibbles-to-bytes [string]
  (->> string
       (partition 2)
       (map #(+ (bit-shift-left (nth % 0) 4) (nth % 1)))
  )
)


(defn string-to-bits [string]
  (->> string
      (map chr-to-nibble)
      (nibbles-to-bytes)
      (map byte-to-bits)
      (apply str)
  )
)

(defn string-to-bytes [string]
  (->> string
       (map chr-to-nibble)
       (map nibbles-to-bytes)
  )
)

(defn sextuple-to-byte [string]
  (reduce + (for [x (range 0 6)]
    (bit-shift-left (Character/digit (nth string x) 2) (- 5 x))
  ))
)

(defn bits-to-base64 [string]
  (->> string
       (partition 6)
       (map sextuple-to-byte)

  )
)

(defn bits-to-base64String [string]
  (->> string
       (partition 6)
       (map sextuple-to-byte)
       (map #(get base64-chars %))
       (apply str)
  )
)

(defn pairwise-xor [pair]
  (bit-xor (nth pair 0) (nth pair 1))
  )

(defn xor-bits [str1 str2]
  (map pairwise-xor
       (map list (map #(Character/digit % 2) str1) (map #(Character/digit % 2) str2)))
  )

(defn bits-to-nibble [string]
  (->> string
       (partition 4)
       (map #(reduce + (for [x (range 0 4)]
                         (bit-shift-left (nth % x) (- 3 x))
                         )))
       )
  )

(defn bits-to-hexString [string]
  (->> string
       (nibbles-to-bytes)
       (map #(get hex-chars %))
       (apply str)
       )
  )

(defn bits-to-string [string]
  (->> string
       (bits-to-nibble)
       (nibbles-to-bytes)
       (map char)
       (apply str)
       )
  )

(defn -main [& args]
  (println (bits-to-base64String (string-to-bits hex-string))))
