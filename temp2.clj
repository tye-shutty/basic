(#(let [info (second %) location (if (var? info) (str (:ns (meta info))) (apply str (butlast (clojure.string/split (str info) #"\."))))]
 [location (str (first %)) (if (var? info) (:doc (meta info)) (second %))]) (first (next (ns-map *ns*))))

(display-array (vec (sort (mapv #(let [info (second %) location (if (var? info) (str (:ns (meta info))) (apply str (interpose "." (butlast (rest (clojure.string/split (str info) #"[\.\s]"))))))]
                                   [location (str (first %)) (if (var? info) (:doc (meta info)) (second (clojure.string/split (str (second %)) #"\s")))]) (ns-map *ns*)))))

(vec (sort (mapv #(let [info (second %) location (if (var? info) (str (:ns (meta info))) (apply str (interpose "." (butlast (rest (clojure.string/split (str info) #"[\.\s]"))))))]
                                   [location (str (first %)) (if (var? info) (:doc (meta info)) (second (clojure.string/split (str (second %)) #"\s")))]) (ns-map *ns*))))

(reduce #(merge % (or (reduce-kv (fn [_ k v] (if (= k (first %2)) (reduced {k (conj v (vec (rest %2)))}))) nil %)
                      {(first %2) [(vec (rest %2))]})) {} [["java.util" "Callable" "java.util.concurrent.Callable"] ["user" "display-array" nil] ["user" "display-array-tabs2" nil]])

(into [] (reduce #(merge % (or (reduce-kv (fn [_ k v] (if (= k (first %2)) (reduced {k (conj v (vec (rest %2)))}))) nil %)
                               {(first %2) [(vec (rest %2))]})) {}
                 (vec (sort (mapv #(let [info (second %) location (if (var? info) (str (:ns (meta info))) (apply str (interpose "." (butlast (rest (clojure.string/split (str info) #"[\.\s]"))))))]
                                     [location (str (first %)) (if (var? info) (:doc (meta info)) (second (clojure.string/split (str (second %)) #"\s")))]) (ns-map *ns*))))))

(dis-array {:window-title "good" :input (into [] (reduce #(merge % (or (reduce-kv (fn [_ k v] (if (= k (first %2)) (reduced {k (conj v (vec (rest %2)))}))) nil %)
                                                                {(first %2) [(vec (rest %2))]})) {}
                                                  (vec (sort (mapv #(let [info (second %) location (if (var? info) (str (:ns (meta info))) (apply str (interpose "." (butlast (rest (clojure.string/split (str info) #"[\.\s]"))))))]
                                                                      [location (str (first %)) (if (var? info) (:doc (meta info)) (second (clojure.string/split (str (second %)) #"\s")))]) (ns-map *ns*))))))})

(dis-array {:input [["tabone" [[1 2] (range 30)[nil]]]]})
