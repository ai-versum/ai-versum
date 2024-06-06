FROM node:20-alpine AS build

#WORKDIR /app

COPY . .
RUN npm i
RUN npm run build

COPY package*.json ./
COPY build ./

EXPOSE 3000
CMD ["node", "./index.js"]
