(require '[cljfx.api :as fx])

;;make column widths relative to average size and actual number of columns
(defn dis-array "{:input [[\"tab name\" [[column1 column2 ...] more rows...]] more tabs...]}"
  [{:keys [window-title input] :or {window-title "display-array"}}]
  (fx/on-fx-thread
   (fx/create-component
    (let [max-width (.getWidth (.getVisualBounds (javafx.stage.Screen/getPrimary)))]
      {:fx/type :stage
       :showing true
       :maximized true ;not working
       :min-width max-width ;don't need min-height?
       :title window-title
       :scene {:fx/type :scene
               :stylesheets #{"styles.css"}
               :root {:fx/type :tab-pane
                      :tabs (loop [remaining-input input tabs []]
                              (if (empty? remaining-input) tabs
                                (let [title (first (first remaining-input))
                                      content (mapv vec (second (first remaining-input)))]
                                  (recur (rest remaining-input)
                                    (conj tabs
                                          {:fx/type :tab
                                           :text title
                                           :closable true
                                           :content
                                           (let [[constraints children]
                                                 (loop [row 0 column 0 output [[][]] row-count []]
                                                   (let [new-row (if (>= column (count (content row))) (inc row) row)
                                                         column (if (= row new-row) column 0)]
                                                     (if (>= new-row (count content))
                                                       (update output 0
                                                               (fn [x] (let [max-width-seq (let [temp (map #(assoc % :max-width (:min-width %)) x)] (prn row-count)(prn temp) temp)
                                                                             mean-width (let [temp (map #(update % :min-width (fn [y] (/ (apply + y) %2))) max-width-seq row-count)] (prn temp) temp)
                                                                             max-widths (map
                                                                                         (fn [y a]
                                                                                           (update y :max-width
                                                                                                   (fn [z]
                                                                                                     (+
                                                                                                      (y :min-width)
                                                                                                      (* 2
                                                                                                         (let [diff-sqrs (reduce
                                                                                                             #(+ % ((fn [b] (* b b))
                                                                                                                    (- %2 (y :min-width))))
                                                                                                             0 z)]
                                                                                                           (if (= diff-sqrs 0 ) (y :min-width)
                                                                                                             (Math/sqrt
                                                                                                              (/ diff-sqrs a))))))))) mean-width row-count)
                                                                             total-relative-width (let [temp (reduce #(+ % (:min-width %2)) 0 max-widths)] (prn temp) temp)]
                                                                         (prn max-widths)
                                                                         (mapv #(update % :min-width (fn [x] (* (/ x total-relative-width) max-width))) max-widths))))
                                                       (recur new-row (inc column)
                                                         (do ;(prn "row" new-row "column" column)
                                                           (let [text (str ((content new-row) column))]
                                                             (update (update output 1 conj
                                                                             {:fx/type :text-field
                                                                              :disable false
                                                                              :grid-pane/hgrow :always
                                                                              :grid-pane/column column
                                                                              :grid-pane/row new-row
                                                                              :text text})
                                                                     0 (fn [x] (if (>= column (count x))
                                                                                 (conj x {:fx/type :column-constraints
                                                                                          :min-width (list (count text))})
                                                                                 (update x column update :min-width conj (count text)))))))
                                                         (if (>= column (count row-count)) (conj row-count 1) (update row-count column inc))))))]
                                             {:fx/type :scroll-pane
                                              :fit-to-width true
                                              :content {:fx/type :grid-pane
                                                        :column-constraints constraints
                                                        :children children}})})))))}}}))))
