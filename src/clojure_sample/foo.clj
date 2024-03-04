(ns clojure-sample.foo)

(defonce
  ^{:doc "All registered rules, grouped by :init-type and full-name"}
  global-rules
  (atom {:rules {} :genres #{}}))

(defn deps-from-libspec
  "A modification from clojure.tools.namespace.parse/deps-from-libspec."
  [prefix form]
  (cond (prefix-spec? form)
    (mapcat (fn [f] (deps-from-libspec
                      (symbol (str (when prefix (str prefix "."))
                                (first form)))
                      f))
      (next form))

    (option-spec? form)
    (let [opts (apply hash-map (next form))]
      [{:ns (symbol (str (when prefix (str prefix ".")) (first form)))
        :alias (or (:as opts) (:as-alias opts))}])

    (js-dep-spec? form)
    (let [opts (apply hash-map (next form))]
      [{:ns (str (when prefix (str prefix ".")) (first form))
        :alias (or (:as opts) (:as-alias opts))}])))
