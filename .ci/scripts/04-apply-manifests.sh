echo "🔧 Verificando se o namespace '$KUBERNETES_NAMESPACE' já existe..."
if kubectl get namespace "$KUBERNETES_NAMESPACE" >/dev/null 2>&1; then
  echo "ℹ️ Namespace '$KUBERNETES_NAMESPACE' já existe. Prosseguindo..."
else
  echo "📁 Criando namespace Kubernetes '$KUBERNETES_NAMESPACE'..."
  kubectl create namespace "$KUBERNETES_NAMESPACE"
  if [ $? -ne 0 ]; then
    echo "❌ Erro ao criar namespace Kubernetes '$KUBERNETES_NAMESPACE'."
    exit 1
  fi
  echo "✅ Namespace '$KUBERNETES_NAMESPACE' criado com sucesso."
fi

echo "📦 Aplicando manifestos Kubernetes no namespace '$KUBERNETES_NAMESPACE'..."
kubectl apply -n "$KUBERNETES_NAMESPACE" -f k8s/db/
kubectl apply -n "$KUBERNETES_NAMESPACE" -f k8s/app/
if [ $? -ne 0 ]; then
  echo "❌ Erro ao aplicar manifestos Kubernetes."
  exit 1
fi
echo "✅ Manifestos Kubernetes aplicados com sucesso no namespace '$KUBERNETES_NAMESPACE'."