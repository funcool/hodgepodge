(defproject funcool/hodgepodge "0.1.4"
  :description "A idiomatic ClojureScript interface to HTML5 storage"
  :url "https://github.com/funcool/hodgepodge"
  :license {:name "BSD (2 Clause)"
            :url "http://opensource.org/licenses/BSD-2-Clause"}
  :source-paths ["src"]

  :dependencies [[org.clojure/clojurescript "0.0-2498" :scope "provided"]
                 [org.clojure/clojure "1.6.0" :scope "provided"]]

  :scm {:name "git"
        :url "https://github.com/funcool/hodgepodge"}

  :jar-exclusions [#"^test/.*"]
  :clean-targets ^{:protect false} ["target"
                                    "resources/test/out"
                                    "resources/test/test.js"]

  :profiles {:dev {:plugins [[lein-cljsbuild "1.0.4"]]}}
  :cljsbuild {:builds {:test
                       {:source-paths ["src" "test"]
                        :compiler {
                            :output-to "resources/test/test.js"
                            :output-dir "resources/test/out"
                            :optimizations :advanced}}}
              :test-commands {"unit" ["phantomjs" "resources/test/runner.js"]}})
