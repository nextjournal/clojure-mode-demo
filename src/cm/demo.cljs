(ns cm.demo
  (:require ["@codemirror/highlight" :as highlight]
            ["@codemirror/history" :refer [history historyKeymap]]
            ["@codemirror/state" :refer [EditorState]]
            ["@codemirror/view" :as view :refer [EditorView]]
            [nextjournal.clojure-mode :as cm]))


(def theme
  (.theme EditorView
          (clj->js {:$content {:white-space "pre-wrap"
                               :padding "10px 0"}
                    :$$focused {:outline "none"}
                    :$line {:padding "0 9px"
                            :line-height "1.6"
                            :font-size "16px"
                            :font-family "var(--code-font)"}
                    :$matchingBracket {:border-bottom "1px solid var(--teal-color)"
                                       :color "inherit"}
                    :$gutters {:background "transparent"
                               :border "none"}
                    :$gutterElement {:margin-left "5px"}
                    ;; only show cursor when focused
                    :$cursor {:visibility "hidden"}
                    "$$focused $cursor" {:visibility "visible"}})))

(defonce extensions
  #js[theme
      (history)
      highlight/defaultHighlightStyle
      (view/drawSelection)
      (.. EditorState -allowMultipleSelections (of true))
      cm/default-extensions
      (.of view/keymap cm/complete-keymap)
      (.of view/keymap historyKeymap)])

(defn init []
  (EditorView. #js {:state (.create EditorState #js {:doc "(def answer\n  (+ (* 4 10) 2))"
                                                     :extensions extensions})
                    :parent (.getElementById js/document "demo")}))
