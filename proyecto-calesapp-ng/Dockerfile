# Etapa 1: Build de la aplicación Angular
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./proyecto-calesapp-ng/
WORKDIR /app/proyecto-calesapp-ng
RUN npm install

COPY . ./

RUN npm run build --prod

# Etapa 2: Servir con Nginx
FROM nginx:alpine
COPY --from=build /app/proyecto-calesapp-ng/dist/proyecto-calesapp-ng/browser/ /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
