# Etapa 1: Build de la aplicación Angular
FROM node:20-alpine AS build
WORKDIR /app

COPY angular-app/package*.json ./angular-app/
WORKDIR /app/angular-app

COPY angular-app/ ./

RUN npm install
RUN npm run build --prod

# Etapa 2: Servir con Nginx
FROM nginx:alpine
COPY --from=build /app/angular-app/dist/angular-app/browser/ /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
