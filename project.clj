(defproject hodgepodge "0.1"
  :description "A idiomatic ClojureScript interface to localStorage"
  :url "https://github.com/dialelo/hodgepodge"
  :license {:name "BSD (2 Clause)"
            :url "http://opensource.org/licenses/BSD-2-Clause"}
  :source-paths ["src"]
  :dependencies [[org.clojure/clojurescript "0.0-2371"]
                 [org.clojure/clojure "1.6.0"]]
  :hooks [leiningen.cljsbuild]
  :plugins [[lein-cljsbuild "1.0.3"]
            [com.cemerick/clojurescript.test "0.3.0"]]
  :cljsbuild {:builds [{:source-paths ["src" "test"]
                        :compiler {:output-to "target/cljs/testable.js"
                                   :optimizations :simple}}]
              :test-commands {"unit" ["phantomjs" :runner
                                      "this.literal_js_was_evaluated=true"
                                      "target/cljs/testable.js"]}})
