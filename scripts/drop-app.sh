#!/bin/bash

echo "🛑 Parando containers..."
docker compose down

echo "🧼 Removendo TODOS os containers..."
docker rm -f $(docker ps -aq) 2>/dev/null || echo "Nenhum container para remover."

echo "🧼 Removendo TODAS as imagens..."
docker rmi -f $(docker images -q) 2>/dev/null || echo "Nenhuma imagem para remover."

echo "🗑️  Limpando volumes (se quiser remover dados do Postgres)..."
read -p "Deseja remover volumes também? Isso apagará os dados do banco de dados. (s/n): " answer
if [ "$answer" = "s" ]; then
  docker volume rm $(docker volume ls -qf dangling=false | grep paymentservice_db) 2>/dev/null || true
  echo "✅ Volumes removidos."
else
  echo "⚠️  Volumes mantidos."
fi

echo "✅ Tudo limpo!"
