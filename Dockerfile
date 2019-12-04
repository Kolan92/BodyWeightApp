FROM node:13.2.0 as build-stage

ARG ISSUER_ARG 
ENV ISSUER=$ISSUER_ARG

ARG CLIENT_ID_ARG 
ENV CLIENT_ID=$CLIENT_ID_ARG

ARG REDIRECT_URI_ARG
ENV REDIRECT_URI=$REDIRECT_URI

WORKDIR /app

COPY ./frontend/package*.json /app/

RUN npm install

COPY ./frontend/ /app/

ARG configuration=production

RUN npm run build -- --output-path=./dist/out --configuration $configuration

FROM nginx:1.17

RUN rm /etc/nginx/conf.d/default.conf

COPY --from=build-stage /app/dist/out/ /usr/share/nginx/html

COPY ./infra/nginx.conf /etc/nginx/nginx.conf