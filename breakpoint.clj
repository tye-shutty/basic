;;call break where you want a repl, enter the symbol you want to know the value of in that repl
;;exit repl with ctl-D
(defn contextual-eval [ctx expr]
    (eval                                           
        `(let [~@(mapcat (fn [[k v]] [k `'~v]) ctx)] 
             ~expr)))
(defmacro local-context []
    (let [symbols (keys &env)]
        (zipmap (map (fn [sym] `(quote ~sym)) symbols) symbols)))
(defn readr [prompt exit-code]
    (let [input (clojure.main/repl-read prompt exit-code)]
        (if (= input ::tl)
            exit-code
             input)))
;;make a break point
(defmacro break []
  `(clojure.main/repl
    :prompt #(print "debug=> ")
    :read readr
    :eval (partial contextual-eval (local-context))))
