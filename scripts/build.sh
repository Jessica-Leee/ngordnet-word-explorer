#!/usr/bin/env bash
set -euo pipefail
repo_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
module="${1:-}"
case "$module" in
  proj2a|proj2b) ;;
  *) echo "Usage: $0 proj2a|proj2b" >&2; exit 2 ;;
esac
lib_dir="${NGORDNET_LIB:-$repo_dir/lib}"
if ! compgen -G "$lib_dir/*.jar" >/dev/null; then
  echo "No course-library JARs found. Set NGORDNET_LIB to your library-fa24 directory." >&2
  exit 1
fi
lib_dir="$(cd "$lib_dir" && pwd)"
javac_cmd="${JAVA_HOME:+$JAVA_HOME/bin/}javac"
mkdir -p "$repo_dir/build/$module"
sources=()
while IFS= read -r -d '' source_file; do
  sources+=("$source_file")
done < <(find "$repo_dir/$module/src" -name '*.java' -print0)
"$javac_cmd" -encoding UTF-8 -cp "$lib_dir/*" -d "$repo_dir/build/$module" "${sources[@]}"
echo "Compiled $module."
