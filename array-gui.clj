(require '[fn-fx.fx-dom :as dom]
         '[fn-fx.diff :refer [component defui render should-update?]]
         '[fn-fx.controls :as ui])

(defn show-array [d2vec]

  (let [d2vec-str (let [a (if (coll? d2vec) (mapv #(if (coll? %) (mapv (fn [x] (if (string? x) x (str x))) %) [(str %)]) d2vec) [[(str d2vec)]])] (prn a) a)
        u (ui/stage :shown true
            :scene (ui/scene :root
                             (ui/scroll-pane :fitToWidth true
                              :children (ui/grid-pane :alignment :center :hgap 10 :vgap 10
                                                           :padding (ui/insets :bottom 25 :left 25 :right 25 :top 25)
                                                           :children (vec (apply concat
                                                                            (keep-indexed
                                                                             #(keep-indexed
                                                                               (fn [i v] (ui/text :text v :grid-pane/column-index i :grid-pane/row-index %)) %2)
                                                                             d2vec-str)))))))
        ]

    (dom/app u)
    ))
