(defn popup [x] (future (do (Thread/sleep (* 1000 x)) (fx/on-fx-thread (fx/create-component {:fx/type :stage :showing true :title "done!" :scene {:fx/type :scene :stylesheets #{"popup-styles.css"} :root {:fx/type :border-pane :center {:fx/type :label :border-pane/margin 10 :text (str x " seconds is up!")}}}})))))
