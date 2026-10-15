#!/usr/bin/env bash
set -euo pipefail
repo_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
lib_dir="${NGORDNET_LIB:-$repo_dir/lib}"
if [[ ! -d "$lib_dir" ]]; then
  echo "Set NGORDNET_LIB to your library-fa24 directory." >&2
  exit 1
fi
lib_dir="$(cd "$lib_dir" && pwd)"
javac_cmd="${JAVA_HOME:+$JAVA_HOME/bin/}javac"
java_cmd="${JAVA_HOME:+$JAVA_HOME/bin/}java"
for module in proj2a proj2b; do
  "$repo_dir/scripts/build.sh" "$module"
  "$javac_cmd" -encoding UTF-8 -cp "$repo_dir/build/$module:$lib_dir/*" \
    -d "$repo_dir/build/$module" "$repo_dir/verification/$module/SmokeCheck.java"
  (cd "$repo_dir/$module" && "$java_cmd" -Djava.awt.headless=true \
    -cp "$repo_dir/build/$module:$lib_dir/*" SmokeCheck)
done
