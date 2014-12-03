(defproject hodgepodge "0.1.0"
  :description "A idiomatic ClojureScript interface to HTML5 storage"
  :url "https://github.com/dialelo/hodgepodge"
  :license {:name "BSD (2 Clause)"
            :url "http://opensource.org/licenses/BSD-2-Clause"}
  :source-paths ["src"]
  :dependencies [[org.clojure/clojurescript "0.0-2371"]
                 [org.clojure/clojure "1.6.0"]]
  :scm {:name "git"
        :url "https://github.com/dialelo/hodgepodge"}
  :hooks [leiningen.cljsbuild]
  :plugins [[lein-cljsbuild "1.0.3"]]
  :cljsbuild {:builds {:main
                       {:source-paths ["src"]
                        :compiler {
                            :output-to "hodgepodge.js"
                            :output-dir "resources"
                            :optimizations :none
                            :source-map true}}
                       :test
                       {:source-paths ["src" "test"]
                        :compiler {
                            :output-to "test.js"
                            :output-dir "resources/test"
                            :optimizations :advanced}}}
              :test-commands {"unit" ["phantomjs" "resources/test/runner.js"]}})
