#!/bin/sh

(cd docs && make)
cp -vr docs/index.html /tmp/index.html
git checkout gh-pages

cp /tmp/index.html index.html
git add -u
git commit -a -m "Update docs"
git push origin gh-pages
git checkout -
