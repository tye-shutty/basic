(defmacro mtest1 [] '(str 1))

(defmacro mtest2 [] `(str 2))

(defmacro mtest3 [] (`str 3))

(defmacro mtest4 [] ('str 4))

(def mvar1 4)

(defmacro mtest5 [] '(str mvar1))

(defmacro mtest6 [] `(str mvar1))
