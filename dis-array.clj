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
                                    (conj tabs {:fx/type :tab
                                                :text title
                                                :closable true
                                                :content (let [[constraints children]
                                                               (loop [row 0 column 0 output [[][]] row-count []]
                                                                 (let [new-row (if (>= column (count (content row))) (inc row) row)
                                                                       column (if (= row new-row) column 0)]
                                                                   (if (>= new-row (count content))
                                                                     (update output 0 (fn [x] (map #(update % :percent-width / %2) x row-count)))
                                                                     (recur new-row (inc column)
                                                                       (do ;(prn "row" new-row "column" column)
                                                                         (let [text (str ((content new-row) column))]
                                                                           (update (update output 1 conj
                                                                                           {:fx/type :text-field
                                                                                            :disable false
                                                                                            ;:grid-pane/hgrow :always
                                                                                            ;:grid-pane/fill-width true
                                                                                            :grid-pane/column column
                                                                                            :grid-pane/row new-row
                                                                                            :text text})
                                                                                   0 (fn [x] (if (>= column (count x))
                                                                                               (conj x {:fx/type :column-constraints
                                                                                                        :min-width (/ max-width 10)
                                                                                                        :percent-width (count text)})
                                                                                               (update x column update :percent-width + (count text)))))))
                                                                       (if (>= column (count row-count)) (conj row-count 1) (update row-count column inc))))))]
                                                           {:fx/type :scroll-pane
                                                            :fit-to-width true
                                                            :content {:fx/type :grid-pane
                                                                      :column-constraints constraints
                                                                      :children children}})})))))}}}))))
