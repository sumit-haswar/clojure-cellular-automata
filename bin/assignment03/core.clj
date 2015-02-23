(ns assignment03.core)

;calculate-next-row function inputs a rule and a current row 
;and outputs the next row
(defn calculate-next-row
  [rule current-row]
  ;local loop variables are the current row, the next row and the bit position
	(loop [current current-row, next-row 2r0, bit-pos 0]   
	    ;using bit-and with 7 gets lowest three bits of current row 
      ;Test this bit number in the rule
	    (if (== bit-pos 31)
        (bit-shift-left next-row 1)
			  (if (bit-test rule (bit-and current 7))
			    ;bit is set, set the corresponding bit for next-row and recur
			    (recur (bit-shift-right current 1) (bit-set next-row bit-pos) (inc bit-pos))
			    ;bit is not set, recur with the next row unchanged 
			    (recur (bit-shift-right current 1) next-row (inc bit-pos))
			  )
	    )
	)
)

;display-row function uses doseq to allow the printing of the 
;output for each of the 31 bits; it tests each bit to see 
;if the output should be 1 or blank.
(defn display-row
  [row]
  (doseq [bit-pointer (range 31 -1 -1)]
      (if (bit-test row bit-pointer)
        (print "#")
        (print " ")
       )
 )
  (println)
)

;This function calls calculate-next-row and display-row to display the output.
(defn generate-cell-automata
  [rule]
  (dorun (map #(display-row %) (take 16 (iterate #(calculate-next-row rule %) 32768))))
)








