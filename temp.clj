(require '[cljfx.api :as fx])

(defn display-array [input]
  (fx/on-fx-thread
   (fx/create-component
    {:fx/type :stage
     :showing true
     :title "display-array"
     :scene {:fx/type :scene
             :root {:fx/type :scroll-pane
                    :fit-to-width true
                    :content {:fx/type :grid-pane
                              :children (loop [row 0 column 0 output []]
                                          (let [new-row (if (>= column (count (input row))) (inc row) row)
                                                column (if (= row new-row) column 0)]

                                            (if (>= new-row (count input)) output
                                              (recur new-row (inc column)
                                                (do (prn "row" new-row "column" column)
                                                  (conj output {:fx/type :text-field
                                                                :grid-pane/column column
                                                                :grid-pane/row new-row
                                                                :text (str ((input new-row) column))}))))))}}}})))
