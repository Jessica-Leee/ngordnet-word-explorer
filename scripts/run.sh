#!/usr/bin/env bash
set -euo pipefail
repo_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
module="${1:-}"
case "$module" in
  proj2a|proj2b) ;;
  *) echo "Usage: $0 proj2a|proj2b [wordsFile synsetsFile hyponymsFile]" >&2; exit 2 ;;
esac
shift
"$repo_dir/scripts/build.sh" "$module"
lib_dir="${NGORDNET_LIB:-$repo_dir/lib}"
lib_dir="$(cd "$lib_dir" && pwd)"
java_cmd="${JAVA_HOME:+$JAVA_HOME/bin/}java"
cd "$repo_dir/$module"
exec "$java_cmd" -Djava.awt.headless=true -cp "$repo_dir/build/$module:$lib_dir/*" main.Main "$@"
