echo "⏳ Aguardando pods ficarem prontos (readiness, timeout 5min)..."

TIMEOUT=300
INTERVAL=5
START_TIME=$(date +%s)

while true; do
  CURRENT_TIME=$(date +%s)
  ELAPSED=$((CURRENT_TIME - START_TIME))

  NOT_READY=$(kubectl get pods -n "$KUBERNETES_NAMESPACE" --field-selector=status.phase=Running \
    -o "custom-columns=NAME:.metadata.name,READY:.status.conditions[?(@.type==\"Ready\")].status" \
    --no-headers | grep -v 'True' | wc -l)

  if [ "$NOT_READY" -eq 0 ]; then
    echo "✅ Todos os pods passaram na readiness probe!"
    break
  fi

  if [ "$ELAPSED" -ge "$TIMEOUT" ]; then
    echo "❌ Timeout: Nem todos os pods ficaram prontos (readiness) em $TIMEOUT segundos."
    kubectl get pods -n "$KUBERNETES_NAMESPACE"
    exit 1
  fi

  sleep "$INTERVAL"
done