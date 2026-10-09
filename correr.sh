#!/usr/bin/env bash
# Compila y corre una clase con main. TODO lo que sale (sin recortar nada) queda también en evidencia/<nombre>.txt.
# Si el programa termina con error, al final repite la última línea «Caused by:»: casi siempre es la causa.
# Uso: ./correr.sh <Clase> <nombre-de-la-evidencia>  (ejemplo: ./correr.sh AppSinSpring mp1)
if [ $# -ne 2 ]; then
  echo "Uso: ./correr.sh <Clase> <nombre-de-la-evidencia>  (ejemplo: ./correr.sh AppSinSpring mp1)"; exit 2
fi
clase="$1"; nombre="$2"
case "$nombre" in
  ""|*[!A-Za-z0-9_-]*) echo "Nombre inválido: «${nombre}». Usa solo letras, números, - y _ (ejemplo: mp1)"; exit 2 ;;
esac
archivo="evidencia/$nombre.txt"
mkdir -p evidencia
if ! ./mvnw -q -B compile dependency:build-classpath -Dmdep.outputFile=target/classpath.txt > "$archivo" 2>&1; then
  cat "$archivo"
  echo "--- NO COMPILA: arriba está el error (busca la línea [ERROR] con el nombre de tu archivo .java) ---" | tee -a "$archivo"
  echo "→ salida completa en $archivo"
  exit 1
fi
java -cp "target/classes:$(cat target/classpath.txt)" "com.academia.banco.$clase" 2>&1 | tee -a "$archivo"
codigo=${PIPESTATUS[0]}
if [ "$codigo" -ne 0 ]; then
  causa=$(grep '^Caused by:' "$archivo" | tail -1)
  if [ -n "$causa" ]; then
    { echo; echo "→ La causa (la última línea «Caused by:» de arriba):"; echo "  $causa"; } | tee -a "$archivo"
  fi
fi
echo "→ salida completa en $archivo"
exit "$codigo"
